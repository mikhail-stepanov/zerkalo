package com.tochka.ms.client.exceptions;

public class MsInternalErrorException extends MicroServiceException {

    public MsInternalErrorException(String message) {
        super(message);
    }

    @Override
    public String staticMessage(){
        return getMessage() == null || getMessage().isEmpty() ? "internal_error" : getMessage();
    }
}
