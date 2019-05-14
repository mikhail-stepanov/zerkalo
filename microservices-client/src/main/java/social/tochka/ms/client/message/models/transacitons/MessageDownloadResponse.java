package social.tochka.ms.client.message.models.transacitons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDownloadResponse {

    private String fileName;

    private Long fileSize;

    private InputStream fileStream;

    private Map<String, String> parameters;
}
