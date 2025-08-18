package Controller;

import DAOs.PostDAO;
import Models.Posts;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-posts")
public class MyPostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // Nếu chưa đăng nhập → chuyển hướng login
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy danh sách post của user này
        PostDAO postDAO = new PostDAO();
        List<Posts> myPosts = postDAO.getPostsByUserId(userId);

        request.setAttribute("post", myPosts);
        request.getRequestDispatcher("viewpost.jsp").forward(request, response);
    }
}
