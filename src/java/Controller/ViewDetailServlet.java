package Controller;

import DAOs.PostDAO;
import Models.Posts;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/viewdetail")
public class ViewDetailServlet extends HttpServlet {

    private PostDAO postDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        postDAO = new PostDAO(); // khởi tạo 1 lần khi servlet load
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID bài đăng");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            Posts post = postDAO.getPostById(id);
            if (post == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bài đăng không tồn tại");
                return;
            }

            // Gửi sang JSP
            request.setAttribute("post", post);
            request.getRequestDispatcher("viewdetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            Logger.getLogger(ViewDetailServlet.class.getName())
                  .log(Level.WARNING, "ID không hợp lệ: " + idParam, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");

        } catch (Exception e) {
            Logger.getLogger(ViewDetailServlet.class.getName())
                  .log(Level.SEVERE, "Lỗi khi lấy chi tiết bài đăng", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra trên server");
        }
    }
}