package social.tochka.common.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String userId;

    private String userName;

    private String userAccount;

    private String userRoleId;

    private Map<String, Map<String, Boolean>> userRights;
}
