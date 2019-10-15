package face.io.msclient.message.interfaces;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

public interface IMessageService {

    String SAVE_PROFILE_QUEUE = "save_profile_queue";
    String ID_TO_COLLECT_QUEUE = "id_to_collect_queue";

    <TMessageType> boolean publish(String queueName, TMessageType messageObject);

    <TMessageType> Closeable subscribe(String queueName, int threadCount, Class<TMessageType> messageClass, Function<TMessageType, Boolean> callback);

    <TMessageType> Closeable subscribeBatch(String queueName, int batchSize, Class<TMessageType> messageClass, Function<List<TMessageType>, Boolean> callback);

}
