/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.PostDAO;
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
@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
    private final PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        try {
//            // Lấy id từ URL
//            String idParam = req.getParameter("id");
//            if (idParam == null || idParam.isEmpty()) {
//                resp.sendRedirect("post.jsp?error=missingId");
//                return;
//            }
//
//            int postId = Integer.parseInt(idParam);
//
//            // Gọi DAO xóa
//            boolean deleted = postDAO.deletePost(postId);
//
//            if (deleted) {
//                resp.sendRedirect("post.jsp?success=deleted");
//            } else {
//                resp.sendRedirect("post.jsp?error=notfound");
//            }
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            resp.sendRedirect("post.jsp?error=invalidId");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            resp.sendRedirect("post.jsp?error=exception");
//        }
    }
}
