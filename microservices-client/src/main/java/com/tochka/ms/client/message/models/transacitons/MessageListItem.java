package com.tochka.ms.client.message.models.transacitons;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class MessageListItem {
    /**
     * Тип транзакции
     */
    private String code;

    /**
     * Идентификатор документооборота
     */
    private UUID docflowId;

    /**
     * Тип документооборота
     */
    private Integer docflowCode;

    /**
     * Идентификатор отправителя
     */
    private String senderId;

    /**
     * Идентификатор получателя
     */
    private String receiverId;

    /**
     * Идентификатор документа
     */
    private Long documentId;
}
