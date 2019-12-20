package zerkalo.msclient.exceptions;

public class MsObjectNotFoundException extends MicroServiceException {

    public MsObjectNotFoundException(String objectType, String objectId) {
        super(String.format("Could not find object. Type: %s. Parameters: %s", objectType, objectId));
    }

    @Override
    public String staticMessage(){
        return "object_not_found_error";
    }
}
