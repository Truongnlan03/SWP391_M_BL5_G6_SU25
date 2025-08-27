/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAOs.EmailHistoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Admin;
import Models.EmailHistory;
import Utils.Constants;


/**
 *
 * @author DELL
 */
@WebServlet(name = "AdminEmailHistoryController", urlPatterns = {"/admin/email-history"})
public class AdminEmailHistoryController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(AdminEmailHistoryController.class.getName());
    private final EmailHistoryDAO emailHistoryDAO;
    
    public AdminEmailHistoryController() {
        this.emailHistoryDAO = new EmailHistoryDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Kiểm tra quyền admin
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute(Constants.SESSION_USER);
            
            if (admin == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            String pageStr = request.getParameter("page");
            String limitStr = request.getParameter("limit");
            String statusFilter = request.getParameter("status");
            String emailFilter = request.getParameter("email");
            
            int page = 1;
            int limit = 20;
            
            try {
                if (pageStr != null && !pageStr.trim().isEmpty()) {
                    page = Integer.parseInt(pageStr);
                }
                if (limitStr != null && !limitStr.trim().isEmpty()) {
                    limit = Integer.parseInt(limitStr);
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid page or limit parameter: " + e.getMessage());
            }
            
            int offset = (page - 1) * limit;
            
            List<EmailHistory> emailHistory;
            int totalCount;
            
            if (statusFilter != null && !statusFilter.trim().isEmpty()) {
                emailHistory = emailHistoryDAO.getEmailHistoryByStatus(statusFilter);
                totalCount = emailHistoryDAO.getEmailCountByStatus(statusFilter);
            } else if (emailFilter != null && !emailFilter.trim().isEmpty()) {
                emailHistory = emailHistoryDAO.getEmailHistoryByRecipient(emailFilter);
                totalCount = emailHistory.size(); // Simplified
            } else {
                emailHistory = emailHistoryDAO.getAllEmailHistory(offset, limit);
                totalCount = emailHistoryDAO.getTotalEmailCount();
            }
            
            int totalPages = (int) Math.ceil((double) totalCount / limit);
            
            int sentCount = emailHistoryDAO.getEmailCountByStatus("sent");
            int failedCount = emailHistoryDAO.getEmailCountByStatus("failed");
            
            request.setAttribute("emailHistory", emailHistory);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("limit", limit);
            request.setAttribute("sentCount", sentCount);
            request.setAttribute("failedCount", failedCount);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("emailFilter", emailFilter);
            
            request.getRequestDispatcher("/admin_email_history.jsp").forward(request, response);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in AdminEmailHistoryController", e);
            request.setAttribute("error", "Có lỗi xảy ra khi tải lịch sử email");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getRequestURI() + "?" + request.getQueryString());
    }
} 