/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import DAOs.EmailHistoryDAO;
import DAOs.EmailTemplateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.EmailHistory;
import Models.EmailTemplate;
import Models.Recruiter;
import Utils.EmailService;
import Utils.JavaMail;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SendCustomEmailController", urlPatterns = {"/send-custom-email"})
public class SendCustomEmailController extends HttpServlet {

    private EmailService emailService;
    private EmailTemplateDAO emailTemplateDAO;
    private EmailHistoryDAO emailHistoryDAO;

    @Override
    public void init() throws ServletException {
    }

    private void initializeIfNeeded() {
        try {
            if (emailTemplateDAO == null) {
                emailTemplateDAO = new EmailTemplateDAO();
            }
            if (emailHistoryDAO == null) {
                emailHistoryDAO = new EmailHistoryDAO();
            }
            if (emailService == null) {
                emailService = new EmailService();
            }
        } catch (Exception e) {
            System.err.println("Error in initializeIfNeeded: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        initializeIfNeeded();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"recruiter".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Recruiter recruiter = (Recruiter) session.getAttribute("user");

        String recipientEmail = request.getParameter("recipientEmail");
        String candidateName = request.getParameter("candidateName");
        String jobTitle = request.getParameter("jobTitle");
        String companyName = request.getParameter("companyName");
        String applicationId = request.getParameter("applicationId");

        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            session.setAttribute("error", "Email người nhận không được để trống.");
            response.sendRedirect("applications");
            return;
        }

        request.setAttribute("recipientEmail", recipientEmail);
        request.setAttribute("candidateName", candidateName);
        request.setAttribute("jobTitle", jobTitle);
        request.setAttribute("companyName", companyName);
        request.setAttribute("applicationId", applicationId);
        request.setAttribute("recruiterName", recruiter.getFullName());

        request.getRequestDispatcher("/send-email.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        initializeIfNeeded();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"recruiter".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Recruiter recruiter = (Recruiter) session.getAttribute("user");

        String recipientEmail = request.getParameter("recipientEmail");
        String candidateName = request.getParameter("candidateName");
        String jobTitle = request.getParameter("jobTitle");
        String companyName = request.getParameter("companyName");
        String applicationIdStr = request.getParameter("applicationId");
        String emailType = request.getParameter("emailType");
        String subject = request.getParameter("subject");
        String emailContent = request.getParameter("emailContent");
        String emailContentHidden = request.getParameter("emailContentHidden");

        if ((emailContent == null || emailContent.trim().isEmpty())
                && emailContentHidden != null && !emailContentHidden.trim().isEmpty()) {
            emailContent = emailContentHidden;
        }

        System.out.println("Form data received:");
        System.out.println("emailType: " + emailType);
        System.out.println("subject: " + subject);
        System.out.println("emailContent length: " + (emailContent != null ? emailContent.length() : "null"));
        System.out.println("emailContentHidden length: " + (emailContentHidden != null ? emailContentHidden.length() : "null"));

        String interviewDate = request.getParameter("interviewDate");
        String interviewTime = request.getParameter("interviewTime");
        String interviewLocation = request.getParameter("interviewLocation");
        String interviewerName = request.getParameter("interviewerName");
        String interviewType = request.getParameter("interviewType");
        String interviewDuration = request.getParameter("interviewDuration");

        String reminderInterviewDate = request.getParameter("reminderInterviewDate");
        String reminderInterviewTime = request.getParameter("reminderInterviewTime");
        String reminderLocation = request.getParameter("reminderLocation");
        String reminderInterviewer = request.getParameter("reminderInterviewer");
        String reminderNotes = request.getParameter("reminderNotes");

        String rejectionReason = request.getParameter("rejectionReason");
        String customRejectionReason = request.getParameter("customRejectionReason");
        String futureOpportunities = request.getParameter("futureOpportunities");

        String salaryOffer = request.getParameter("salaryOffer");
        String startDate = request.getParameter("startDate");
        String workingTime = request.getParameter("workingTime");
        String workLocation = request.getParameter("workLocation");
        String responseDeadline = request.getParameter("responseDeadline");
        String benefits = request.getParameter("benefits");
        String offerNotes = request.getParameter("offerNotes");

        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            session.setAttribute("error", "Email người nhận không được để trống.");
            response.sendRedirect("applications");
            return;
        }

        if (emailType == null || emailType.trim().isEmpty()) {
            session.setAttribute("error", "Vui lòng chọn loại email.");
            response.sendRedirect("send-email?recipientEmail=" + recipientEmail + "&candidateName=" + candidateName + "&jobTitle=" + jobTitle + "&companyName=" + companyName + "&applicationId=" + applicationIdStr);
            return;
        }

        try {
            boolean emailSent = false;
            String successMessage = "";

            List<EmailTemplate> templates = emailTemplateDAO.getTemplatesByType(emailType.trim());
            if (templates.isEmpty()) {
                session.setAttribute("error", "Không tìm thấy template email cho loại này.");
                response.sendRedirect("send-email?recipientEmail=" + recipientEmail + "&candidateName=" + candidateName + "&jobTitle=" + jobTitle + "&companyName=" + companyName + "&applicationId=" + applicationIdStr);
                return;
            }

            EmailTemplate template = templates.get(0);

            String processedSubject = processTemplate(template.getSubject(),
                    candidateName, jobTitle, companyName, recruiter.getFullName(),
                    interviewDate, interviewTime, interviewLocation, interviewerName, interviewType, interviewDuration,
                    reminderInterviewDate, reminderInterviewTime, reminderLocation, reminderInterviewer, reminderNotes,
                    rejectionReason, customRejectionReason, futureOpportunities,
                    salaryOffer, startDate, workingTime, workLocation, responseDeadline, benefits, offerNotes,
                    subject, emailContent);
            String processedContent = processTemplate(template.getBodyHtml(),
                    candidateName, jobTitle, companyName, recruiter.getFullName(),
                    interviewDate, interviewTime, interviewLocation, interviewerName, interviewType, interviewDuration,
                    reminderInterviewDate, reminderInterviewTime, reminderLocation, reminderInterviewer, reminderNotes,
                    rejectionReason, customRejectionReason, futureOpportunities,
                    salaryOffer, startDate, workingTime, workLocation, responseDeadline, benefits, offerNotes,
                    subject, emailContent);

            emailSent = JavaMail.sendEmail(recipientEmail, processedSubject, processedContent);
            successMessage = "Email đã được gửi thành công!";

            saveEmailHistory(recipientEmail, processedSubject, processedContent,
                    applicationIdStr, template.getTemplateName(), emailType.trim());

            if (emailSent) {
                session.setAttribute("success", successMessage);
            } else {
                session.setAttribute("error", "Không thể gửi email. Vui lòng thử lại sau.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in SendCustomEmailController: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getName());
            session.setAttribute("error", "Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }

        response.sendRedirect("applications");
    }

    private String processTemplate(String template, String candidateName, String jobTitle, String companyName, String recruiterName,
            String interviewDate, String interviewTime, String interviewLocation, String interviewerName, String interviewType, String interviewDuration,
            String reminderInterviewDate, String reminderInterviewTime, String reminderLocation, String reminderInterviewer, String reminderNotes,
            String rejectionReason, String customRejectionReason, String futureOpportunities,
            String salaryOffer, String startDate, String workingTime, String workLocation, String responseDeadline, String benefits, String offerNotes,
            String subject, String emailContent) {
        String result = template;

        result = result.replace("{{candidateName}}", candidateName != null ? candidateName : "");
        result = result.replace("{{jobTitle}}", jobTitle != null ? jobTitle : "");
        result = result.replace("{{companyName}}", companyName != null ? companyName : "");
        result = result.replace("{{recruiterName}}", recruiterName != null ? recruiterName : "");
        result = result.replace("{{applicationDate}}", new Timestamp(System.currentTimeMillis()).toString());

        result = result.replace("{{interviewDate}}", interviewDate != null ? interviewDate : "");
        result = result.replace("{{interviewTime}}", interviewTime != null ? interviewTime : "");
        result = result.replace("{{location}}", interviewLocation != null ? interviewLocation : "Văn phòng công ty");
        result = result.replace("{{interviewerName}}", interviewerName != null ? interviewerName : "Người phỏng vấn");
        result = result.replace("{{interviewType}}", interviewType != null ? interviewType : "Phỏng vấn trực tiếp");
        result = result.replace("{{duration}}", interviewDuration != null ? interviewDuration : "60");

        result = result.replace("{{reminderInterviewDate}}", reminderInterviewDate != null ? reminderInterviewDate : "");
        result = result.replace("{{reminderInterviewTime}}", reminderInterviewTime != null ? reminderInterviewTime : "");
        result = result.replace("{{reminderLocation}}", reminderLocation != null ? reminderLocation : "Văn phòng công ty");
        result = result.replace("{{reminderInterviewer}}", reminderInterviewer != null ? reminderInterviewer : "Người phỏng vấn");
        result = result.replace("{{reminderNotes}}", reminderNotes != null ? reminderNotes : "");

        String finalRejectionReason = (rejectionReason != null && rejectionReason.equals("Khác"))
                ? (customRejectionReason != null ? customRejectionReason : "Không phù hợp với yêu cầu công việc")
                : (rejectionReason != null ? rejectionReason : "Không phù hợp với yêu cầu công việc");
        result = result.replace("{{rejectionReason}}", finalRejectionReason);
        result = result.replace("{{futureOpportunities}}", futureOpportunities != null ? futureOpportunities : "");

        result = result.replace("{{salaryOffer}}", salaryOffer != null ? salaryOffer : "Thỏa thuận");
        result = result.replace("{{startDate}}", startDate != null ? startDate : "");
        result = result.replace("{{workingTime}}", workingTime != null ? workingTime : "8 giờ/ngày");
        result = result.replace("{{workLocation}}", workLocation != null ? workLocation : "Văn phòng công ty");
        result = result.replace("{{responseDeadline}}", responseDeadline != null ? responseDeadline : "7");
        result = result.replace("{{benefits}}", benefits != null ? benefits : "");
        result = result.replace("{{offerNotes}}", offerNotes != null ? offerNotes : "");

        result = result.replace("{{subject}}", subject != null ? subject : "");
        result = result.replace("{{emailContent}}", emailContent != null ? emailContent : "");

        return result;
    }

    private void saveEmailHistory(String recipientEmail, String subject, String content,
            String applicationIdStr, String templateName, String emailType) {
        try {
            EmailHistory emailHistory = new EmailHistory();

            if (applicationIdStr != null && !applicationIdStr.trim().isEmpty()) {
                emailHistory.setApplicationId(Integer.parseInt(applicationIdStr.trim()));
            }

            emailHistory.setTemplateName(templateName);
            emailHistory.setRecipientEmail(recipientEmail);
            emailHistory.setSubject(subject);
            emailHistory.setBodyHtml(content);
            emailHistory.setStatus("sent");
            emailHistory.setSentAt(new Timestamp(System.currentTimeMillis()));
            emailHistory.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            emailHistoryDAO.saveEmailHistory(emailHistory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
