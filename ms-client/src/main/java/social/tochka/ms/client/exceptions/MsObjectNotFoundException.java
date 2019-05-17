package social.tochka.ms.client.exceptions;

public class MsObjectNotFoundException extends MicroServiceException {

    public MsObjectNotFoundException(String objectType, String objectId) {
        super(String.format("Не удалось найти объект. Тип: %s. Параметры поиска: %s", objectType, objectId));
    }

    @Override
    public String staticMessage(){
        return "object_not_found_error";
    }
}
