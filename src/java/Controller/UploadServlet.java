/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.Map;

/**
 *
 * @author DELL
 */
@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlx6r02ap",
                "api_key", "476379442332742",
                "api_secret", "AP6fsHOrKHhHILISZmpUiUZCLIY"
        ));

        try {
            Part filePart = request.getPart("file");

            // Lưu file tạm
            File tempFile = File.createTempFile("upload_", ".tmp");
            try (InputStream fileContent = filePart.getInputStream(); FileOutputStream outStream = new FileOutputStream(tempFile)) {
                fileContent.transferTo(outStream);
            }

            Map result = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            ));
            String imageUrl = result.get("secure_url").toString();
            tempFile.delete();

        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
