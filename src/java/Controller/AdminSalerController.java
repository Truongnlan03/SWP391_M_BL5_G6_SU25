/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import DAOs.AdminDAO;
import DAOs.BlogDAO;
import Models.Blog;
import Models.Banner;
import DAOs.BannerDAO;
import DAOs.HomepageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Vector;
import Models.HomepageComponentContent;
import Models.HomepageComponentType;
import Utils.InputSanitizer;
import Utils.UploadPicture;

/**
 *
 * @author DELL
 */
@MultipartConfig
@WebServlet(name = "AdminSalerController", urlPatterns = {"/AdminSalerController"})
public class AdminSalerController extends HttpServlet {

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
        if (session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            response.sendRedirect("home");
            return;
        }

        AdminDAO dao = new AdminDAO();
        int userID = (int) session.getAttribute("userId");
        if (dao.getSpecificStaff(userID).getRole().equals("manager")) {
            response.sendRedirect("home");
            return;
        }

        String target = request.getParameter("target");

        if (target == null) {
            target = "blog";
        }

        if (target.equals("blog")) {
            processBlog(request, response);
        } else if (target.equals("banner")) {
            processBanner(request, response);
        } else if (target.equals("homecomponent")) {
            processHomepageComponent(request, response);
        }
    }

    private void processBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");

        if (service == null) {
            service = "listAll";
        }

        if (service.equals("listAll")) {
            listAlls(request, response);
        } else if (service.equals("listMy")) {

        } else if (service.equals("Add")) {
            addBlog(request, response);
        } else if (service.equals("Detail")) {
            detailBlog(request, response);
        } else if (service.equals("Update")) {
            updateBlog(request, response);
        } else if (service.equals("Delete")) {
            deleteBlog(request, response);
        }
    }

    private void processHomepageComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");

        if (service == null) {
            service = "listAll";
        }

        if (service.equals("listAll")) {
            listAllHomepageComponent(request, response);
        } else if (service.equals("listMy")) {
        } else if (service.equals("Add")) {
            addHomeComponent(request, response);
        } else if (service.equals("Detail")) {
            detailHomeComponent(request, response);
        } else if (service.equals("Update")) {
            updateHomeComponent(request, response);
        } else if (service.equals("Delete")) {
            deleteHomepageComponent(request, response);
        }
    }

    private void listAllHomepageComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomepageDAO dao = new HomepageDAO();
        Vector<HomepageComponentContent> components = new Vector<>();

        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String searchType = InputSanitizer.cleanSearchQuery(request.getParameter("componentType"));

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
            sortOrder = "DESC";
        }

        int totalRecords;

        if (searchType == null) {
            searchType = "";
        }

        components = dao.getComponentContentsWithPaging(searchType, page, recordsPerPage, sortField, sortOrder);
        totalRecords = dao.getComponentContentsByType(searchType);
        log("type: " + searchType + " " + components.size());

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("components", components);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("searchComponentType", searchType);

        request.getRequestDispatcher("admin_saler_all_homecomponent.jsp").forward(request, response);
    }

    private void deleteHomepageComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int componentID = Integer.parseInt(request.getParameter("componentId"));
        HomepageDAO dao = new HomepageDAO();
        boolean flag = dao.deleteComponentContent(componentID);
        response.sendRedirect("AdminSalerController?target=homecomponent");
    }

    private void detailHomeComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int componentId = Integer.parseInt(request.getParameter("componentId"));

            HomepageDAO dao = new HomepageDAO();
            HomepageComponentContent component = dao.getComponentContentById(componentId);

            if (component == null) {
                response.sendRedirect("AdminSalerController?target=homecomponent");
                return;
            }

            request.setAttribute("component", component);
            request.getRequestDispatcher("admin_saler_detail_homecomponent.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("AdminSalerController?target=homecomponent");
        }
    }

    private void updateHomeComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            submit = "";
        }

        if (submit.equals("submit")) {
            try {
                int componentId = Integer.parseInt(request.getParameter("componentId"));
                String name = InputSanitizer.cleanSearchQuery(request.getParameter("name"));
                String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));
                String typeIdStr = request.getParameter("typeId");
                String positionStr = request.getParameter("position");

                HomepageDAO dao = new HomepageDAO();
                HomepageComponentContent existingComponent = dao.getComponentContentById(componentId);

                if (existingComponent == null) {
                    response.sendRedirect("AdminSalerController?target=homecomponent");
                    return;
                }

                if (name == null || name.length() == 0) {
                    request.setAttribute("message", "Tên component không được để trống!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", existingComponent);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                if (title == null || title.length() == 0) {
                    request.setAttribute("message", "Tiêu đề không được để trống!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", existingComponent);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                if (typeIdStr == null || typeIdStr.isEmpty()) {
                    request.setAttribute("message", "Vui lòng chọn loại component!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", existingComponent);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                if (positionStr == null || positionStr.isEmpty()) {
                    request.setAttribute("message", "Vui lòng nhập vị trí hiển thị!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", existingComponent);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                int typeId = Integer.parseInt(typeIdStr);
                int position = Integer.parseInt(positionStr);

                if (position < 1) {
                    request.setAttribute("message", "Vị trí hiển thị phải lớn hơn 0!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", existingComponent);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                String thumbnail = existingComponent.getImg();

                try {
                    Part filePart = request.getPart("thumbnail");

                    if (filePart != null && filePart.getSize() > 0) {
                        String contentType = filePart.getContentType();

                        if (contentType != null && contentType.startsWith("image/")) {
                        } else {
                            request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                            request.setAttribute("component", existingComponent);
                            request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                            return;
                        }
                        String basePath = request.getServletContext().getRealPath("/");
                        thumbnail = UploadPicture.uploadImage(filePart, thumbnail, basePath);
                    } else {
                    }
                } catch (Exception e) {
                    log(e.getMessage());
                }

                String content = request.getParameter("content");
                String iconClass = request.getParameter("iconClass");
                String status = request.getParameter("status");

                if (content == null) {
                    content = "";
                }
                if (iconClass == null) {
                    iconClass = "";
                }
                if (status == null) {
                    status = "active";
                }

                HomepageComponentContent component = new HomepageComponentContent();
                component.setId(componentId);
                component.setTypeId(typeId);
                component.setPosition(position);
                component.setName(name);
                component.setTitle(title);
                component.setContent(content);
                component.setIconClass(iconClass);
                component.setStatus(status);
                component.setImg(thumbnail);

                log("update: " + thumbnail + " - " + component.getImg());
                
                boolean flag = dao.updateComponentContent(component);

                if (flag) {
                    response.sendRedirect("AdminSalerController?target=homecomponent");
                } else {
                    request.setAttribute("message", "Có lỗi xảy ra khi cập nhật component!");
                    loadComponentTypesForForm(request);
                    request.setAttribute("component", component);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                response.sendRedirect("AdminSalerController?target=homecomponent");
            }

        } else {
            try {
                int componentId = Integer.parseInt(request.getParameter("componentId"));
                HomepageDAO dao = new HomepageDAO();
                HomepageComponentContent component = dao.getComponentContentById(componentId);

                if (component == null) {
                    response.sendRedirect("AdminSalerController?target=homecomponent");
                    return;
                }
                loadComponentTypesForForm(request);
                request.setAttribute("component", component);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("AdminSalerController?target=homecomponent");
            }
        }
    }

    private void loadComponentTypesForForm(HttpServletRequest request) {
        HomepageDAO dao = new HomepageDAO();
        Vector<HomepageComponentType> componentTypes = dao.getAllComponentTypes();
        request.setAttribute("componentTypes", componentTypes);
    }

    private void addHomeComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            submit = "";
        }

        if (submit.equals("submit")) {
            String name = InputSanitizer.cleanSearchQuery(request.getParameter("name"));
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));
            String typeIdStr = request.getParameter("typeId");
            String positionStr = request.getParameter("position");

            if (name == null || name.length() == 0) {
                request.setAttribute("message", "Tên component không được để trống!");
                loadComponentTypesForForm(request);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                return;
            }

            if (title == null || title.length() == 0) {
                request.setAttribute("message", "Tiêu đề không được để trống!");
                loadComponentTypesForForm(request);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                return;
            }

            if (typeIdStr == null || typeIdStr.isEmpty()) {
                request.setAttribute("message", "Vui lòng chọn loại component!");
                loadComponentTypesForForm(request);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                return;
            }

            if (positionStr == null || positionStr.isEmpty()) {
                request.setAttribute("message", "Vui lòng nhập vị trí hiển thị!");
                loadComponentTypesForForm(request);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                return;
            }

            String thumbnail = "";
            try {
                Part filePart = request.getPart("thumbnail");

                String contentType = filePart.getContentType();

                if (contentType != null && contentType.startsWith("image/")) {
                } else {
                    request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                String basePath = request.getServletContext().getRealPath("/");
                thumbnail = UploadPicture.uploadImage(filePart, thumbnail, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }

            try {
                int typeId = Integer.parseInt(typeIdStr);
                int position = Integer.parseInt(positionStr);

                if (position < 1) {
                    request.setAttribute("message", "Vị trí hiển thị phải lớn hơn 0!");
                    loadComponentTypesForForm(request);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                    return;
                }

                String content = request.getParameter("content");
                String iconClass = request.getParameter("iconClass");
                String status = request.getParameter("status");

                if (content == null) {
                    content = "";
                }
                if (iconClass == null) {
                    iconClass = "";
                }
                if (status == null) {
                    status = "active";
                }

                HomepageComponentContent component = new HomepageComponentContent();
                component.setTypeId(typeId);
                component.setPosition(position);
                component.setName(name);
                component.setTitle(title);
                component.setContent(content);
                component.setIconClass(iconClass);
                component.setStatus(status);
                component.setImg(thumbnail);

                HomepageDAO dao = new HomepageDAO();
                boolean flag = dao.insertComponentContent(component);

                if (flag) {
                    response.sendRedirect("AdminSalerController?target=homecomponent");
                } else {
                    request.setAttribute("message", "Có lỗi xảy ra khi thêm component!");
                    loadComponentTypesForForm(request);
                    request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Dữ liệu không hợp lệ!");
                loadComponentTypesForForm(request);
                request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
            }

        } else {
            loadComponentTypesForForm(request);
            request.getRequestDispatcher("admin_saler_add_homecomponent.jsp").forward(request, response);
        }
    }

    private void listAlls(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        Vector<Blog> blogs = new Vector<>();

        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String searchTitle = InputSanitizer.cleanSearchQuery(request.getParameter("title"));

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
            sortOrder = "DESC";
        }

        int totalRecords;

        String who = request.getParameter("who");
        if (who == null) {
            who = "";
        }

        if (searchTitle != null && !searchTitle.isEmpty()) {
            blogs = blogDAO.searchBlogsByTitleWithPaging(searchTitle, page, recordsPerPage, sortField, sortOrder);
            totalRecords = blogDAO.getTotalBlogsByTitle(searchTitle);
        } else {
            blogs = blogDAO.getAllBlogsWithPagingAndSorting(page, recordsPerPage, sortField, sortOrder);
            totalRecords = blogDAO.getTotalBlogs();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("searchTitle", searchTitle);

        request.getRequestDispatcher("admin_saler_allblog.jsp").forward(request, response);
    }

    private void listAllBanners(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BannerDAO bannerDAO = new BannerDAO();
        Vector<Banner> banners = new Vector<>();

        String pageStr = request.getParameter("page");
        String recordsPerPageStr = request.getParameter("recordsPerPage");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String searchTitle = InputSanitizer.cleanSearchQuery(request.getParameter("title"));

        int page = 1;
        int recordsPerPage = 10;

        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        if (recordsPerPageStr != null && !recordsPerPageStr.isEmpty()) {
            recordsPerPage = Integer.parseInt(recordsPerPageStr);
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "position";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "ASC";
        }

        int totalRecords;

        if (searchTitle != null && !searchTitle.isEmpty()) {
            banners = bannerDAO.searchBannersByTitleWithPaging(searchTitle, page, recordsPerPage, sortField, sortOrder);
            totalRecords = bannerDAO.getTotalBannersByTitle(searchTitle);
        } else {
            banners = bannerDAO.getAllBannersWithPagingAndSorting(page, recordsPerPage, sortField, sortOrder);
            totalRecords = bannerDAO.getTotalBanners();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("banners", banners);
        request.setAttribute("currentPage", page);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("searchTitle", searchTitle);

        request.getRequestDispatcher("admin_saler_allbanner.jsp").forward(request, response);
    }

    private void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        Vector<Blog> blogs = new Vector<>();

        String who = request.getParameter("who");
        if (who == null) {
            who = "";
        }

        if (who.equals("me")) {
            HttpSession session = request.getSession(true);
            int id = (int) session.getAttribute("userId");
            blogs = blogDAO.getBlogsByAdminId(id);
        } else {
            blogs = blogDAO.getAllBlogs(); 
        }

        log("di qua day ma");
        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("admin_saler_allblog.jsp").forward(request, response);
    }

    private void addBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");

        if (submit == null) {
            submit = "";
        }

        log(submit + " ye");

        if (submit.equals("submit")) {
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));

            log("blah" + title + "blah");
            log("blah" + title.length() + "blah");

            if (title == null) {
                request.setAttribute("message", "Tiêu đề không được để trống!");
                request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
                return;
            } else if (title.length() == 0) {
                request.setAttribute("message", "Tiêu đề không được để trống!");
                request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
                return;
            }

            String thumbnail = "";
            try {
                Part filePart = request.getPart("thumbnail");

                String contentType = filePart.getContentType();

                if (contentType != null && contentType.startsWith("image/")) {
                } else {
                    request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                    request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
                    return;
                }

                String basePath = request.getServletContext().getRealPath("/");
                thumbnail = UploadPicture.uploadImage(filePart, thumbnail, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }

            String description = request.getParameter("description");
            String status = "draft";
            int admin_id = 1;

            Blog blog = new Blog(admin_id, title, description, thumbnail, status);
            BlogDAO dao = new BlogDAO();
            boolean flag = dao.insertBlog(blog);
            log("add " + flag);
            response.sendRedirect("AdminSalerController?target=blog");
        } else {
            request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
        }
    }

    private void detailBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        BlogDAO dao = new BlogDAO();
        Blog blog = dao.getBlogById(blogId);
        request.setAttribute("blog", blog);
        request.getRequestDispatcher("admin_saler_detail_blog.jsp").forward(request, response);
    }

    private void updateBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");

        if (submit == null) {
            submit = "";
        }
        if (submit.equals("submit")) {
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));
            int blogId = Integer.parseInt(request.getParameter("blogId"));
            BlogDAO dao = new BlogDAO();
            Blog x = dao.getBlogById(blogId);

            String thumbnail = x.getThumbnail();

            try {
                Part filePart = request.getPart("thumbnail");

                if (filePart != null && filePart.getSize() > 0) {
                    String contentType = filePart.getContentType();

                    if (contentType != null && contentType.startsWith("image/")) {
                    } else {
                        // Không phải ảnh
                        request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                        request.setAttribute("blog", x);
                        request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
                        return;
                    }
                    String basePath = request.getServletContext().getRealPath("/");
                    thumbnail = UploadPicture.uploadImage(filePart, thumbnail, basePath);
                } else {
                }
            } catch (Exception e) {
                log(e.getMessage());
            }
            String description = request.getParameter("description");
            String status = "draft";
            int admin_id = 1;
            int blogID = Integer.parseInt(request.getParameter("blogId"));
            Blog blog = new Blog(blogID, admin_id, title, description, thumbnail, status);
            boolean flag = dao.updateBlogFields(blog);

            response.sendRedirect("AdminSalerController?target=blog");

        } else {
            int blogId = Integer.parseInt(request.getParameter("blogId"));
            BlogDAO dao = new BlogDAO();
            Blog blog = dao.getBlogById(blogId);

            request.setAttribute("blog", blog);
            request.getRequestDispatcher("admin_saler_add_blog.jsp").forward(request, response);
        }
    }

    private void deleteBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int blogID = Integer.parseInt(request.getParameter("blogId"));

        BlogDAO dao = new BlogDAO();
        boolean flag = dao.deleteBlog(blogID);

        response.sendRedirect("AdminSalerController?target=blog");
    }

    private void processBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");

        if (service == null) {
            service = "listAll";
        }
        if (service.equals("listAll")) {
            listAllBanners(request, response);
        } else if (service.equals("listMy")) {
        } else if (service.equals("Add")) {
            addBanner(request, response);
        } else if (service.equals("Detail")) {
            detailBanner(request, response);
        } else if (service.equals("Update")) {
            updateBanner(request, response);
        } else if (service.equals("Delete")) {
            deleteBanner(request, response);
        }
    }

    private void listAllBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BannerDAO bannerDAO = new BannerDAO();
        Vector<Banner> banners = new Vector<>();

        String who = request.getParameter("who");
        if (who == null) {
            who = "";
        }
        if (who.equals("me")) {
            HttpSession session = request.getSession(true);
            int id = (int) session.getAttribute("userId");
            banners = bannerDAO.getBannersByAdminId(id);
        } else {
            banners = bannerDAO.getAllBanners(); 
        }
        log("banner " + banners.size());

        request.setAttribute("banners", banners);
        request.getRequestDispatcher("admin_saler_allbanner.jsp").forward(request, response);
    }

    private void addBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");

        if (submit == null) {
            submit = "";
        }

        if (submit.equals("submit")) {
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));
            log("da qua day ");

            String imageUrl = "";
            try {
                Part filePart = request.getPart("file");

                String contentType = filePart.getContentType();

                if (contentType != null && contentType.startsWith("image/")) {
                } else {
                    request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                    request.getRequestDispatcher("admin_saler_add_banner.jsp").forward(request, response);
                    return;
                }

                String basePath = request.getServletContext().getRealPath("/");
                imageUrl = UploadPicture.uploadImage(filePart, imageUrl, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }

            String redirectUrl = request.getParameter("redirect_url");
            int position = Integer.parseInt(request.getParameter("position"));
            boolean isActive = request.getParameter("is_active") != null;
            int admin_id = 1;

            Banner banner = new Banner(admin_id, title, imageUrl, redirectUrl, position, isActive);

            BannerDAO dao = new BannerDAO();
            boolean flag = dao.insertBanner(banner);
            log("Add banner: " + flag);
            response.sendRedirect("AdminSalerController?target=banner");
        } else {
            request.getRequestDispatcher("admin_saler_add_banner.jsp").forward(request, response);
        }
    }

    private void updateBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null) {
            submit = "";
        }
        if (submit.equals("submit")) {
            String title = InputSanitizer.cleanSearchQuery(request.getParameter("title"));
            int bannerId = Integer.parseInt(request.getParameter("bannerId"));
            BannerDAO dao = new BannerDAO();
            Banner x = dao.getBannerById(bannerId);

            String imageUrl = x.getImage_url();
            try {
                Part filePart = request.getPart("file");

                if (filePart != null && filePart.getSize() > 0) {
                    String contentType = filePart.getContentType();

                    if (contentType != null && contentType.startsWith("image/")) {
                    } else {
                        request.setAttribute("mustbeImg", "Chỉ cho phép upload file ảnh!");
                        request.setAttribute("banner", x);
                        request.getRequestDispatcher("admin_saler_add_banner.jsp").forward(request, response);
                        return;
                    }
                }

                String basePath = request.getServletContext().getRealPath("/");
                imageUrl = UploadPicture.uploadImage(filePart, imageUrl, basePath);

            } catch (Exception e) {
                log(e.getMessage());
            }

            String redirectUrl = request.getParameter("redirect_url");
            int position = Integer.parseInt(request.getParameter("position"));
            boolean isActive = request.getParameter("is_active") != null;
            int admin_id = 1; 
            Banner banner = new Banner(bannerId, admin_id, title, imageUrl, redirectUrl, position, isActive, null);
            boolean flag = dao.updateBanner(banner);

            response.sendRedirect("AdminSalerController?target=banner");
        } else {
            int bannerId = Integer.parseInt(request.getParameter("bannerId"));
            BannerDAO dao = new BannerDAO();
            Banner banner = dao.getBannerById(bannerId);

            request.setAttribute("banner", banner);
            request.getRequestDispatcher("admin_saler_add_banner.jsp").forward(request, response);
        }
    }

    private void detailBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bannerId = Integer.parseInt(request.getParameter("bannerId"));

        BannerDAO dao = new BannerDAO();
        Banner banner = dao.getBannerById(bannerId);

        request.setAttribute("banner", banner);
        request.getRequestDispatcher("admin_saler_detail_banner.jsp").forward(request, response);
    }

    private void deleteBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bannerId = Integer.parseInt(request.getParameter("bannerId"));

        BannerDAO dao = new BannerDAO();
        boolean flag = dao.deleteBanner(bannerId);

        response.sendRedirect("AdminSalerController?target=banner");
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
