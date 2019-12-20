package zerkalo.profiles.services;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zerkalo.common.services.MessageService;
import zerkalo.msclient.message.interfaces.IMessageService;
import zerkalo.msclient.profiles.models.ProfileCollectRequest;
import zerkalo.msclient.profiles.models.ProfileSaveRequest;
import zerkalo.profiles.providers.ProfileQueryBuilder;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.util.List;

@Service
public class ProfileBatchSaver {

    @Value("${profiles.saver.batch-size:#{50}}")
    private Integer batchSize;

    @Autowired
    private IMessageService messageService;

    private Closeable queueSubscriber;

    @Autowired
    private RestHighLevelClient elasticClient;

    @Autowired
    private ProfileQueryBuilder queryBuilder;

    private static final Logger logger = LoggerFactory.getLogger(ProfileBatchSaver.class);

    public void start() {
        queueSubscriber = messageService.subscribeBatch(MessageService.SAVE_PROFILE_QUEUE, batchSize, ProfileSaveRequest.class, this::handleMessage);
    }

    public void stop() {
        try {
            if (queueSubscriber != null)
                queueSubscriber.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @PreDestroy
    private void destroy() {
        try {
            if (queueSubscriber != null)
                queueSubscriber.close();
            if (elasticClient != null)
                elasticClient.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private boolean handleMessage(List<ProfileSaveRequest> messages) {
        try {
            if (!messages.isEmpty()) {

                messages.forEach(message -> messageService.publish(MessageService.ID_TO_COLLECT_QUEUE, ProfileCollectRequest.builder().id(message.getId())));

                BulkRequest bulkRequest = queryBuilder.insert()
                        .messages(messages)
                        .build();

                elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT);

                return true;
            } else return false;
        } catch (Exception ex) {
            logger.error("Exception while processing message from queue: " + ex.getLocalizedMessage());
            return false;
        }
    }
}
