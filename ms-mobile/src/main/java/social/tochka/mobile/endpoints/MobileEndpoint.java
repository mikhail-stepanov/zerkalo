package social.tochka.mobile.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import social.tochka.ms.client.auth.interfaces.IAuthenticationService;
import social.tochka.ms.client.auth.models.*;
import social.tochka.ms.client.exceptions.MicroServiceException;

import javax.validation.Valid;

@RestController
public class MobileEndpoint {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/auth/info")
    public AuthInfoResponse info(@Valid @RequestBody AuthInfoRequest request) throws MicroServiceException {
        return authenticationService.info(request);
    }

    @PostMapping("/auth/signup")
    public AuthLoginResponse signUp(@Valid @RequestBody AuthSignUpRequest request) throws MicroServiceException {
        return authenticationService.signUp(request);
    }

    @PostMapping("/auth/login")
    public AuthLoginResponse login(@Valid @RequestBody AuthLoginRequest request) throws MicroServiceException {
        return authenticationService.login(request);
    }

    @PostMapping("/auth/logout")
    public AuthLogoutResponse logout(@Valid @RequestBody AuthLogoutRequest request) throws MicroServiceException {
        return authenticationService.logout(request);
    }


}
