/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAOs.CVTemplateDAO;
import Models.CVTemplate;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "ListCVController", urlPatterns = {"/list-cv"})
public class ListCVController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy userId từ session (người đang đăng nhập)
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            // Nếu chưa login thì chuyển hướng sang trang login
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy tham số tìm kiếm (nếu có)
        String keyword = request.getParameter("keyword");
        // Lấy tham số sắp xếp (asc/desc), mặc định là desc
        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "desc";
        }

        // Xử lý phân trang
        int page = 1;          // Trang mặc định = 1
        int pageSize = 5;      // Số CV hiển thị mỗi trang
        if (request.getParameter("page") != null) {
            try {
                // Nếu có tham số page thì parse ra số
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ignored) {
                // Nếu lỗi parse thì bỏ qua, giữ nguyên page = 1
            }
        }

        // Tính offset để query DB (LIMIT offset, pageSize)
        int offset = (page - 1) * pageSize;

        // Gọi DAO để lấy dữ liệu từ DB
        CVTemplateDAO dao = new CVTemplateDAO();
        // Lấy danh sách CV theo userId, có filter keyword + sort + phân trang
        List<CVTemplate> cvList = dao.getCVsByUserId(userId, keyword, sort, offset, pageSize);
        // Đếm tổng số CV để tính tổng số trang
        int totalCVs = dao.countCVsByUserId(userId, keyword);
        int totalPages = (int) Math.ceil((double) totalCVs / pageSize);

        // Đẩy dữ liệu sang JSP
        request.setAttribute("cvList", cvList);         // danh sách CV
        request.setAttribute("currentPage", page);      // trang hiện tại
        request.setAttribute("totalPages", totalPages); // tổng số trang
        request.setAttribute("keyword", keyword);       // từ khóa tìm kiếm
        request.setAttribute("sort", sort);             // kiểu sắp xếp

        // Chuyển tiếp sang trang listCV.jsp để hiển thị
        request.getRequestDispatcher("listCV.jsp").forward(request, response);
    }

}
