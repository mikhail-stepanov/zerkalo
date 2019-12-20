package zerkalo.msclient.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificateResponse {

    /**
     * Certificate identifier
     */
    private String id;

    /**
     * Certificate name
     */
    private String cn;

    /**
     * Certificate thumbprint
     */
    private String thumbprint;
}
