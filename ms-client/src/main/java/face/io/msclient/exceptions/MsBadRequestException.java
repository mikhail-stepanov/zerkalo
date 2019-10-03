package face.io.msclient.exceptions;

public class MsBadRequestException extends MicroServiceException {

    public MsBadRequestException(String parametername, String parameterValue) {
        super(String.format("Неверный аргумент. Название: %s. Значение: %s", parametername, parameterValue));
    }

    public MsBadRequestException(String parametername) {
        super(String.format("Неверный аргумент. Название: %s. Значение: null", parametername));
    }

    @Override
    public String staticMessage(){
        return "bad_request_error";
    }
}
