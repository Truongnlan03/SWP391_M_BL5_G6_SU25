/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAOs.ApplicationDAO;
import DAOs.PostsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Application;
import Models.JobSeeker;
import Models.Posts;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ApplicationController", urlPatterns = {"/applications"})
public class ApplicationController extends HttpServlet {
    
    private ApplicationDAO applicationDAO;
    private PostsDAO postsDAO;
    
    public ApplicationController() {
        try {
            applicationDAO = new ApplicationDAO();
            postsDAO = new PostsDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        
        if (role == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int page = 1;
            int pageSize = 10;
            
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            String status = request.getParameter("status");
            String keyword = request.getParameter("keyword");
            String sortBy = request.getParameter("sortBy");
            
            if (sortBy == null || sortBy.trim().isEmpty()) {
                sortBy = "created_at_desc";
            }

            if ("job-seeker".equals(role)) {
                log("diquaday");
                handleJobSeekerApplications(request, response, session, page, pageSize, status, keyword, sortBy);
            } else if ("recruiter".equals(role)) {
                handleRecruiterApplications(request, response, session, page, pageSize, status, keyword, sortBy);
            } else {
                response.sendRedirect("home");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách ứng tuyển");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void handleJobSeekerApplications(HttpServletRequest request, HttpServletResponse response, 
            HttpSession session, int page, int pageSize, String status, String keyword, String sortBy) 
            throws SQLException, ServletException, IOException {
        
        JobSeeker jobSeeker = (JobSeeker) session.getAttribute("user");
        if (jobSeeker == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Application> applications = applicationDAO.getApplicationsByJobSeeker(
            jobSeeker.getId(), page, pageSize, status, keyword, sortBy);
        
        log("size: " + applications.size());
        
        int totalApplications = applicationDAO.countApplicationsByJobSeeker(
            jobSeeker.getId(), status, keyword);
        
        log("total: " + totalApplications);
        
        int totalPages = (int) Math.ceil((double) totalApplications / pageSize);

        List<Map<String, Object>> recommendedJobsWithScores = getJobRecommendations(jobSeeker);

        request.setAttribute("applications", applications);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("status", status);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("userType", "jobseeker");
        request.setAttribute("recommendedJobsWithScores", recommendedJobsWithScores);

        log("da di qua day");
        
        request.getRequestDispatcher("applications.jsp").forward(request, response);
    }

    private List<Map<String, Object>> getJobRecommendations(JobSeeker jobSeeker) {
        List<Map<String, Object>> recommendedJobsWithScores = new ArrayList<>();
        
        try {
            services.JobRecommendationService recommendationService = new services.JobRecommendationService();
            List<services.JobRecommendationService.JobRecommendation> recommendations = 
                recommendationService.getRecommendations(jobSeeker, 6);
            
            for (services.JobRecommendationService.JobRecommendation rec : recommendations) {
                Map<String, Object> jobWithScore = new HashMap<>();
                jobWithScore.put("job", rec.getJob());
                jobWithScore.put("score", rec.getScore());
                recommendedJobsWithScores.add(jobWithScore);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
            try {
                List<Posts> latestJobs = postsDAO.getLatestPosts(3);
                for (Posts job : latestJobs) {
                    Map<String, Object> jobWithScore = new HashMap<>();
                    jobWithScore.put("job", job);
                    jobWithScore.put("score", 0.0); // Fallback score
                    recommendedJobsWithScores.add(jobWithScore);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return recommendedJobsWithScores;
    }
    

    


    private void handleRecruiterApplications(HttpServletRequest request, HttpServletResponse response, 
            HttpSession session, int page, int pageSize, String status, String keyword, String sortBy) 
            throws SQLException, ServletException, IOException {
        
        Recruiter recruiter = (Recruiter) session.getAttribute("user");
        if (recruiter == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        DAOs.ExperienceDAO experienceDAO = new DAOs.ExperienceDAO();
        DAOs.EducationDAO educationDAO = new DAOs.EducationDAO();
        DAOs.CertificateDAO certificateDAO = new DAOs.CertificateDAO();
        DAOs.AwardDAO awardDAO = new DAOs.AwardDAO();
        DAOs.CVTemplateDAO cvTemplateDAO = new DAOs.CVTemplateDAO();

        List<Application> applications = applicationDAO.getApplicationsByRecruiter(
            recruiter.getId(), page, pageSize, status, keyword, sortBy);
        
        for (Application app : applications) {
            JobSeeker js = app.getJobseeker();
            if (js != null) {
                try {
                    js.setExperiences(experienceDAO.getExperiencesByJobSeeker(js.getId()));
                } catch (Exception e) { js.setExperiences(new java.util.ArrayList<>()); }
                try {
                    js.setEducations(educationDAO.getEducationsByJobSeeker(js.getId()));
                } catch (Exception e) { js.setEducations(new java.util.ArrayList<>()); }
                try {
                    js.setCertificates(certificateDAO.getCertificatesByJobSeeker(js.getId()));
                } catch (Exception e) { js.setCertificates(new java.util.ArrayList<>()); }
                try {
                    js.setAwards(awardDAO.getAwardsByJobSeeker(js.getId()));
                } catch (Exception e) { js.setAwards(new java.util.ArrayList<>()); }
                try {
                    js.setCvTemplates(cvTemplateDAO.getCVsByJobSeeker(js.getId()));
                } catch (Exception e) { js.setCvTemplates(new java.util.ArrayList<>()); }
            }
        }
        
        int totalApplications = applicationDAO.countApplicationsByRecruiter(
            recruiter.getId(), status, keyword);
        
        int totalPages = (int) Math.ceil((double) totalApplications / pageSize);

        request.setAttribute("applications", applications);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalApplications", totalApplications);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("status", status);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("userType", "recruiter");

        request.getRequestDispatcher("recruiter-applications.jsp").forward(request, response);
    }
} 