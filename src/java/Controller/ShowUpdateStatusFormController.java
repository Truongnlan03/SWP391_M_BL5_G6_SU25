/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.SQLException;

import DAOs.ApplicationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Application;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ShowUpdateStatusFormController", urlPatterns = {"/show-update-status-form"})
public class ShowUpdateStatusFormController extends HttpServlet {

    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"recruiter".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Recruiter recruiter = (Recruiter) session.getAttribute("user");
        
        String applicationIdStr = request.getParameter("applicationId");
        
        if (applicationIdStr == null || applicationIdStr.trim().isEmpty()) {
            session.setAttribute("error", "Không tìm thấy ID ứng tuyển.");
            response.sendRedirect("applications");
            return;
        }
        
        try {
            int applicationId = Integer.parseInt(applicationIdStr.trim());
            
            Application application = applicationDAO.getApplicationById(applicationId, recruiter.getId());
            
            if (application == null) {
                session.setAttribute("error", "Không tìm thấy đơn ứng tuyển hoặc bạn không có quyền truy cập.");
                response.sendRedirect("applications");
                return;
            }
            
            request.setAttribute("application", application);
            
            request.getRequestDispatcher("/update-application-status.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID ứng tuyển không hợp lệ.");
            response.sendRedirect("applications");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra khi tải thông tin ứng tuyển.");
            response.sendRedirect("applications");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 