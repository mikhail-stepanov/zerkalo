package face.io.msclient.message.interfaces;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

public interface IMessageService {

    String QUEUE_NAME = "edi_import_queue";

    <TMessageType> boolean publish(String queueName, TMessageType messageObject);

    <TMessageType> Closeable subscribe(String queueName, int threadCount, Class<TMessageType> messageClass, Function<TMessageType, Boolean> callback);

    <TMessageType> Closeable subscribeBatch(String queueName, int batchSize, Class<TMessageType> messageClass, Function<List<TMessageType>, Boolean> callback);

}
