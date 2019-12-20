package zerkalo.msclient.exceptions;

public class MsAlreadyExistsException extends MicroServiceException {

    public MsAlreadyExistsException() {
        super("Object Exists.");
    }

    @Override
    public String staticMessage(){
        return "object_already_exists";
    }
}
