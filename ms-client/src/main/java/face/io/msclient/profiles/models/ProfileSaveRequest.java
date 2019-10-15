package face.io.msclient.profiles.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSaveRequest {

    private String id;

    private String username;

    private boolean isPrivate;

    private boolean isBusiness;

    private String businessCategoryName;

    private boolean isVerified;

    private String profilePicUrlHd;

    private String fullName;

    private String connectedFbPage;

    private String externalUrl;

    private String externalUrlLinkshimmed;

    private boolean isJoinedRecently;

    private int edgeFollowBy;

    private int edgeFollower;

    private int edgeOwnerToTimelineMedia;
}
