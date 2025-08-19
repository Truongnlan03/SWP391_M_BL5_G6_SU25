/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.CVTemplateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

@WebServlet(name = "UploadCVController", urlPatterns = {"/upload-cv"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UploadCVController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("cvFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (!fileName.toLowerCase().endsWith(".pdf")
                && !fileName.toLowerCase().endsWith(".doc")
                && !fileName.toLowerCase().endsWith(".docx")) {
            request.setAttribute("error", "Chỉ được upload PDF hoặc DOC/DOCX");
            request.getRequestDispatcher("list-cv.jsp").forward(request, response);
            return;
        }

        // Tạo thư mục nếu chưa có
        String uploadPath = getServletContext().getRealPath("/") + "uploads/cv";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String savedPath = uploadPath + File.separator + fileName;
        filePart.write(savedPath);

        String fileUrl = request.getContextPath() + "/uploads/cv/" + fileName;

        int userId = (Integer) request.getSession().getAttribute("userId");
        CVTemplateDAO dao = new CVTemplateDAO();
        boolean success = dao.updateCVLink(userId, fileUrl,0);

        if (success) {
            response.sendRedirect("list-cv?success=upload");
        } else {
            request.setAttribute("error", "Không thể lưu link CV vào cơ sở dữ liệu");
            request.getRequestDispatcher("listCV.jsp").forward(request, response);
        }
    }
}
