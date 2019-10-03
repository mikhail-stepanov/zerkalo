package face.io.msclient.exceptions;

public class MsAlreadyExistsException extends MicroServiceException {

    public MsAlreadyExistsException() {
        super("Объект существует.");
    }

    @Override
    public String staticMessage(){
        return "object_already_exists";
    }
}