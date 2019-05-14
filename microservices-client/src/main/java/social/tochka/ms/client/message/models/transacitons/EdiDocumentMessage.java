package social.tochka.ms.client.message.models.transacitons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiDocumentMessage implements Serializable {

    private String accountId;

    private String objectId;

    private String transportType;

    private String folderName;
}
