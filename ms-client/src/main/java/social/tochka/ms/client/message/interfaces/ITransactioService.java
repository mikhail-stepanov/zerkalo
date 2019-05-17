package social.tochka.ms.client.message.interfaces;


import social.tochka.ms.client.message.models.transacitons.MessageListRequest;
import social.tochka.ms.client.message.models.transacitons.MessageListResponse;
import social.tochka.ms.client.message.models.transacitons.MessageSendRequest;
import social.tochka.ms.client.message.models.transacitons.MessageSendResponse;

public interface ITransactioService {

    String MESSAGES_SERVICE_SEND_V1 = "/v1/messages/send";
    String MESSAGES_SERVICE_LIST_V1 = "/v1/messages/list";

    /**
     * Отправка транзакции получателю
     *
     * @param request параметры транзакции для отправки
     * @return - параметры отправленной транзации со сгенерированным идентификатором
     * @throws Exception
     */
    MessageSendResponse send(MessageSendRequest request) throws Exception;

    /**
     * Метод для получения списка транзакций
     *
     * @param request - параметры запроса
     * @return - список транзакций согласно параметрам
     * @throws Exception
     */
    MessageListResponse list(MessageListRequest request) throws Exception;
}
