/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.RecruiterDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
@WebServlet(name = "EditRecruiterProfileController", urlPatterns = {"/edit-recruiter-profile"})
public class EditRecruiterProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Recruiter recruiter = (Recruiter) session.getAttribute("user");

        if (recruiter == null || !session.getAttribute("role").equals("recruiter")) {
            response.sendRedirect("login");
            return;
        }
        request.setAttribute("recruiter", recruiter);
        request.getRequestDispatcher("edit_recruiter_profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Recruiter recruiter = (Recruiter) session.getAttribute("user");

        if (recruiter == null || !session.getAttribute("role").equals("recruiter")) {
            response.sendRedirect("login");
            return;
        }

        try {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dob = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String profilePicture = request.getParameter("profilePicture");
            String companyName = request.getParameter("companyName");
            String companyDescription = request.getParameter("companyDescription");
            String logo = request.getParameter("logo");
            String website = request.getParameter("website");
            String companyAddress = request.getParameter("companyAddress");
            String companySize = request.getParameter("companySize");
            String industry = request.getParameter("industry");

            recruiter.setFullName(fullName);
            recruiter.setPhone(phone);
            if (dob != null && !dob.isEmpty()) {
                recruiter.setDateOfBirth(Date.valueOf(dob));
            }
            recruiter.setGender(gender);
            recruiter.setAddress(address);
            recruiter.setProfilePicture(profilePicture);
            recruiter.setCompanyName(companyName);
            recruiter.setCompanyDescription(companyDescription);
            recruiter.setLogo(logo);
            recruiter.setWebsite(website);
            recruiter.setCompanyAddress(companyAddress);
            recruiter.setCompanySize(companySize);
            recruiter.setIndustry(industry);

            RecruiterDAO recruiterDAO = new RecruiterDAO();
            boolean success = recruiterDAO.updateRecruiter(recruiter);

            if (success) {
                session.setAttribute("user", recruiter);
                response.sendRedirect("recruiter-profile");
            } else {
                request.setAttribute("errorMessage", "Không cập nhật được hồ sơ. Vui lòng thử lại.");
                request.getRequestDispatcher("edit_recruiter_profile.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật hồ sơ.");
            request.getRequestDispatcher("edit_recruiter_profile.jsp").forward(request, response);
        }
    }
}
