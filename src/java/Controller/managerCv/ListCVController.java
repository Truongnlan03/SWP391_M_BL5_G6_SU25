/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.managerCv;

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

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "desc";
        }

        int page = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ignored) {
            }
        }

        int offset = (page - 1) * pageSize;

        CVTemplateDAO dao = new CVTemplateDAO();
        List<CVTemplate> cvList = dao.getCVsByUserId(userId, keyword, sort, offset, pageSize);
        int totalCVs = dao.countCVsByUserId(userId, keyword);
        int totalPages = (int) Math.ceil((double) totalCVs / pageSize);

        request.setAttribute("cvList", cvList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("managerCV/listCV.jsp").forward(request, response);
    }

}
