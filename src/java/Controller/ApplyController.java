/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.sql.Timestamp;

import DAOs.ApplicationDAO;
import DAOs.JobDAO;
import DAOs.RecruitmentProcessDAO;
import DAOs.PostsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Application;
import Models.JobListing;
import Models.JobSeeker;
import Models.RecruitmentProcess;
import Models.Posts;
import Models.CVTemplate;
import DAOs.CVTemplateDAO;
import java.util.List;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ApplyController", urlPatterns = {"/apply"})
public class ApplyController extends HttpServlet {
    private ApplicationDAO applicationDAO;
    private RecruitmentProcessDAO recruitmentProcessDAO;
    private JobDAO jobDAO;
    private PostsDAO postsDAO;
    private CVTemplateDAO cvTemplateDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
        recruitmentProcessDAO = new RecruitmentProcessDAO();
        jobDAO = new JobDAO();
        postsDAO = new PostsDAO();
        cvTemplateDAO = new CVTemplateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            JobSeeker jobSeeker = (JobSeeker) session.getAttribute("user");
            String postIdStr = request.getParameter("id");
            if (postIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            int postId = Integer.parseInt(postIdStr);

            Posts post = postsDAO.getPostById(postId);
            List<CVTemplate> cvList = cvTemplateDAO.getCVsByJobSeeker(jobSeeker.getId());

            if (post == null) {
                // Handle post not found
                response.sendRedirect(request.getContextPath() + "/home?error=post_not_found");
                return;
            }

            request.setAttribute("post", post);
            request.setAttribute("cvList", cvList);
            request.getRequestDispatcher("/apply-job.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home?error=server_error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null || !"job-seeker".equals(session.getAttribute("role"))) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            JobSeeker jobSeeker = (JobSeeker) session.getAttribute("user");
            int postId = Integer.parseInt(request.getParameter("postId"));
            int cvId = Integer.parseInt(request.getParameter("cvId"));

            Posts post = postsDAO.getPostById(postId);
            if (post == null) {
                throw new Exception("Post not found with id: " + postId);
            }
            int recruiterId = post.getUserId();

            Application application = new Application();
            application.setJobSeekerId(jobSeeker.getId());
            application.setPostId(postId);
            application.setCvId(cvId);
            application.setStatus("new");

            boolean success = applicationDAO.saveApplicationAndCreateProcess(application, recruiterId, 0);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/applications?applySuccess=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/post/view?id=" + postId + "&applyError=true");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home?error=invalid_id");
        } catch (Exception e) {
            e.printStackTrace();
            String postIdParam = request.getParameter("postId");
            if (postIdParam != null && !postIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/post/view?id=" + postIdParam + "&applyError=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/home?error=unknown");
            }
        }
    }
} 