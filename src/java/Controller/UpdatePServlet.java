/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.PostDAO;
import Models.Posts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 *
 * @author DELL
 */
@WebServlet("/updatePost")
public class UpdatePServlet extends HttpServlet {

    private final PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Lấy id bài đăng
        int id = Integer.parseInt(req.getParameter("id"));

        // Lấy post từ DB
//        Posts post = postDAO.getPostByPostId(id);
//
//        if (post != null) {
//            req.setAttribute("post", post);
//            req.getRequestDispatcher("update_post.jsp").forward(req, resp);
//        } else {
//            resp.sendRedirect("post.jsp?error=PostNotFound");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int userId = Integer.parseInt(req.getParameter("userId"));

            Posts post = new Posts();
            post.setId(id);
            post.setUserId(userId);
            post.setTitle(req.getParameter("title"));
            post.setContent(req.getParameter("content"));
            post.setSalary(req.getParameter("salary"));
            post.setLocation(req.getParameter("location"));
            post.setJobType(req.getParameter("jobType"));
            post.setExperience(req.getParameter("experience"));
            // deadline (cần convert)
            String deadline = req.getParameter("deadline");
            if (deadline != null && !deadline.isEmpty()) {
                post.setDeadline(java.sql.Date.valueOf(deadline));
            }
            post.setWorkingTime(req.getParameter("workingTime"));
            post.setJobDescription(req.getParameter("jobDescription"));
            post.setRequirements(req.getParameter("requirements"));
            post.setBenefits(req.getParameter("benefits"));
            post.setContactAddress(req.getParameter("contactAddress"));
            post.setApplicationMethod(req.getParameter("applicationMethod"));
            String quantity = req.getParameter("quantity");
            if (quantity != null && !quantity.isEmpty()) {
                post.setQuantity(Integer.parseInt(quantity));
            }
            post.setRank(req.getParameter("rank"));
            post.setIndustry(req.getParameter("industry"));
            post.setContactPerson(req.getParameter("contactPerson"));
            post.setCompanySize(req.getParameter("companySize"));
            post.setCompanyWebsite(req.getParameter("companyWebsite"));
            post.setCompanyDescription(req.getParameter("companyDescription"));
            post.setKeywords(req.getParameter("keywords"));

            boolean updated = postDAO.updatePost(post);

            if (updated) {
                resp.sendRedirect("post.jsp?success=updated");
            } else {
                resp.sendRedirect("updatePost?id=" + id + "&error=failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("post.jsp?error=exception");
        }
    }
}
