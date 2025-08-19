package Controller;

import DAOs.PostDAO;
import Models.Posts;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/post")   // ✅ mapping đúng với URL bạn muốn
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy userId từ session
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        
        if (userId != null) {
            PostDAO postDAO = new PostDAO();
            List<Posts> posts = postDAO.getPostsByUserId(userId);

            // Gửi dữ liệu sang JSP
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("post.jsp").forward(request, response);
        } else {
            // Nếu chưa đăng nhập thì chuyển hướng về trang login
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}