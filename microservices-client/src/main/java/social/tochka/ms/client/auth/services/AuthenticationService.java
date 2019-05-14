package social.tochka.ms.client.auth.services;

import social.tochka.ms.client.auth.interfaces.IAuthenticationService;
import social.tochka.ms.client.auth.models.AuthInfoRequest;
import social.tochka.ms.client.auth.models.AuthInfoResponse;
import social.tochka.ms.client.auth.models.AuthLoginRequest;
import social.tochka.ms.client.auth.models.AuthLoginResponse;
import social.tochka.ms.client.common.services.BaseMicroservice;
import org.springframework.web.client.RestTemplate;


public class AuthenticationService extends BaseMicroservice implements IAuthenticationService {

    public AuthenticationService(RestTemplate restTemplate) {
        super("ms-auth", restTemplate);
    }

    @Override
    public AuthInfoResponse info(AuthInfoRequest request) {

        return restTemplate.postForEntity(buildUrl(AUTH_INFO), request, AuthInfoResponse.class).getBody();
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest request) {
        return restTemplate.postForEntity(buildUrl(AUTH_LOGIN), request, AuthLoginResponse.class).getBody();
    }
}
