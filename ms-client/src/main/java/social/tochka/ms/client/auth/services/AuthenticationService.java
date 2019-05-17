package social.tochka.ms.client.auth.services;

import social.tochka.ms.client.auth.interfaces.IAuthenticationService;
import social.tochka.ms.client.auth.models.*;
import social.tochka.ms.client.common.services.BaseMicroservice;
import org.springframework.web.client.RestTemplate;
import social.tochka.ms.client.exceptions.MicroServiceException;


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
