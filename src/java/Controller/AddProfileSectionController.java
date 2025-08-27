/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class AddProfileSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/experience-add")) {
            request.getRequestDispatcher("experience-add.jsp").forward(request, response);
        } else if (uri.endsWith("/education-add")) {
            request.getRequestDispatcher("education-add.jsp").forward(request, response);
        } else if (uri.endsWith("/certificate-add")) {
            request.getRequestDispatcher("certificate-add.jsp").forward(request, response);
        } else if (uri.endsWith("/award-add")) {
            request.getRequestDispatcher("award-add.jsp").forward(request, response);
        } else if (uri.endsWith("/experience-edit")) {
            handleEditSection(request, response, "experience");
        } else if (uri.endsWith("/certificate-edit")) {
            handleEditSection(request, response, "certificate");
        } else if (uri.endsWith("/education-edit")) {
            handleEditSection(request, response, "education");
        } else if (uri.endsWith("/award-edit")) {
            handleEditSection(request, response, "award");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleEditSection(HttpServletRequest request, HttpServletResponse response, String section) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
            response.sendRedirect("login");
            return;
        }
        Models.JobSeeker jobSeeker = (Models.JobSeeker) session.getAttribute("user");
        try {
            switch (section) {
                case "experience": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    DAOs.ExperienceDAO dao = new DAOs.ExperienceDAO();
                    Models.Experience exp = dao.getExperienceById(id);
                    if (exp == null || exp.getJobSeekerId() != jobSeeker.getId()) {
                        session.setAttribute("errorMessage", "Không tìm thấy kinh nghiệm hoặc bạn không có quyền sửa!");
                        response.sendRedirect("profile");
                        return;
                    }
                    request.setAttribute("exp", exp);
                    request.getRequestDispatcher("experience-edit.jsp").forward(request, response);
                    break;
                }
                case "certificate": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    DAOs.CertificateDAO dao = new DAOs.CertificateDAO();
                    Models.Certificate cert = dao.getCertificateById(id);
                    if (cert == null || cert.getJobSeekerId() != jobSeeker.getId()) {
                        session.setAttribute("errorMessage", "Không tìm thấy chứng chỉ hoặc bạn không có quyền sửa!");
                        response.sendRedirect("profile");
                        return;
                    }
                    request.setAttribute("cert", cert);
                    request.getRequestDispatcher("certificate-edit.jsp").forward(request, response);
                    break;
                }
                case "education": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    DAOs.EducationDAO dao = new DAOs.EducationDAO();
                    Models.Education edu = dao.getEducationById(id);
                    if (edu == null || edu.getJobSeekerId() != jobSeeker.getId()) {
                        session.setAttribute("errorMessage", "Không tìm thấy học vấn hoặc bạn không có quyền sửa!");
                        response.sendRedirect("profile");
                        return;
                    }
                    request.setAttribute("edu", edu);
                    request.getRequestDispatcher("education-edit.jsp").forward(request, response);
                    break;
                }
                case "award": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    DAOs.AwardDAO dao = new DAOs.AwardDAO();
                    Models.Award award = dao.getAwardById(id);
                    if (award == null || award.getJobSeekerId() != jobSeeker.getId()) {
                        session.setAttribute("errorMessage", "Không tìm thấy giải thưởng hoặc bạn không có quyền sửa!");
                        response.sendRedirect("profile");
                        return;
                    }
                    request.setAttribute("award", award);
                    request.getRequestDispatcher("award-edit.jsp").forward(request, response);
                    break;
                }
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi hệ thống khi tải dữ liệu chỉnh sửa!");
            response.sendRedirect("profile");
        }
    }
} 