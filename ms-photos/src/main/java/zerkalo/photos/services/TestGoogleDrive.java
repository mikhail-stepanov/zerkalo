package zerkalo.photos.services;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
public class TestGoogleDrive {

    @RequestMapping(value = "/drive/test", method = RequestMethod.GET)
    private void test() throws IOException, GeneralSecurityException {

        Drive driveService = GoogleDriveService.getDriveService();

        File folderMetadata = new File();
        folderMetadata.setName("test");
        folderMetadata.setMimeType("application/vnd.google-apps.folder");

        File folder = driveService.files().create(folderMetadata)
                .setFields("id")
                .execute();
        String folderId = folder.getId();

        File fileMetadata = new File();
        fileMetadata.setName("mudak.jpg");
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File("C:/test/mudak.jpg");
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        File file = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();

        System.out.println("File ID: " + file.getId());


//        File mediaFile = new File();
//        mediaFile.setName("mudak.jpg");
//
//        // setting the id of folder to which the file must be inserted to
////        mediaFile.setParents(Collections.singletonList("photos"));
//        mediaFile.setMimeType("image/jpeg");
//
//        java.io.File filePath = new java.io.File("C:/test/mudak.jpg");
//        FileContent mediaContent = new FileContent("image/jpeg", filePath);
//
//
//        driveService.files().create(mediaFile, mediaContent)
//                .setFields("id")
//                .setIgnoreDefaultVisibility(false)
//                .execute();
    }
}
