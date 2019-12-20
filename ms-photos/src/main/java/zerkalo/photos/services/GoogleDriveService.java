package zerkalo.photos.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {

    private static final String APPLICATION_NAME = "FaceIO";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    // setting the Drive scope since it is essential to access Team Drive
    private static List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials_own.json";

    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);

    // create and return credential
    private static Credential getCredentials() throws IOException {
        return GoogleCredential.fromStream(GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH))
                .createScoped(SCOPES);
    }

    // build and return an authorized drive client service
    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Instantiating a client
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}