package com.tochka.ms.client.message.interfaces;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

public interface IMessageService {

    String IMPORT_QUEUE_NAME = "edi_import_queue";
    String EXPORT_QUEUE_NAME = "edi_export_queue";
    String ERROR_QUEUE_NAME = "edi_error_queue";
    String BILLING_QUEUE_NAME = "edi_billing_queue";


    <TMessageType> boolean publish(String queueName, TMessageType messageObject);

    <TMessageType> Closeable subscribe(String queueName, int threadCount, Class<TMessageType> messageClass, Function<TMessageType, Boolean> callback);

    <TMessageType> Closeable subscribeBatch(String queueName, int batchSize, Class<TMessageType> messageClass, Function<List<TMessageType>, Boolean> callback);

}
