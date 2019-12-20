package zerkalo.msclient.exceptions;

public class MsNotAuthorizedException extends MicroServiceException {

    public MsNotAuthorizedException() {
        super("Not authorized.");
    }

    @Override
    public String staticMessage(){
        return getMessage() == null || getMessage().isEmpty() ? "internal_error" : getMessage();
    }
}
