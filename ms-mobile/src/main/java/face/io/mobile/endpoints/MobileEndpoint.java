package face.io.mobile.endpoints;

import face.io.msclient.auth.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import face.io.msclient.auth.interfaces.IAuthenticationService;
import face.io.msclient.exceptions.MicroServiceException;

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
