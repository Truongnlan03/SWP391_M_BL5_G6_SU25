package Controller.managerCv;

import DAOs.CVTemplateDAO;
import Models.CVTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "EditCVServlet", urlPatterns = {"/edit-cv"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class EditCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("list-cv");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("list-cv");
            return;
        }

        CVTemplateDAO dao = new CVTemplateDAO();
        CVTemplate cv = dao.getCVById(id);

        if (cv == null) {
            response.sendRedirect("list-cv");
            return;
        }

        request.setAttribute("cv", cv);
        request.getRequestDispatcher("managerCV/editCV.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("list-cv");
            return;
        }

        int id = Integer.parseInt(idParam);

        String uploadPath = getServletContext().getRealPath("/") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fullName = request.getParameter("full_name");
        String jobPosition = request.getParameter("job_position");
        String birthDate = request.getParameter("birth_date");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String website = request.getParameter("website");
        String address = request.getParameter("address");
        String careerGoal = request.getParameter("career_goal");
        String education = request.getParameter("education");
        String workExperience = request.getParameter("work_experience");
        String certificates = request.getParameter("certificates");

        // Giữ ảnh cũ nếu không upload mới
        String oldImagePath = request.getParameter("old_image_path");
        String imagePath = oldImagePath;

        Part filePart = request.getPart("image_path");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String savePath = uploadPath + File.separator + fileName;
            filePart.write(savePath);
            imagePath = "uploads/" + fileName;
        }

        CVTemplate cv = new CVTemplate();
        cv.setId(id);
        cv.setJobSeekerId((Integer) request.getSession().getAttribute("userId")); // 🔹 thêm dòng này
        cv.setFullName(fullName);
        cv.setJobPosition(jobPosition);
        if (birthDate != null && !birthDate.isEmpty()) {
            cv.setBirthDate(Date.valueOf(birthDate));
        }
        cv.setGender(gender);
        cv.setPhone(phone);
        cv.setEmail(email);
        cv.setWebsite(website);
        cv.setAddress(address);
        cv.setCareerGoal(careerGoal);
        cv.setEducation(education);
        cv.setWorkExperience(workExperience);
        cv.setCertificates(certificates);
        cv.setPdfFilePath(imagePath);

        CVTemplateDAO dao = new CVTemplateDAO();
        boolean updated = dao.updateCV(cv);

        if (updated) {
            response.sendRedirect("list-cv?success=2");
        } else {
            request.setAttribute("error", "Không thể cập nhật CV, vui lòng thử lại!");
            request.setAttribute("cv", cv);
            request.getRequestDispatcher("managerCV/editCV.jsp").forward(request, response);
        }
    }

}
