/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import DAOs.ApplicationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Application;
import Models.JobSeeker;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
@WebServlet(name = "DownloadCVController", urlPatterns = {"/download-cv"})
public class DownloadCVController extends HttpServlet {

    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login to access this resource");
            return;
        }

        String applicationId = request.getParameter("applicationId");

        if (applicationId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing application ID");
            return;
        }

        try {
            Application application = null;

            if ("recruiter".equals(role)) {
                Recruiter recruiter = (Recruiter) user;
                application = applicationDAO.getApplicationById(Integer.parseInt(applicationId), recruiter.getId());
            } else if ("job-seeker".equals(role)) {
                JobSeeker jobSeeker = (JobSeeker) user;
                application = applicationDAO.getApplicationByIdForJobSeeker(Integer.parseInt(applicationId), jobSeeker.getId());
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid user role");
                return;
            }

            if (application == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Application not found or access denied");
                return;
            }

            String cvFilePath = application.getCvFile();
            if (cvFilePath == null || cvFilePath.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file not found");
                return;
            }

            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "cvs";
            File cvFile = new File(uploadPath, cvFilePath);

            if (!cvFile.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file does not exist on server at path: " + cvFile.getAbsolutePath());
                return;
            }

            String contentType = getServletContext().getMimeType(cvFile.getName());
            if (contentType == null) {
                if (cvFile.getName().toLowerCase().endsWith(".pdf")) {
                    contentType = "application/pdf";
                } else {
                    contentType = "application/octet-stream";
                }
            }

            response.setContentType(contentType);
            response.setContentLength((int) cvFile.length());

            if (contentType.equals("application/pdf")) {
                response.setHeader("Content-Disposition", "inline; filename=\"" + cvFile.getName() + "\"");
            } else {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + cvFile.getName() + "\"");
            }

            try (FileInputStream fileInputStream = new FileInputStream(cvFile); OutputStream outputStream = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid application ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading CV file");
        }
    }
}
