/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import DAOs.BannerDAO;
import DAOs.BlogDAO;
import DAOs.FeaturedJobDAO;
import DAOs.HomepageDAO;
import DAOs.JobDAO;
import DAOs.NotificationDAO;
import DAOs.PostsDAO;
import DAOs.RecruiterNotificationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.HomepageComponentContent;
import Models.JobTypeCount;
import Models.Notification;
import Models.Posts;
import Models.RecruiterNotification;
import Utils.Constants;


/**
 *
 * @author DELL
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home", "/"})
public class HomeController extends HttpServlet {

    private PostsDAO postsDAO;
    private JobDAO jobDAO;
    private BannerDAO bannerDAO;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        postsDAO = new PostsDAO();
        jobDAO = new JobDAO();
        bannerDAO = new BannerDAO();
        blogDAO = new BlogDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            HomepageDAO homepageDAO = new HomepageDAO();
            Vector<HomepageComponentContent> testimonials = homepageDAO.getListComponentContentsByType("testimonial");
            request.setAttribute("testimonials", testimonials);

            Vector<HomepageComponentContent> applyProcesses = homepageDAO.getListComponentContentsByType("apply_process");
            request.setAttribute("applyProcesses", applyProcesses);

            request.setAttribute("banners", bannerDAO.getTopBanners(3));

            request.setAttribute("blogList", blogDAO.getLatestBlogs(2));
            
            List<JobTypeCount> jobCategories = jobDAO.getJobTypeCounts(8);
            request.setAttribute("jobCategories", jobCategories);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }
        
        try {
            int premium = 3; 
            FeaturedJobDAO fjDao = new FeaturedJobDAO();
            List<Posts> premiumPost = fjDao.listPostBaseOnFeatureStillActive(premium);
            
            log("sizepremium: " + premiumPost.size());
            
            request.setAttribute("posts", premiumPost);
            
            int lastest = 2; 
            List<Posts> recentPost = fjDao.listPostBaseOnFeatureStillActive(lastest);
            
            log("sizerecent: " + recentPost.size());
            
            request.setAttribute("recentPosts", recentPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        if (role != null && !role.equals("admin")) {
            processQuerry(request, response);
            return;
        }
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void processQuerry(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }
        service = "list";

        if (service.equals("list")){
            listNotice(request, response);
        } else if (service.equals("")){
        }
    }

    private void listNotice(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String role = (String) session.getAttribute("role");
        int id = (int) session.getAttribute("userId");
        NotificationDAO dao = new NotificationDAO();
        
        int topk = 5;
        Vector<Notification> list = dao.getNotice(id, topk, role);
        Vector<Notification> unread = dao.getUnreadNotice(id, topk, role);
        request.setAttribute("notice", list);
        request.setAttribute("unread", unread);
        request.setAttribute("rr", unread.size());
        
        log("id recruiter:" + id + " " + role);
        log("" + list.size());
                
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
