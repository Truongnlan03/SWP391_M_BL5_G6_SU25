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
import Utils.EmailService;

/**
 *
 * @author DELL
 */
@WebServlet(name = "UpdateApplicationStatusController", urlPatterns = {"/update-application-status"})
public class UpdateApplicationStatusController extends HttpServlet {

    private ApplicationDAO applicationDAO;
    private EmailService emailService;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
        emailService = new EmailService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"recruiter".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Recruiter recruiter = (Recruiter) session.getAttribute("user");

        String applicationIdStr = request.getParameter("applicationId");
        String status = request.getParameter("status");
        String action = request.getParameter("action");
        String rejectionReason = request.getParameter("rejectionReason");
        String offerDetails = request.getParameter("offerDetails");

        if (applicationIdStr == null || applicationIdStr.trim().isEmpty()) {
            session.setAttribute("error", "Không tìm thấy ID ứng tuyển.");
            response.sendRedirect("applications");
            return;
        }

        if (status == null || status.trim().isEmpty()) {
            session.setAttribute("error", "Vui lòng chọn trạng thái.");
            response.sendRedirect("applications");
            return;
        }

        String[] validStatuses = {"new", "reviewed", "interviewed", "offered", "rejected"};
        boolean isValidStatus = false;
        for (String validStatus : validStatuses) {
            if (validStatus.equals(status.trim())) {
                isValidStatus = true;
                break;
            }
        }

        if (!isValidStatus) {
            session.setAttribute("error", "Trạng thái không hợp lệ.");
            response.sendRedirect("applications");
            return;
        }

        if ("rejected".equals(status.trim()) && (rejectionReason == null || rejectionReason.trim().isEmpty())) {
            session.setAttribute("error", "Vui lòng nhập lý do từ chối.");
            response.sendRedirect("applications");
            return;
        }

        if ("offered".equals(status.trim()) && (offerDetails == null || offerDetails.trim().isEmpty())) {
            session.setAttribute("error", "Vui lòng nhập chi tiết đề nghị.");
            response.sendRedirect("applications");
            return;
        }

        try {
            int applicationId = Integer.parseInt(applicationIdStr.trim());

            Application application = applicationDAO.getApplicationById(applicationId, recruiter.getId());
            if (application == null) {
                session.setAttribute("error", "Không tìm thấy đơn ứng tuyển hoặc bạn không có quyền.");
                response.sendRedirect("applications");
                return;
            }

            boolean updated = applicationDAO.updateApplicationStatus(applicationId, status.trim(), recruiter.getId());

            if (updated) {
                boolean emailSent = false;
                String emailMessage = "";

                switch (status.trim()) {
                    case "reviewed":
                        emailSent = emailService.sendApplicationReviewedEmailByEmail(
                                application.getJobseeker().getEmail(),
                                application.getJobseeker().getFullName(),
                                application.getPost().getTitle(),
                                application.getPost().getCompanyName(),
                                applicationId
                        );
                        emailMessage = "Email thông báo đã xem hồ sơ đã được gửi.";
                        break;

                    case "interviewed":
                        emailSent = emailService.sendInterviewCompletedEmailByEmail(
                                application.getJobseeker().getEmail(),
                                application.getJobseeker().getFullName(),
                                application.getPost().getTitle(),
                                application.getPost().getCompanyName(),
                                applicationId
                        );
                        emailMessage = "Email cảm ơn tham gia phỏng vấn đã được gửi.";
                        break;

                    case "rejected":
                        emailSent = emailService.sendRejectionEmailByEmail(
                                application.getJobseeker().getEmail(),
                                application.getJobseeker().getFullName(),
                                application.getPost().getTitle(),
                                application.getPost().getCompanyName(),
                                rejectionReason,
                                applicationId
                        );
                        emailMessage = "Email từ chối đã được gửi.";
                        break;

                    case "offered":
                        emailSent = emailService.sendAcceptanceEmailByEmail(
                                application.getJobseeker().getEmail(),
                                application.getJobseeker().getFullName(),
                                application.getPost().getTitle(),
                                application.getPost().getCompanyName(),
                                offerDetails,
                                applicationId
                        );
                        emailMessage = "Email chấp nhận đã được gửi.";
                        break;

                    default:
                        emailSent = false;
                        emailMessage = "";
                        break;
                }

                String statusDisplay = "";
                switch (status.trim()) {
                    case "new":
                        statusDisplay = "Mới";
                        break;
                    case "reviewed":
                        statusDisplay = "Đã xem";
                        break;
                    case "interviewed":
                        statusDisplay = "Phỏng vấn";
                        break;
                    case "offered":
                        statusDisplay = "Mời nhận việc";
                        break;
                    case "rejected":
                        statusDisplay = "Từ chối";
                        break;
                    default:
                        statusDisplay = status;
                }

                String successMessage = "Đã cập nhật trạng thái thành \"" + statusDisplay + "\" thành công!";
                if (emailSent && !emailMessage.isEmpty()) {
                    successMessage += " " + emailMessage;
                    session.setAttribute("emailSent", true);
                }

                session.setAttribute("success", successMessage);
            } else {
                session.setAttribute("error", "Không thể cập nhật trạng thái. Ứng tuyển không tồn tại hoặc bạn không có quyền.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID ứng tuyển không hợp lệ.");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra khi cập nhật. Vui lòng thử lại.");
        }

        response.sendRedirect("applications");
    }
}
