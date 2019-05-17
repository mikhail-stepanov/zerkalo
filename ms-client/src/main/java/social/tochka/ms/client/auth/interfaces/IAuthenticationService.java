package social.tochka.ms.client.auth.interfaces;

import social.tochka.ms.client.auth.models.*;
import social.tochka.ms.client.exceptions.MicroServiceException;

public interface IAuthenticationService {

    String AUTH_INFO = "/auth/info";
    String AUTH_SIGNUP = "/auth/signup";
    String AUTH_LOGIN = "/auth/login";
    String AUTH_LOGOUT = "/auth/logout";
    String AUTH_PASSWORD = "/auth/password";

    AuthInfoResponse info(AuthInfoRequest request) throws MicroServiceException;

    AuthLoginResponse signUp(AuthSignUpRequest request) throws MicroServiceException;

    AuthLoginResponse login(AuthLoginRequest request) throws MicroServiceException;

    AuthLogoutResponse logout(AuthLogoutRequest request) throws MicroServiceException;

    String password(String request) throws MicroServiceException;

}
