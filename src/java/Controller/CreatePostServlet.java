package Controller;

import DAOs.PostDAO;
import Models.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/create-post")
public class CreatePostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy dữ liệu từ form
        String title = request.getParameter("title");
        String salary = request.getParameter("salary");
        String location = request.getParameter("location");
        String jobType = request.getParameter("jobType");
        String experience = request.getParameter("experience");
        String jobDescription = request.getParameter("jobDescription");
        String requirements = request.getParameter("requirements");
        String benefits = request.getParameter("benefits");
        String contactAddress = request.getParameter("contactAddress");
        String applicationMethod = request.getParameter("applicationMethod");
        String quantityStr = request.getParameter("quantity");
        String deadlineStr = request.getParameter("deadline");

        // Validate dữ liệu bắt buộc
        if (isNullOrEmpty(title) || isNullOrEmpty(jobType) ||
            isNullOrEmpty(jobDescription) || isNullOrEmpty(salary) ||
            isNullOrEmpty(location)) {

            request.setAttribute("error", "Vui lòng nhập đầy đủ các thông tin bắt buộc!");
            request.getRequestDispatcher("create-post.jsp").forward(request, response);
            return;
        }

        // Chuyển đổi kiểu dữ liệu
        java.sql.Date deadline = null;
        if (!isNullOrEmpty(deadlineStr)) {
            try {
                deadline = java.sql.Date.valueOf(deadlineStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Định dạng ngày không hợp lệ (yyyy-MM-dd).");
                request.getRequestDispatcher("create-post.jsp").forward(request, response);
                return;
            }
        }

        Integer quantity = null;
        if (!isNullOrEmpty(quantityStr)) {
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException ignored) {}
        }

        // Lấy tên công ty từ session
        String companyName = (String) session.getAttribute("company_name");
        if (isNullOrEmpty(companyName)) {
            companyName = "Chưa cập nhật";
        }

        // Tạo đối tượng Post
        Post post = new Post();
        post.setUserId(userId);
        post.setUserType("employer");
        post.setPostType("job");
        post.setTitle(title);
        post.setStatus("active");
        post.setCompanyName(companyName);
        post.setSalary(salary);
        post.setLocation(location);
        post.setJobType(jobType);
        post.setExperience(experience);
        post.setDeadline(deadline);
        post.setJobDescription(jobDescription);
        post.setRequirements(requirements);
        post.setBenefits(benefits);
        post.setContactAddress(contactAddress);
        post.setApplicationMethod(applicationMethod);
        post.setQuantity(quantity);

        // Lưu vào DB
        PostDAO dao = new PostDAO();
        boolean success = dao.insertPost(post);

        if (success) {
            response.sendRedirect("viewpost.jsp");
        } else {
            request.setAttribute("error", "Không thể tạo bài đăng. Vui lòng thử lại.");
            request.getRequestDispatcher("create-post.jsp").forward(request, response);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
