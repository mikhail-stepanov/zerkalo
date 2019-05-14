package social.tochka.ms.client.message.models.transacitons;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MessageSendResponse {

    private UUID transactionId;
}
