/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

/**
 *
 * @author DELL
 */
public class UploadPicture {

    public static String uploadImage(Part filePart, String oldImageURL, String basePath) throws ServletException {
        String res = oldImageURL;
        String uploadDir = basePath;
        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        uploadDir += "uploads";
        try {
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String extension = "";
            int dotIndex = submittedFileName.lastIndexOf('.');
            if (dotIndex >= 0) {
                extension = submittedFileName.substring(dotIndex);
            }
            String randomFileName = UUID.randomUUID().toString() + extension;
            File fileToSave = new File(uploadFolder, randomFileName);
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            res = "uploads/" + randomFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
