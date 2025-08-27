/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Models.JobSeeker;
import Models.Recruiter;
import DAOs.JobSeekerDAO;
import DAOs.RecruiterDAO;
import DAOs.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import java.sql.Date;
//import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import DAOs.NotificationDAO;
import Models.Notification;
import Utils.JavaMail;
import Models.Admin;
import DAOs.AdminDAO;
import DAOs.FeaturedJobDAO;
import DAOs.PostsDAO;
import DAOs.PromotionProgramDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;
import Models.Posts;
import Models.PromotionProgram;
import Utils.InputSanitizer;
import Utils.UploadPicture;

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
    String service = request.getParameter("service");
        HttpSession session = request.getSession(true);
        if (session.getAttribute("role") == null){
            response.sendRedirect("home");
            return;
        } else if (!session.getAttribute("role").equals("admin")){
            response.sendRedirect("home");
            return;
        }
        
        AdminDAO dao = new AdminDAO();
        int userID = (int)session.getAttribute("userId");
        if (dao.getSpecificStaff(userID).getRole().equals("saler")){
            response.sendRedirect("home");
            return;
        }

        String target = request.getParameter("target");
        if (target == null) {
            target = "JobSeeker";
        }

        if (target.equals("JobSeeker")) {
            processJobSeeker(request, response);
        } else if (target.equals("Recruiter")) {
            processRecruiter(request, response);
        } else if (target.equals("Manager")){
            processManager(request, response);
        } else if (target.equals("Saler")){
            processSaler(request, response);
        } else if (target.equals("Staff")){
            processManager(request, response);
        } else if (target.equals("program")){
            processProgram(request, response);
        }

    }
    
    private void processManager(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }

        if (service.equals("list")) {
            listManagers(request, response);
        } else if (service.equals("Add")) {
            addManager(request, response);
        } else if (service.equals("Ban")) {
            banManager(request, response);
        } else if (service.equals("Detail")) {
            detailManager(request, response);
        } else if (service.equals("Update")){
            updateManager(request, response);
        }
    }
    

    private void listRecruiters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        
        int page = 1;
        int recordsPerPage = 10;
        
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        if (recordsPerPageStr != null && !recordsPerPageStr.isEmpty()) {
            recordsPerPage = Integer.parseInt(recordsPerPageStr);
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "ASC";
        }
        
        RecruiterDAO dao = new RecruiterDAO();
        Vector<Recruiter> vec;
        int totalRecords;
        
        if (submit != null && submit.equals("Search")) {
            String idStr = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String companyName = InputSanitizer.cleanSearchQuery(request.getParameter("companyName"));
            String companyAddress = request.getParameter("companyAddress");
            String companySize = request.getParameter("companySize");
            String industry = request.getParameter("industry");
            String loyaltyStr = request.getParameter("loyaltyScore");
            String verificationStatus = request.getParameter("verificationStatus");
            String isActiveStr = request.getParameter("isActive");

            Recruiter criteria = new Recruiter();

            if (idStr != null && !idStr.isEmpty()) {
                criteria.setId(Integer.parseInt(idStr));
            }
            if (username != null && !username.isEmpty()) {
                criteria.setUsername(username);
            }
            if (email != null && !email.isEmpty()) {
                criteria.setEmail(email);
            }
            if (phone != null && !phone.isEmpty()) {
                criteria.setPhone(phone);
            }
            if (companyName != null && !companyName.isEmpty()) {
                criteria.setCompanyName(companyName);
            }
            if (companyAddress != null && !companyAddress.isEmpty()) {
                criteria.setCompanyAddress(companyAddress);
            }
            if (companySize != null && !companySize.isEmpty()) {
                criteria.setCompanySize(companySize);
            }
            if (industry != null && !industry.isEmpty()) {
                criteria.setIndustry(industry);
            }
            if (loyaltyStr != null && !loyaltyStr.isEmpty()) {
                criteria.setLoyaltyScore(Double.parseDouble(loyaltyStr));
            }
            if (verificationStatus != null && !verificationStatus.isEmpty()) {
                criteria.setVerificationStatus(verificationStatus);
            }
            criteria.setActive(true);
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }

            vec = dao.searchRecruitersWithPagingAndSorting(criteria, page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalSearchResults(criteria);
            request.setAttribute("param", criteria);
        } else {
            vec = dao.getAllRecruiterWithPagingAndSorting(page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalRecruiters();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
       
        request.setAttribute("vec", vec);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("admin_manage_recruiter.jsp").forward(request, response);
    }

    private void listManagers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        
        int page = 1;
        int recordsPerPage = 10;
        
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        if (recordsPerPageStr != null && !recordsPerPageStr.isEmpty()) {
            recordsPerPage = Integer.parseInt(recordsPerPageStr);
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "ASC";
        }
        
        AdminDAO dao = new AdminDAO();
        Vector<Admin> vec;
        int totalRecords;
        
        if (submit != null && submit.equals("Search")) {
            String idStr = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String fullName = InputSanitizer.cleanSearchQuery(request.getParameter("fullName"));
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String isActiveStr = request.getParameter("isActive");

            Admin criteria = new Admin();
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
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }
            criteria.setActive(true);
            

            vec = dao.searchStaffWithPagingAndSorting(criteria, page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalStaff(criteria);
            request.setAttribute("param", criteria);
        } else {
            vec = dao.getStaffWithPagingAndSorting(page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalStaff();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        request.setAttribute("vec", vec);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("admin_manage_manager.jsp").forward(request, response);
    }

    private void listSalers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        
        int page = 1;
        int recordsPerPage = 10;
        
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        if (recordsPerPageStr != null && !recordsPerPageStr.isEmpty()) {
            recordsPerPage = Integer.parseInt(recordsPerPageStr);
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "ASC";
        }
        
        AdminDAO dao = new AdminDAO();
        Vector<Admin> vec;
        int totalRecords;
        
        if (submit != null && submit.equals("Search")) {
            String idStr = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String fullName = InputSanitizer.cleanSearchQuery(request.getParameter("fullName"));
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String isActiveStr = request.getParameter("isActive");

            Admin criteria = new Admin();
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
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }
            criteria.setActive(true);

            vec = dao.searchAdminWithPagingAndSorting(criteria, "saler", page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalSearchResults(criteria, "saler");
            request.setAttribute("param", criteria);
        } else {
            vec = dao.getAdminsWithPagingAndSorting("saler", page, recordsPerPage, sortField, sortOrder);
            totalRecords = dao.getTotalAdmins("saler");
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        request.setAttribute("vec", vec);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("admin_manage_saler.jsp").forward(request, response);
    }
    
    private void listManager(HttpServletRequest request, HttpServletResponse response)
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
            String isActiveStr = request.getParameter("isActive");

            Admin criteria = new Admin();
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
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }

            AdminDAO dao = new AdminDAO();
            Vector<Admin> vec = dao.searchAdmin(criteria, "manager");  
            request.setAttribute("vec", vec);
        } else {
            AdminDAO dao = new AdminDAO();
            Vector<Admin> vec = dao.getAdmins("manager");  
            request.setAttribute("vec", vec);
        }
        request.getRequestDispatcher("admin_manage_manager.jsp").forward(request, response);
    }
    
    private void addManager(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        AdminDAO dao = new AdminDAO();

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher("admin_add_manager.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String role = request.getParameter("role");
           
            String profilePicture = "";
            try {
                Part filePart = request.getPart("profilePicture");

                String contentType = filePart.getContentType();

                if (contentType != null && contentType.startsWith("image/")) {
                } else {
                    request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                    request.getRequestDispatcher("admin_add_manager.jsp").forward(request, response);
                    return;
                }
                String basePath = request.getServletContext().getRealPath("/");
                profilePicture = UploadPicture.uploadImage(filePart, profilePicture, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }
            String isActiveStr = request.getParameter("isActive");
            Date dateOfBirth = Date.valueOf(dateOfBirthStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);

            Admin manager = new Admin(username, password, email, fullName, phone,
                    dateOfBirth, gender, address, profilePicture, isActive, role);

            dao.registerAdmin(manager, role); 
            response.sendRedirect("AdminController?target=Staff");
        }
    }
    
    private void updateManager(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        AdminDAO dao = new AdminDAO();

        String submit = request.getParameter("submit");
        if (submit == null) {
            int id = Integer.parseInt(request.getParameter("ID"));
            Admin x = dao.getSpecificStaff(id);
            request.setAttribute("manager", x);
            request.getRequestDispatcher("admin_add_manager.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String role = request.getParameter("role");
            
            int id = Integer.parseInt(request.getParameter("ID"));
            Admin x = dao.getSpecificStaff(id);
            
            String profilePicture = x.getProfilePicture();
            try {
                Part filePart = request.getPart("profilePicture");

                if (filePart != null && filePart.getSize() > 0){
                    String contentType = filePart.getContentType();
                    
                    if (contentType != null && contentType.startsWith("image/")) {
                    } else {
                        request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                        request.setAttribute("manager", x);
                        request.getRequestDispatcher("admin_add_manager.jsp").forward(request, response);
                        return;
                    }
                }

                String basePath = request.getServletContext().getRealPath("/");
                profilePicture = UploadPicture.uploadImage(filePart, profilePicture, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }
            
            Admin nwAdmin = new Admin();
            nwAdmin.setEmail(email);
            nwAdmin.setPhone(phone);
            
            if (!dao.checkEmailPhone(nwAdmin)){
                request.setAttribute("messError", "email hoặc SĐT bị trùng!");
                request.setAttribute("manager", x);
                request.getRequestDispatcher("admin_add_manager.jsp").forward(request, response);
                return;
            }
            
            String isActiveStr = request.getParameter("isActive");
            Date dateOfBirth = Date.valueOf(dateOfBirthStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);

            Admin manager = new Admin(id, username, password, email, fullName, phone,
                    dateOfBirth, gender, address, profilePicture, isActive, role);

            dao.updateAdmin(manager); 

            response.sendRedirect("AdminController?target=Staff");
        }
    }

    private void banManager(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        AdminDAO dao = new AdminDAO();
        status = !status;
        dao.changeStatus(ID, status); 
        response.sendRedirect("AdminController?target=Staff");
    }

    private void detailManager(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));

        AdminDAO dao = new AdminDAO();
        Admin manager = dao.getSpecificStaff(ID); 
        request.setAttribute("Manager", manager);
        request.getRequestDispatcher("admin_detail_manager.jsp").forward(request, response);
    }
    
    private void processSaler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }
        if (service.equals("list")) {
            listSalers(request, response);
        } else if (service.equals("Add")) {
            addSaler(request, response);
        } else if (service.equals("Ban")) {
            banSaler(request, response);
        } else if (service.equals("Detail")) {
            detailSaler(request, response);
        }
    }
    
    private void listSaler(HttpServletRequest request, HttpServletResponse response)
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
            String isActiveStr = request.getParameter("isActive");

            Admin criteria = new Admin();
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
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }

            AdminDAO dao = new AdminDAO();
            Vector<Admin> vec = dao.searchAdmin(criteria, "saler");  
            request.setAttribute("vec", vec);
        } else {
            log("here u r");
            AdminDAO dao = new AdminDAO();
            Vector<Admin> vec = dao.getAdmins("saler");  
            log("sth went wrong");
            request.setAttribute("vec", vec);
        }

        request.getRequestDispatcher("admin_manage_saler.jsp").forward(request, response);
    }
    
    private void addSaler(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        AdminDAO dao = new AdminDAO();

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher("admin_add_saler.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String profilePicture = request.getParameter("profilePicture");
            String isActiveStr = request.getParameter("isActive");

            Date dateOfBirth = Date.valueOf(dateOfBirthStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);

            Admin manager = new Admin(username, password, email, fullName, phone,
                    dateOfBirth, gender, address, profilePicture, isActive, "saler");

            dao.registerAdmin(manager, "saler"); 
            response.sendRedirect("AdminController?target=Saler");
        }
    }

    private void banSaler(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        AdminDAO dao = new AdminDAO();
        status = !status;
        dao.changeStatus(ID, status); 
        response.sendRedirect("AdminController?target=Saler");
    }

    private void detailSaler(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));

        AdminDAO dao = new AdminDAO();
        Admin saler = dao.getSpecificAdmin(ID, "saler"); 
        request.setAttribute("Saler", saler);
        request.getRequestDispatcher("admin_detail_saler.jsp").forward(request, response);
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

            StringBuilder sql = new StringBuilder("SELECT * FROM [projectg6_SWP391].[dbo].[Job_Seekers] WHERE 1=1");
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
            String dateOfBirthStr = request.getParameter("dateOfBirth"); 
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String profilePicture = request.getParameter("profilePicture");
            String cvFile = request.getParameter("cvFile");
            String skills = request.getParameter("skills");
            String experienceYearsStr = request.getParameter("experienceYears"); 
            String education = request.getParameter("education");
            String desiredJobTitle = request.getParameter("desiredJobTitle");
            String desiredSalaryStr = request.getParameter("desiredSalary"); 
            String jobCategory = request.getParameter("jobCategory");
            String preferredLocation = request.getParameter("preferredLocation");
            String careerLevel = request.getParameter("careerLevel");
            String workType = request.getParameter("workType");
            String profileSummary = request.getParameter("profileSummary");
            String portfolioUrl = request.getParameter("portfolioUrl");
            String languages = request.getParameter("languages");
            String createdAtStr = request.getParameter("createdAt"); 
            String updatedAtStr = request.getParameter("updatedAt"); 
            String isActiveStr = request.getParameter("isActive"); 
            Date dateOfBirth = Date.valueOf(dateOfBirthStr); 
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

    private void processRecruiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }
        if (service.equals("list")) {
            listRecruiters(request, response);
        } else if (service.equals("Add")) {
            addRecruiter(request, response);
        } else if (service.equals("Ban")) {
            banRecruiter(request, response);
        } else if (service.equals("Detail")) {
            detailRecruiter(request, response);
        } else if (service.equals("UpdateVerificationStatus")) {
        }
    }

    private void listRecruiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            submit = "";
        }

        if (submit.equals("Search")) {
            String idStr = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String companyName = request.getParameter("companyName");
            String companyAddress = request.getParameter("companyAddress");
            String companySize = request.getParameter("companySize");
            String industry = request.getParameter("industry");
            String loyaltyStr = request.getParameter("loyaltyScore");
            String verificationStatus = request.getParameter("verificationStatus");
            String isActiveStr = request.getParameter("isActive");

            Recruiter criteria = new Recruiter();

            if (idStr != null && !idStr.isEmpty()) {
                criteria.setId(Integer.parseInt(idStr));
            }
            if (username != null && !username.isEmpty()) {
                criteria.setUsername(username);
            }
            if (email != null && !email.isEmpty()) {
                criteria.setEmail(email);
            }
            if (phone != null && !phone.isEmpty()) {
                criteria.setPhone(phone);
            }
            if (companyName != null && !companyName.isEmpty()) {
                criteria.setCompanyName(companyName);
            }
            if (companyAddress != null && !companyAddress.isEmpty()) {
                criteria.setCompanyAddress(companyAddress);
            }
            if (companySize != null && !companySize.isEmpty()) {
                criteria.setCompanySize(companySize);
            }
            if (industry != null && !industry.isEmpty()) {
                criteria.setIndustry(industry);
            }
            if (loyaltyStr != null && !loyaltyStr.isEmpty()) {
                criteria.setLoyaltyScore(Double.parseDouble(loyaltyStr));
            }
            if (verificationStatus != null && !verificationStatus.isEmpty()) {
                criteria.setVerificationStatus(verificationStatus);
            }
            criteria.setActive(true); // Mặc định true
            if (isActiveStr != null && !isActiveStr.isEmpty()) {
                criteria.setActive(Boolean.parseBoolean(isActiveStr));
            }

            RecruiterDAO dao = new RecruiterDAO();
            Vector<Recruiter> vec = dao.searchRecruiters(criteria);

            request.setAttribute("vec", vec);
        } else {
            RecruiterDAO dao = new RecruiterDAO();
            Vector<Recruiter> vec = dao.getAllRecruiter();

            request.setAttribute("vec", vec);
        }

        request.getRequestDispatcher("admin_manage_recruiter.jsp").forward(request, response);
    }

    private void addRecruiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();

        String submit = request.getParameter("submit");
        if (submit == null) {
            request.getRequestDispatcher("admin_add_recruiter.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String profilePicture = request.getParameter("profilePicture");
            String companyName = request.getParameter("companyName");
            String companyDescription = request.getParameter("companyDescription");
            String logo = request.getParameter("logo");
            String website = request.getParameter("website");
            String companyAddress = request.getParameter("companyAddress");
            String companySize = request.getParameter("companySize");
            String industry = request.getParameter("industry");
            String taxCode = request.getParameter("taxCode");
            String loyaltyScoreStr = request.getParameter("loyaltyScore");
            String verificationStatus = request.getParameter("verificationStatus");
            String createdAtStr = request.getParameter("createdAt");
            String updatedAtStr = request.getParameter("updatedAt");
            String isActiveStr = request.getParameter("isActive");
            Date createdAt = Date.valueOf(createdAtStr);
            Date updatedAt = Date.valueOf(updatedAtStr);
            Date dateOfBirth = Date.valueOf(dateOfBirthStr); 
            double loyaltyScore = Double.parseDouble(loyaltyScoreStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);

            Recruiter p = new Recruiter(username, password, email, fullName, phone,
                    dateOfBirth, gender, address, profilePicture, companyName,
                    companyDescription, logo, website, companyAddress, companySize,
                    industry, taxCode, loyaltyScore, verificationStatus,
                    createdAt, updatedAt, isActive);
            dao.registerRecruiter(p);
            response.sendRedirect("AdminController?target=Recruiter");

        }
    }

    private void banRecruiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        RecruiterDAO dao = new RecruiterDAO();
        dao.changeStatus(ID, status);
        response.sendRedirect("AdminController?target=Recruiter");
    }

    private void detailRecruiter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));

        RecruiterDAO dao = new RecruiterDAO();
        Recruiter p = dao.getSpeccificRecruiter(ID);

        request.setAttribute("Recruiter", p);
        request.getRequestDispatcher("admin_detail_recruiter.jsp").forward(request, response);
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        if (session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            response.sendRedirect("home");
            return;
        }

        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }

        switch (service) {
            case "list":
                listPromotionPrograms(request, response);
                break;
            case "viewPosts":
                viewPostsByProgram(request, response);
                break;
            case "edit":
                editPromotionProgram(request, response);
                break;
            case "add":
                addPromotionProgram(request, response);
                break;
            case "update":
                updatePromotionProgram(request, response);
                break;
            case "delete":
                deletePromotionProgram(request, response);
                break;
            default:
                listPromotionPrograms(request, response);
                break;
        }
    }

    private void listPromotionPrograms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PromotionProgramDAO programDAO = new PromotionProgramDAO();
        FeaturedJobDAO featuredJobDAO = new FeaturedJobDAO();
        PostsDAO postsDAO = new PostsDAO();
        RecruiterDAO recruiterDAO = new RecruiterDAO();

        List<PromotionProgram> promotionPrograms = programDAO.getAllPromotionPrograms();

        Map<Integer, Integer> postCounts = new HashMap<>();
        Map<Integer, Double> revenues = new HashMap<>();

        for (PromotionProgram program : promotionPrograms) {
            int postCount = featuredJobDAO.countPostsByPromotionId(program.getId());
            double revenue = featuredJobDAO.getTotalRevenueByPromotionId(program.getId());
            
            postCounts.put(program.getId(), postCount);
            revenues.put(program.getId(), revenue);
        }

        int totalActivePosts = postsDAO.getTotalPosts();
        double totalRevenue = revenues.values().stream().mapToDouble(Double::doubleValue).sum();
        int totalActiveRecruiters = recruiterDAO.countActiveRecruiters();

        request.setAttribute("promotionPrograms", promotionPrograms);
        request.setAttribute("postCounts", postCounts);
        request.setAttribute("revenues", revenues);
        request.setAttribute("totalActivePosts", totalActivePosts);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalActiveRecruiters", totalActiveRecruiters);

        request.getRequestDispatcher("admin_manage_promotion_programs.jsp").forward(request, response);
    }

    private void viewPostsByProgram(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String programIdStr = request.getParameter("programId");
        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String submit = request.getParameter("submit");

        if (programIdStr == null || programIdStr.isEmpty()) {
            response.sendRedirect("AdminController?target=program");
            return;
        }

        int programId = Integer.parseInt(programIdStr);
        int page = 1;
        int pageSize = 10;

        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        if (recordsPerPageStr != null && !recordsPerPageStr.isEmpty()) {
            pageSize = Integer.parseInt(recordsPerPageStr);
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "created_at";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "DESC";
        }

        PromotionProgramDAO programDAO = new PromotionProgramDAO();
        FeaturedJobDAO featuredJobDAO = new FeaturedJobDAO();

        PromotionProgram promotionProgram = programDAO.getPromotionProgramById(programId);
        if (promotionProgram == null) {
            response.sendRedirect("AdminController?target=program");
            return;
        }

        List<FeaturedJobDAO.PostPromotionInfo> posts;
        int totalPosts;

        if (submit != null && submit.equals("Search")) {
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));

            if (title != null && !title.isEmpty()) {
                posts = featuredJobDAO.searchPostsByPromotionIdWithPaging(programId, title, page, pageSize, sortField, sortOrder);
                totalPosts = featuredJobDAO.countSearchPostsByPromotionId(programId, title);
                request.setAttribute("searchTitle", title);
            } else {
                posts = featuredJobDAO.getPostsByPromotionIdWithPaging(programId, page, pageSize, sortField, sortOrder);
                totalPosts = featuredJobDAO.countUniquePostsByPromotionId(programId);
            }
        } else {
            posts = featuredJobDAO.getPostsByPromotionIdWithPaging(programId, page, pageSize, sortField, sortOrder);
            totalPosts = featuredJobDAO.countUniquePostsByPromotionId(programId);
        }

        double totalRevenue = featuredJobDAO.getTotalRevenueByPromotionId(programId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        request.setAttribute("promotionProgram", promotionProgram);
        request.setAttribute("posts", posts);
        request.setAttribute("totalPosts", totalPosts);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalPosts);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("admin_promotion_posts_list.jsp").forward(request, response);
    }

    private void editPromotionProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("AdminController?target=program");
            return;
        }

        int id = Integer.parseInt(idStr);
        PromotionProgramDAO programDAO = new PromotionProgramDAO();
        PromotionProgram program = programDAO.getPromotionProgramById(id);

        if (program == null) {
            response.sendRedirect("AdminController?target=program");
            return;
        }

        request.setAttribute("program", program);
        request.setAttribute("action", "edit");
        request.getRequestDispatcher("admin_add_edit_promotion.jsp").forward(request, response);
    }

    private void addPromotionProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("action", "add");
        request.getRequestDispatcher("admin_add_edit_promotion.jsp").forward(request, response);
    }

    private void updatePromotionProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String costStr = request.getParameter("cost");
        String durationDaysStr = request.getParameter("durationDays");
        String description = request.getParameter("description");
        String positionType = request.getParameter("positionType");
        String quantityStr = request.getParameter("quantity");
        String isActiveStr = request.getParameter("isActive");

        try {
            int id = Integer.parseInt(idStr);
            double cost = Double.parseDouble(costStr);
            int durationDays = Integer.parseInt(durationDaysStr);
            int quantity = Integer.parseInt(quantityStr);
            boolean isActive = Boolean.parseBoolean(isActiveStr);

            PromotionProgram program = new PromotionProgram();
            program.setId(id);
            program.setName(name);
            program.setCost(cost);
            program.setDurationDays(durationDays);
            program.setDescription(description);
            program.setPositionType(positionType);
            program.setQuantity(quantity);
            program.setActive(isActive);

            PromotionProgramDAO programDAO = new PromotionProgramDAO();
            boolean success = programDAO.updatePromotionProgram(program);

            if (success) {
                request.setAttribute("successMessage", "Cập nhật chương trình thành công!");
            } else {
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chương trình.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Dữ liệu không hợp lệ.");
        }

        response.sendRedirect("AdminController?target=program");
    }

    private void deletePromotionProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                
                PromotionProgramDAO programDAO = new PromotionProgramDAO();
                PromotionProgram program = programDAO.getPromotionProgramById(id);
                
                if (program != null) {
                    program.setActive(false);
                    boolean success = programDAO.updatePromotionProgram(program);
                    
                    if (success) {
                        request.setAttribute("successMessage", "Vô hiệu hóa chương trình thành công!");
                    } else {
                        request.setAttribute("errorMessage", "Có lỗi xảy ra khi vô hiệu hóa chương trình.");
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID không hợp lệ.");
            }
        }

        response.sendRedirect("AdminController?target=program");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String target = request.getParameter("target");
    String service = request.getParameter("service");
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