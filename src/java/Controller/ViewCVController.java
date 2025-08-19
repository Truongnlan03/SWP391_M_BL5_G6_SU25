package Controller;

import DAOs.CVTemplateDAO;
import Models.CVTemplate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewCVController", urlPatterns = {"/view-cv"})
public class ViewCVController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("list-cv");
            return;
        }

        int id = Integer.parseInt(idParam);
        CVTemplateDAO dao = new CVTemplateDAO();
        CVTemplate cv = dao.getCVById(id);

        if (cv == null) {
            response.sendRedirect("list-cv");
            return;
        }

        request.setAttribute("cv", cv);
        request.getRequestDispatcher("viewCV.jsp").forward(request, response);
    }
}
