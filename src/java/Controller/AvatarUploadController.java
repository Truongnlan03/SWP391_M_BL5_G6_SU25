/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAOs.JobSeekerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Models.JobSeeker;

/**
 *
 * @author DELL
 */
@WebServlet(name = "AvatarUploadController", urlPatterns = {"/avatar-upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AvatarUploadController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AvatarUploadController.class.getName());
    private static final String UPLOAD_DIR = "uploads/avatars";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    private JobSeekerDAO jobSeekerDAO;

    @Override
    public void init() throws ServletException {
        jobSeekerDAO = new JobSeekerDAO();

        try {
            String realPath = getServletContext().getRealPath("/");
            Path uploadPath = Paths.get(realPath, UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to create avatar upload directory", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
            response.sendRedirect("login");
            return;
        }

        JobSeeker jobSeeker = (JobSeeker) session.getAttribute("user");

        Part avatarPart = request.getPart("avatarFile");
        if (avatarPart == null || avatarPart.getSize() == 0) {
            session.setAttribute("errorMessage", "Vui lòng chọn file ảnh!");
            response.sendRedirect("profilejobseeker");
            return;
        }

        String submittedFileName = getSubmittedFileName(avatarPart).toLowerCase();
        if (!(submittedFileName.endsWith(".png") || submittedFileName.endsWith(".jpg") || submittedFileName.endsWith(".jpeg"))) {
            session.setAttribute("errorMessage", "Chỉ chấp nhận ảnh PNG hoặc JPG!");
            response.sendRedirect("profilejobseeker");
            return;
        }

        if (avatarPart.getSize() > MAX_FILE_SIZE) {
            session.setAttribute("errorMessage", "Ảnh không được vượt quá 5MB!");
            response.sendRedirect("profilejobseeker");
            return;
        }

        String extension = submittedFileName.substring(submittedFileName.lastIndexOf('.'));
        String uniqueFile = UUID.randomUUID().toString() + extension;

        try {
            String realPath = getServletContext().getRealPath("/");
            Path avatarPath = Paths.get(realPath, UPLOAD_DIR, uniqueFile);
            avatarPart.write(avatarPath.toString());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error saving avatar", ex);
            session.setAttribute("errorMessage", "Lỗi khi upload ảnh!");
            response.sendRedirect("profilejobseeker");
            return;
        }

        String storedPath = UPLOAD_DIR + "/" + uniqueFile;

        boolean updated = jobSeekerDAO.updateProfilePicture(jobSeeker.getId(), storedPath);
        if (updated) {
            jobSeeker.setProfilePicture(storedPath);
            session.setAttribute("user", jobSeeker);
            session.setAttribute("successMessage", "Cập nhật avatar thành công!");
        } else {
            session.setAttribute("errorMessage", "Không thể cập nhật avatar. Vui lòng thử lại!");
        }

        response.sendRedirect("profilejobseeker");
    }

    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");
        for (String content : header.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
