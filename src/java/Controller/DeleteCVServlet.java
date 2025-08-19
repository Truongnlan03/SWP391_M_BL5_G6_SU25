package Controller;

import DAOs.CVTemplateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteCVServlet", urlPatterns = {"/delete-cv"})
public class DeleteCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("list-cv");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("list-cv");
            return;
        }

        Integer jobSeekerId = (Integer) request.getSession().getAttribute("userId");
        if (jobSeekerId == null) {
            response.sendRedirect("login");
            return;
        }

        CVTemplateDAO dao = new CVTemplateDAO();
        boolean deleted = dao.deleteCV(id, jobSeekerId);

        if (deleted) {
            response.sendRedirect("list-cv?success=3"); 
        } else {
            response.sendRedirect("list-cv?error=1"); 
        }
    }
}
