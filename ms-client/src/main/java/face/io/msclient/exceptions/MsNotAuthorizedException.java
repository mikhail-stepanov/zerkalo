package face.io.msclient.exceptions;

public class MsNotAuthorizedException extends MicroServiceException {

    public MsNotAuthorizedException() {
        super("");
    }

    @Override
    public String staticMessage(){
        return getMessage() == null || getMessage().isEmpty() ? "internal_error" : getMessage();
    }
}
