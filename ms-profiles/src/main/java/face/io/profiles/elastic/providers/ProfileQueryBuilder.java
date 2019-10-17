package face.io.profiles.elastic.providers;

import face.io.msclient.profiles.models.ProfileSaveRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProfileQueryBuilder {

    @Value("${profiles.saver.elastic.index.profiles}")
    private String elasticIndex;

    public InsertQueryBuilder insert() {
        return new InsertQueryBuilder();
    }

    public DeleteQueryBuilder delete() {
        return new DeleteQueryBuilder();
    }

    public class InsertQueryBuilder {

        private List<ProfileSaveRequest> messages;

        InsertQueryBuilder() {
        }

        public InsertQueryBuilder messages(List<ProfileSaveRequest> messages) {
            this.messages = messages;
            return this;
        }

        public BulkRequest build() throws IOException {
            BulkRequest request = new BulkRequest();
            for (ProfileSaveRequest message : messages) {

                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.field("id", message.getId());
                    builder.field("username", message.getUsername());
                    builder.field("isPrivate", message.isPrivate());
                    builder.field("isBusiness", message.isBusiness());
                    builder.field("businessCategoryName", message.getBusinessCategoryName());
                    builder.field("isVerified", message.isVerified());
                    builder.field("profilePicUrlHd", message.getProfilePicUrlHd());
                    builder.field("fullName", message.getFullName());
                    builder.field("connectedFbPage", message.getConnectedFbPage());
                    builder.field("externalUrl", message.getExternalUrl());
                    builder.field("externalUrlLinkshimmed", message.getExternalUrlLinkshimmed());
                    builder.field("isJoinedRecently", message.isJoinedRecently());
                    builder.field("edgeFollowBy", message.getEdgeFollowBy());
                    builder.field("edgeFollower", message.getEdgeFollower());
                    builder.field("edgeOwnerToTimelineMedia", message.getEdgeOwnerToTimelineMedia());
                }
                builder.endObject();
                request.add(new IndexRequest(elasticIndex).id(message.getId()).source(builder));
            }
            return request;
        }
    }

    public class DeleteQueryBuilder {

        DeleteQueryBuilder() {
        }

        public DeleteIndexRequest build() {
            return new DeleteIndexRequest(elasticIndex);
        }
    }
}
