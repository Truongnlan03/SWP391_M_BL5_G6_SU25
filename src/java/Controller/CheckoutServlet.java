/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.PostPricingDAO;
import DAOs.FinancialTransactionDAO;
import DAOs.PostsDAO;
import DAOs.PromotionProgramDAO;
import DAOs.RecruiterDAO;
import Models.PostPricing;
import Models.FinancialTransaction;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Posts;
import Models.PromotionProgram;

/**
 *
 * @author DELL
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    private PostPricingDAO pricingDAO = new PostPricingDAO();
    private FinancialTransactionDAO transactionDAO = new FinancialTransactionDAO();
    private RecruiterDAO recruiterDAO = new RecruiterDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null){
            response.sendRedirect("home");
            return;
        }
        
        if ("registration".equals(action)) {
            handleRegistrationCheckout(request, response);
        } else if ("jobPost".equals(action)) {
            handleJobPostCheckout(request, response);
        } else if ("showPricing".equals(action)) {
            showPricingOptions(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void handleRegistrationCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer recruiterId = Integer.parseInt(request.getParameter("recruiterId"));
        
        if ("verified".equals(recruiterDAO.getVerificationStatus(recruiterId))) {
            response.sendRedirect("home");
            return;
        }
        
        PostPricing regPricing = pricingDAO.getPricingByCode("registration");
        if (regPricing == null) {
            request.setAttribute("error", "Pricing not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setRecruiterId(recruiterId);
        transaction.setType("expense");
        transaction.setTransactionType("registration");
        transaction.setAmount(regPricing.getPrice());
        transaction.setDescription("Registration fee payment");
        transaction.setStatus("pending");
        
        int transactionId = transactionDAO.createTransaction(transaction);
        
        if (transactionId > 0) {
            session.setAttribute("pendingTransactionId", transactionId);
            session.setAttribute("checkoutType", "registration");
            
            response.sendRedirect("payment?totalBill=" + regPricing.getPrice() + 
                "&transactionType=registration&transactionId=" + transactionId);
        } else {
            request.setAttribute("error", "Failed to create transaction");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    private void handleJobPostCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        
        Integer recruiterId = (Integer) session.getAttribute("userId");
        
        if (recruiterId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String positionCode = request.getParameter("positionCode");
        String jobIdStr = request.getParameter("jobId");
        
        if (positionCode == null || jobIdStr == null) {
            response.sendRedirect("recruiter_dashboard.jsp");
            return;
        }
        
        try {
            int jobId = Integer.parseInt(jobIdStr);
            
            PostPricing pricing = pricingDAO.getPricingByCode(positionCode);
            
            if (pricing == null) {
                request.setAttribute("error", "Invalid position code!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            
            PostsDAO postDao = new PostsDAO();
            Posts post = postDao.getPostById(jobId);
            if (post.getUserId() != recruiterId){
                request.setAttribute("error", "Not your post!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            
            PromotionProgramDAO programDao = new PromotionProgramDAO();
            PromotionProgram program = programDao.getPromotionProgramById(programDao.findProgramIDBy(positionCode));
            if (program.getQuantity() == 0){
                request.setAttribute("error", "Run out of program!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            
            FinancialTransaction transaction = new FinancialTransaction();
            transaction.setRecruiterId(recruiterId);
            transaction.setType("expense");
            transaction.setTransactionType(positionCode); // normal, featured, premium
            transaction.setAmount(pricing.getPrice());
            transaction.setDescription("Job post payment - " + pricing.getPositionName());
            transaction.setStatus("pending");
            
            int transactionId = transactionDAO.createTransaction(transaction);
            
            log("da di den day cua checkout");
            
            if (transactionId > 0) {
                log("di vao nhanh nay");
                session.setAttribute("pendingTransactionId", transactionId);
                session.setAttribute("checkoutType", "jobPost");
                session.setAttribute("jobId", jobId);
                session.setAttribute("positionCode", positionCode);
                session.setAttribute("durationDays", pricing.getDurationDays());
                session.setAttribute("programID", programDao.findProgramIDBy(positionCode));
                
                response.sendRedirect("payment?totalBill=" + pricing.getPrice() + 
                    "&transactionType=" + positionCode + "&transactionId=" + transactionId);
            } else {
                request.setAttribute("error", "Failed to create transaction");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("home");
        }
    }
    
    private void showPricingOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jobId = request.getParameter("jobId");
        
        log("size: " + pricingDAO.getJobPostPricing().size());
        
        request.setAttribute("jobId", jobId);
        request.setAttribute("pricingOptions", pricingDAO.getJobPostPricing());
        request.getRequestDispatcher("pricing_options.jsp").forward(request, response);
    }
}