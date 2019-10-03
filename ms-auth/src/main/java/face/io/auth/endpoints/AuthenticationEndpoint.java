package face.io.auth.endpoints;

import face.io.common.endpoints.AbstractMicroservice;
import face.io.common.models.UserInfo;
import face.io.common.util.RLUCache;
import face.io.database.DatabaseService;
import face.io.database.entity.TchUser;
import face.io.database.entity.TchUserSession;
import face.io.msclient.auth.interfaces.IAuthenticationService;
import face.io.msclient.auth.models.*;
import face.io.msclient.exceptions.MicroServiceException;
import face.io.msclient.exceptions.MsNotAuthorizedException;
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

@Primary
@RestController
public class AuthenticationEndpoint extends AbstractMicroservice implements IAuthenticationService {

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

        TchUserSession session = SelectById.query(TchUserSession.class, sessionId).selectOne(context);

        return AuthInfoResponse.builder()
                .userId(session.getUser().getObjectId().getIdSnapshot().get("id").toString())
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
                .selectFirst(context);

        if (user == null) {
            throw new MsNotAuthorizedException();
        }

        TchUserSession session = context.newObject(TchUserSession.class);
        session.setUser(user);
        context.commitChanges();

        return AuthLoginResponse.builder()
                .token(session.getObjectId().getIdSnapshot().get("id").toString())
                .userId(session.getUser().getObjectId().getIdSnapshot().get("id").toString())
                .userName(user.getLogin())
                .build();
    }

    @Override
    @RequestMapping(path = AUTH_LOGOUT, method = RequestMethod.POST)
    public AuthLogoutResponse logout(@Valid @RequestBody AuthLogoutRequest request) throws MicroServiceException {
        return null;
    }

    @Override
    @RequestMapping(path = AUTH_SIGNUP, method = RequestMethod.POST)
    public AuthLoginResponse signUp(@Valid @RequestBody AuthSignUpRequest request) throws MicroServiceException {

        ObjectContext context = databaseService.getContext();

        TchUser user = context.newObject(TchUser.class);

        user.setLogin(request.getLogin());
        user.setPassword(password(request.getPassword()));

        context.commitChanges();

        return login(AuthLoginRequest.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .build());
    }

    @Override
    @RequestMapping(path = AUTH_PASSWORD, method = RequestMethod.POST)
    public String password(@Valid @RequestBody String request) throws MicroServiceException {
        String password = "tch_" + request;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
