package social.tochka.ms.client.message.models.transacitons;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageListResponse {

    private boolean isLast;

    private String lastDateTime;

    private List<MessageListItem> transactions;
}
