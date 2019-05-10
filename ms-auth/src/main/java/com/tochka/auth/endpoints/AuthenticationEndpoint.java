package com.tochka.auth.endpoints;

import com.tochka.common.endpoints.AbstractMicroservice;
import com.tochka.common.models.UserInfo;
import com.tochka.common.util.RLUCache;
import com.tochka.database.DatabaseService;
import com.tochka.database.entity.TchUser;
import com.tochka.database.entity.TchUserSession;
import com.tochka.ms.client.auth.interfaces.IAuthenticationService;
import com.tochka.ms.client.auth.models.AuthInfoRequest;
import com.tochka.ms.client.auth.models.AuthInfoResponse;
import com.tochka.ms.client.auth.models.AuthLoginRequest;
import com.tochka.ms.client.auth.models.AuthLoginResponse;
import com.tochka.ms.client.exceptions.MicroServiceException;
import com.tochka.ms.client.exceptions.MsNotAuthorizedException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Primary
@RestController
public class AuthenticationEndpoint extends AbstractMicroservice implements IAuthenticationService {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private DatabaseService databaseService;

    private RLUCache<String, String> tokensCache;
    private RLUCache<String, UserInfo> usersCache;

    @PostConstruct
    private void initialize() {
        int ttl = 60_000;
        int max = 10_000;

        tokensCache = new RLUCache<>(ttl, max);
        usersCache = new RLUCache<>(ttl, max);
    }

    @Override
    @RequestMapping(path = AUTH_INFO, method = RequestMethod.POST)
    public AuthInfoResponse info(@Valid @RequestBody AuthInfoRequest request) throws MicroServiceException {

        ObjectContext context = databaseService.getContext();

        UUID sessionId = UUID.fromString(request.getToken());

        TchUserSession session = SelectById.query(TchUserSession.class, sessionId).selectFirst(context);

        return AuthInfoResponse.builder()
                .userId(session.getUser().getAccountId().toString())
                .userName(session.getUser().getLogin())
                .build();
    }

    @Override
    @RequestMapping(path = AUTH_LOGIN, method = RequestMethod.POST)
    public AuthLoginResponse login(@Valid @RequestBody AuthLoginRequest request) throws MicroServiceException {

        ObjectContext context = databaseService.getContext();

        TchUser user = ObjectSelect.query(TchUser.class)
                .where(TchUser.LOGIN.eq(request.getLogin()))
                .where(TchUser.PASSWORD.eq(password(request.getPassword())))
                .selectOne(context);

        if (user == null) {
            throw new MsNotAuthorizedException();
        }
        TchUserSession session = ObjectSelect.query(TchUserSession.class).where(TchUserSession.USER.eq(user)).selectFirst(context);

        if (session == null) {
            session = context.newObject(TchUserSession.class);
            session.setUser(user);
            context.commitChanges();
        }

        return AuthLoginResponse.builder()
                .token(session.getObjectId().getIdSnapshot().get("id").toString())
                .userId(session.getUser().getAccountId().toString())
                .userName(user.getLogin())
                .build();
    }

    public String password(@Valid @RequestBody String request) throws MicroServiceException {
        String password = "dp_" + request;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
