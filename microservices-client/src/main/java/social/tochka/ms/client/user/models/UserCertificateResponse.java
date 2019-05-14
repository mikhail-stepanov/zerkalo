package social.tochka.ms.client.user.models;

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
     * Идентификатор сертификата
     */
    private String id;

    /**
     * Общее имя сертификата
     */
    private String cn;

    /**
     * Отпечаток сертификата
     */
    private String thumbprint;
}
