package social.tochka.ms.client.message.models.transacitons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageSendRequest {

    /**
     * Идентификатор документооборота
     */
    private String docflowId;

    /**
     * Идентификатор отправителя
     */
    private String senderId;

    /**
     * Идентификатор получателя
     */
    private String recipientId;

    /**
     * Идентификатор контента документа
     */
    private String documentContentId;
}
