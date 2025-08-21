/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Models.JobSeeker;
import DAOs.JobSeekerDAO;
import DAOs.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
@MultipartConfig
public class AdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        if (session.getAttribute("role") == null) {
            response.sendRedirect("home");
            return;
        } else if (!session.getAttribute("role").equals("admin")) {
            response.sendRedirect("home");
            return;
        }
        
        String target = request.getParameter("target");
        if (target == null) {
            target = "JobSeeker";
        }
        if (target.equals("JobSeeker")) {
            processJobSeeker(request, response);
        }
    }

    
    private void processJobSeeker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }

        if (service.equals("list")) {
            listJobSeeker(request, response);
        } else if (service.equals("Add")) {
            addJobSeeker(request, response);
        } else if (service.equals("Ban")) {
            banJobSeeker(request, response);
        } else if (service.equals("Detail")) {
            detailJobSeeker(request, response);
        }
    }

    
    private void listJobSeeker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            submit = "";
        }
        if (submit.equals("Search")) {
            String idStr = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String experienceStr = request.getParameter("experienceYears");
            String education = request.getParameter("education");
            String salaryStr = request.getParameter("desiredSalary");
            String jobCategory = request.getParameter("jobCategory");
            String languages = request.getParameter("languages");
            String isActiveStr = request.getParameter("isActive");

            JobSeeker criteria = new JobSeeker();
            if (idStr != null && !idStr.isEmpty()) {
                criteria.setId(Integer.parseInt(idStr));
            }
            if (username != null && !username.isEmpty()) {
                criteria.setUsername(username);
            }
            if (email != null && !email.isEmpty()) {
                criteria.setEmail(email);
            }
            if (fullName != null && !fullName.isEmpty()) {
                criteria.setFullName(fullName);
            }
            if (phone != null && !phone.isEmpty()) {
                criteria.setPhone(phone);
            }
            if (gender != null && !gender.isEmpty()) {
                criteria.setGender(gender);
            }
            if (address != null && !address.isEmpty()) {
                criteria.setAddress(address);
            }
            if (experienceStr != null && !experienceStr.isEmpty()) {
                criteria.setExperienceYears(Integer.parseInt(experienceStr));
            }
            if (education != null && !education.isEmpty()) {
                criteria.setEducation(education);
            }
            if (salaryStr != null && !salaryStr.isEmpty()) {
                criteria.setDesiredSalary(Double.parseDouble(salaryStr));
            }
            if (jobCategory != null && !jobCategory.isEmpty()) {
                criteria.setJobCategory(jobCategory);
            }
            if (languages != null && !languages.isEmpty()) {
                criteria.setLanguages(languages);
            }
            criteria.setActive(true);
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }

            StringBuilder sql = new StringBuilder("SELECT * FROM [project_SWP391].[dbo].[Job_Seekers] WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (criteria.getId() != 0) {
                sql.append(" AND id = ?");
                params.add(criteria.getId());
            }
            if (criteria.getUsername() != null) {
                sql.append(" AND username LIKE ?");
                params.add("%" + criteria.getUsername() + "%");
            }
            if (criteria.getEmail() != null) {
                sql.append(" AND email LIKE ?");
                params.add("%" + criteria.getEmail() + "%");
            }
            if (criteria.getFullName() != null) {
                sql.append(" AND full_name LIKE ?");
                params.add("%" + criteria.getFullName() + "%");
            }
            if (criteria.getPhone() != null) {
                sql.append(" AND phone LIKE ?");
                params.add("%" + criteria.getPhone() + "%");
            }
            if (criteria.getGender() != null) {
                sql.append(" AND gender = ?");
                params.add(criteria.getGender());
            }
            if (criteria.getAddress() != null) {
                sql.append(" AND address LIKE ?");
                params.add("%" + criteria.getAddress() + "%");
            }
            if (criteria.getExperienceYears() != 0) {
                sql.append(" AND experience_years = ?");
                params.add(criteria.getExperienceYears());
            }
            if (criteria.getEducation() != null) {
                sql.append(" AND education LIKE ?");
                params.add("%" + criteria.getEducation() + "%");
            }
            if (criteria.getDesiredSalary() != 0) {
                sql.append(" AND desired_salary >= ?");
                params.add(criteria.getDesiredSalary());
            }
            if (criteria.getJobCategory() != null) {
                sql.append(" AND job_category LIKE ?");
                params.add("%" + criteria.getJobCategory() + "%");
            }
            if (criteria.getLanguages() != null) {
                sql.append(" AND languages LIKE ?");
                params.add("%" + criteria.getLanguages() + "%");
            }

            sql.append(" AND is_active = ?");
            params.add(criteria.isActive());

            JobSeekerDAO dao = new JobSeekerDAO();
            Vector<JobSeeker> vec = dao.searchJobSeekers(criteria);

            request.setAttribute("vec", vec);
        } else {
            JobSeekerDAO dao = new JobSeekerDAO();
            Vector<JobSeeker> vec = dao.getAllJobSeeker();

            request.setAttribute("vec", vec);
        }

        request.getRequestDispatcher("admin_manage_jobseeker.jsp").forward(request, response);
    }

    
    private void addJobSeeker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher("admin_add_jobseeker.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirthStr = request.getParameter("dateOfBirth"); // cần parse sang Date
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String profilePicture = request.getParameter("profilePicture");
            String cvFile = request.getParameter("cvFile");
            String skills = request.getParameter("skills");
            String experienceYearsStr = request.getParameter("experienceYears"); // cần parse int
            String education = request.getParameter("education");
            String desiredJobTitle = request.getParameter("desiredJobTitle");
            String desiredSalaryStr = request.getParameter("desiredSalary"); // cần parse double
            String jobCategory = request.getParameter("jobCategory");
            String preferredLocation = request.getParameter("preferredLocation");
            String careerLevel = request.getParameter("careerLevel");
            String workType = request.getParameter("workType");
            String profileSummary = request.getParameter("profileSummary");
            String portfolioUrl = request.getParameter("portfolioUrl");
            String languages = request.getParameter("languages");
            String createdAtStr = request.getParameter("createdAt"); // cần parse Date
            String updatedAtStr = request.getParameter("updatedAt"); // cần parse Date
            String isActiveStr = request.getParameter("isActive"); // cần parse boolean
            Date dateOfBirth = Date.valueOf(dateOfBirthStr); // nếu format là yyyy-MM-dd
            int experienceYears = Integer.parseInt(experienceYearsStr);
            double desiredSalary = Double.parseDouble(desiredSalaryStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);
            Date createdAt = Date.valueOf(createdAtStr);
            Date updatedAt = Date.valueOf(updatedAtStr);

            JobSeeker p = new JobSeeker(username, password, email, fullName, phone,
                    dateOfBirth, gender, address, profilePicture, cvFile, skills,
                    experienceYears, education, desiredJobTitle, desiredSalary,
                    jobCategory, preferredLocation, careerLevel, workType,
                    profileSummary, portfolioUrl, languages, createdAt, updatedAt, isActive);

            dao.registerJobSeeker(p);

            response.sendRedirect("AdminController?target=JobSeeker");

        }
    }

    
    private void banJobSeeker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        JobSeekerDAO dao = new JobSeekerDAO();
        dao.changeStatus(ID, status);

        response.sendRedirect("AdminController?target=JobSeeker");
    }

    
    private void detailJobSeeker(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));

        JobSeekerDAO dao = new JobSeekerDAO();
        JobSeeker p = dao.getSpeccificJobSeeker(ID);

        request.setAttribute("JobSeeker", p);

        request.getRequestDispatcher("admin_detail_jobseeker.jsp").forward(request, response);
    }

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
