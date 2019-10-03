package face.io.msclient.auth.services;

import face.io.msclient.auth.interfaces.IAuthenticationService;
import face.io.msclient.auth.models.*;
import face.io.msclient.common.services.BaseMicroservice;
import face.io.msclient.exceptions.MicroServiceException;
import org.springframework.web.client.RestTemplate;


public class AuthenticationService extends BaseMicroservice implements IAuthenticationService {

    public AuthenticationService(RestTemplate restTemplate) {
        super("ms-auth", restTemplate);
    }

    @Override
    public AuthInfoResponse info(AuthInfoRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(AUTH_INFO), request, AuthInfoResponse.class).getBody());
    }

    @Override
    public AuthLoginResponse signUp(AuthSignUpRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(AUTH_SIGNUP), request, AuthLoginResponse.class).getBody());
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(AUTH_LOGIN), request, AuthLoginResponse.class).getBody());
    }

    @Override
    public AuthLogoutResponse logout(AuthLogoutRequest request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(AUTH_LOGOUT), request, AuthLogoutResponse.class).getBody());
    }

    @Override
    public String password(String request) throws MicroServiceException {
        return retry(() -> restTemplate.postForEntity(buildUrl(AUTH_PASSWORD), request, String.class).getBody());
    }
}
