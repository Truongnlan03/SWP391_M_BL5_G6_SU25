/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.JobSeeker;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ProfileJobSeekerController", urlPatterns = {"/profilejobseeker"})
public class ProfileJobSeekerController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProfileJobSeekerController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("profile_jobseeker.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action != null ? action : "") {
            case "editContact" ->
                handleEditContact(request, response);
            default ->
                response.sendRedirect("profilejobseeker");
        }
    }

    private void handleEditContact(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
            response.sendRedirect("login");
            return;
        }
        JobSeeker jobSeeker = (JobSeeker) session.getAttribute("user");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        try {
            boolean changed = false;
            if (fullName != null && !fullName.trim().isEmpty()) {
                jobSeeker.setFullName(fullName.trim());
                changed = true;
            }
            if (email != null && !email.trim().isEmpty()) {
                jobSeeker.setEmail(email.trim());
                changed = true;
            }
            if (phone != null) {
                jobSeeker.setPhone(phone.trim());
                changed = true;
            }
            if (address != null) {
                jobSeeker.setAddress(address.trim());
                changed = true;
            }
            if (changed) {
                // Persist changes (assume JobSeekerDAO exists)
                DAOs.JobSeekerDAO jobSeekerDAO = new DAOs.JobSeekerDAO();
                boolean updated = jobSeekerDAO.updateContactInfo(jobSeeker);
                if (updated) {
                    session.setAttribute("user", jobSeeker);
                    session.setAttribute("successMessage", "Thông tin đã được cập nhật thành công!");
                } else {
                    session.setAttribute("errorMessage", "Không cập nhật được thông tin liên hệ. Vui lòng thử lại!");
                }
            } else {
                session.setAttribute("errorMessage", "Không có thay đổi nào trong thông tin liên lạc.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật thông tin liên hệ", e);
            session.setAttribute("errorMessage", "Có lỗi hệ thống khi cập nhật thông tin liên lạc!");
        }
        response.sendRedirect("profilejobseeker");
    }
}
