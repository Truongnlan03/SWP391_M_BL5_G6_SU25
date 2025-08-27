/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAOs.ApplicationDAO;
import DAOs.JobSeekerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.Application;
import Models.JobSeeker;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CandidateDetailsController", urlPatterns = {"/candidate-details"})
public class CandidateDetailsController extends HttpServlet {
    private ApplicationDAO applicationDAO;
    private JobSeekerDAO jobSeekerDAO;

    @Override
    public void init() throws ServletException {
        applicationDAO = new ApplicationDAO();
        jobSeekerDAO = new JobSeekerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Recruiter recruiter = (Recruiter) session.getAttribute("user");
        
        if (recruiter == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("{\"error\": \"Unauthorized access\"}");
            return;
        }
        
        String applicationId = request.getParameter("applicationId");
        String candidateId = request.getParameter("candidateId");
        
        PrintWriter out = response.getWriter();
        
        try {
            if (applicationId == null || candidateId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing required parameters\"}");
                return;
            }

            Application application = applicationDAO.getApplicationById(Integer.parseInt(applicationId), recruiter.getId());
            if (application == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Application not found\"}");
                return;
            }

            JobSeeker candidate = jobSeekerDAO.getSpeccificJobSeeker(Integer.parseInt(candidateId));
            if (candidate == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Candidate not found\"}");
                return;
            }

            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"id\": ").append(candidate.getId()).append(",");
            json.append("\"fullName\": \"").append(escapeJson(candidate.getFullName())).append("\",");
            json.append("\"email\": \"").append(escapeJson(candidate.getEmail())).append("\",");
            json.append("\"phone\": \"").append(escapeJson(candidate.getPhone() != null ? candidate.getPhone() : "")).append("\",");
            json.append("\"address\": \"").append(escapeJson(candidate.getAddress() != null ? candidate.getAddress() : "")).append("\",");
            json.append("\"dateOfBirth\": \"").append(candidate.getDateOfBirth() != null ? candidate.getDateOfBirth().toString() : "").append("\",");
            json.append("\"gender\": \"").append(escapeJson(candidate.getGender() != null ? candidate.getGender() : "")).append("\",");
            json.append("\"skills\": \"").append(escapeJson(candidate.getSkills() != null ? candidate.getSkills() : "")).append("\",");
            json.append("\"experience\": \"").append(candidate.getExperienceYears()).append(" năm\",");
            json.append("\"avatar\": \"").append(escapeJson(candidate.getProfilePicture() != null ? candidate.getProfilePicture() : "")).append("\",");
            json.append("\"cvFile\": \"").append(escapeJson(application.getCvFile() != null ? application.getCvFile() : "")).append("\"");
            json.append("}");

            out.print(json.toString());
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Database error occurred\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid ID format\"}");
        } finally {
            out.close();
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t")
                  .replace("\\", "\\\\");
    }
} 