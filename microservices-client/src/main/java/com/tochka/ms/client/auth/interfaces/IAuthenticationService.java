package com.tochka.ms.client.auth.interfaces;

import com.tochka.ms.client.auth.models.AuthInfoRequest;
import com.tochka.ms.client.auth.models.AuthInfoResponse;
import com.tochka.ms.client.auth.models.AuthLoginRequest;
import com.tochka.ms.client.auth.models.AuthLoginResponse;
import com.tochka.ms.client.exceptions.MicroServiceException;

public interface IAuthenticationService {

    String AUTH_INFO = "/v1/auth/info";
    String AUTH_LOGIN = "/v1/auth/login";

    AuthInfoResponse info(AuthInfoRequest request) throws MicroServiceException;

    AuthLoginResponse login(AuthLoginRequest request) throws MicroServiceException;
}
