/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAOs.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Admin;
import Models.JobSeeker;
import Models.Recruiter;
import Utils.PasswordUtils;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private static final int SESSION_TIMEOUT = 30 * 60;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize UserDAO", e);
            throw new ServletException("Failed to initialize UserDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (isUserLoggedIn(session)) {
            redirectToHomePage(session, response);
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (isInvalidInput(username, password, role)) {
            handleInvalidInput(request, response);
            return;
        }

        try {
            HttpSession session = request.getSession();
            Object user = authenticateUser(username, PasswordUtils.hashPassword(password), role);

            if (user != null) {
                handleSuccessfulLogin(user, role, session, response);
            } else {
                handleFailedLogin(request, response);
            }
        } catch (Exception e) {
            handleLoginError(request, response, e);
        }
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

    private boolean isInvalidInput(String username, String password, String role) {
        return username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || role == null || role.trim().isEmpty();
    }

    private void handleInvalidInput(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", "Vui lòng điền đầy đủ thông tin đăng nhập");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private Object authenticateUser(String username, String password, String role) {
        switch (role) {
            case "admin":
                Admin admin = userDAO.loginAdmin(username, password);
                return (admin != null && admin.isActive()) ? admin : null;

            case "recruiter":
                Recruiter recruiter = userDAO.loginRecruiter(username, password);
                return (recruiter != null && recruiter.isActive()
                        && "verified".equalsIgnoreCase(recruiter.getVerificationStatus()))
                        ? recruiter : null;

            case "job-seeker":
                JobSeeker jobSeeker = userDAO.loginJobSeeker(username, password);
                return (jobSeeker != null && jobSeeker.isActive()) ? jobSeeker : null;

            default:
                return null;
        }
    }

    private void handleSuccessfulLogin(Object user, String role,
            HttpSession session, HttpServletResponse response)
            throws IOException {
        session.setAttribute("user", user);
        session.setAttribute("role", role);
        session.setAttribute("userId", getUserId(user));
        session.setAttribute("userType", role);
        if (role.equals("admin")) {
            session.setAttribute("adminRole", ((Admin) user).getRole());
        }
        session.setMaxInactiveInterval(SESSION_TIMEOUT);

        redirectToHomePage(session, response);
    }

    private Integer getUserId(Object user) {
        if (user instanceof Admin) {
            return ((Admin) user).getId();
        } else if (user instanceof Recruiter) {
            return ((Recruiter) user).getId();
        } else if (user instanceof JobSeeker) {
            return ((JobSeeker) user).getId();
        }
        return null;
    }

    private void handleFailedLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", "Tên người dùng, mật khẩu hoặc trạng thái tài khoản không hợp lệ");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void handleLoginError(HttpServletRequest request, HttpServletResponse response,
            Exception e) throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, "Login error", e);
        request.setAttribute("error", "Đã xảy ra lỗi trong quá trình đăng nhập");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void redirectToHomePage(HttpSession session, HttpServletResponse response)
            throws IOException {
        String role = (String) session.getAttribute("role");
        String homePage = switch (role) {
            case "admin" ->
                "home";
            case "recruiter" ->
                "home";
            case "job-seeker" ->
                "home";
            default ->
                "login";
        };
        response.sendRedirect(homePage);
    }

    @Override
    public String getServletInfo() {
        return "Handles user authentication and session management";
    }
}
