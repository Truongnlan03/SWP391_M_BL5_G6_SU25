/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DAOs.JobSearchDAO;
import DAOs.PostsDAO;
import DAOs.SearchSuggestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Models.AdvancedSearchCriteria;
import Models.JobSearch;
import Models.Posts;

/**
 *
 * @author DELL
 */
@WebServlet(name = "AdvancedSearchController", urlPatterns = {"/advanced-search"})
public class AdvancedSearchController extends HttpServlet {

    private PostsDAO postsDAO;
    private JobSearchDAO jobSearchDAO;
    private SearchSuggestionDAO searchSuggestionDAO;

    @Override
    public void init() throws ServletException {
        postsDAO = new PostsDAO();
        jobSearchDAO = new JobSearchDAO();
        searchSuggestionDAO = new SearchSuggestionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AdvancedSearchCriteria criteria = parseSearchCriteria(request);

            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }

            List<Posts> jobs = postsDAO.advancedSearch(criteria);
            int totalJobs = postsDAO.countAdvancedSearchResults(criteria);

            if (!action.equals("search")) {
                jobs = postsDAO.getAllPosts();
                totalJobs = jobs.size();
            }

            int totalPages = (int) Math.ceil((double) totalJobs / criteria.getPageSize());

            HttpSession session = request.getSession();
            Integer jobSeekerId = (Integer) session.getAttribute("userId");
            if (jobSeekerId != null && criteria.hasFilters()) {
                try {
                    saveSearchHistory(jobSeekerId, criteria, totalJobs);
                } catch (Exception e) {
                    System.err.println("Error saving search history: " + e.getMessage());
                }
            }

            List<String> popularKeywords = new ArrayList<>();
            List<String> popularLocations = new ArrayList<>();
            try {
                popularKeywords = searchSuggestionDAO.getPopularKeywords(10);
                popularLocations = searchSuggestionDAO.getPopularLocations(10);
            } catch (Exception e) {
                System.err.println("Error getting search suggestions: " + e.getMessage());
            }

            request.setAttribute("jobs", jobs);
            request.setAttribute("totalJobs", totalJobs);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", criteria.getPage());
            request.setAttribute("searchTime", 0);
            request.setAttribute("popularKeywords", popularKeywords);
            request.setAttribute("popularLocations", popularLocations);
            request.setAttribute("criteria", criteria);

            String queryString = buildQueryString(request);
            request.setAttribute("queryString", queryString);

            request.getRequestDispatcher("/advanced-search.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi tìm kiếm: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private AdvancedSearchCriteria parseSearchCriteria(HttpServletRequest request) {
        AdvancedSearchCriteria criteria = new AdvancedSearchCriteria();

        criteria.setKeyword(request.getParameter("keyword"));
        criteria.setLocation(request.getParameter("location"));
        criteria.setIndustry(request.getParameter("industry"));

        criteria.setJobType(request.getParameter("jobType"));
        criteria.setExperienceLevel(request.getParameter("experienceLevel"));
        criteria.setWorkType(request.getParameter("workType"));

        String minSalaryStr = request.getParameter("minSalary");
        if (minSalaryStr != null && !minSalaryStr.trim().isEmpty()) {
            try {
                criteria.setMinSalary(new BigDecimal(minSalaryStr));
            } catch (NumberFormatException e) {
            }
        }

        String maxSalaryStr = request.getParameter("maxSalary");
        if (maxSalaryStr != null && !maxSalaryStr.trim().isEmpty()) {
            try {
                criteria.setMaxSalary(new BigDecimal(maxSalaryStr));
            } catch (NumberFormatException e) {
            }
        }

        criteria.setCompanySize(request.getParameter("companySize"));
        criteria.setBenefits(request.getParameter("benefits"));

        criteria.setSkills(request.getParameter("skills"));
        criteria.setEducation(request.getParameter("education"));
        criteria.setLanguage(request.getParameter("language"));

        criteria.setSortBy(request.getParameter("sortBy"));
        criteria.setSortOrder(request.getParameter("sortOrder"));

        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                criteria.setPage(Integer.parseInt(pageStr));
            } catch (NumberFormatException e) {
                criteria.setPage(1);
            }
        }

        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null && !pageSizeStr.trim().isEmpty()) {
            try {
                criteria.setPageSize(Integer.parseInt(pageSizeStr));
            } catch (NumberFormatException e) {
                criteria.setPageSize(10);
            }
        }

        String saveSearch = request.getParameter("saveSearch");
        if ("true".equals(saveSearch)) {
            criteria.setSaved(true);
            criteria.setSearchName(request.getParameter("searchName"));
        }

        return criteria;
    }

    private void saveSearchHistory(Integer jobSeekerId, AdvancedSearchCriteria criteria, int resultCount) {
        try {
            JobSearch jobSearch = new JobSearch();
            jobSearch.setJobSeekerId(jobSeekerId);
            jobSearch.setSearchType("advanced");
            jobSearch.setKeyword(criteria.getKeyword());
            jobSearch.setLocation(criteria.getLocation());
            jobSearch.setIndustry(criteria.getIndustry());
            jobSearch.setJobLevel(criteria.getExperienceLevel());
            jobSearch.setJobType(criteria.getJobType());
            jobSearch.setMinSalary(criteria.getMinSalary());
            jobSearch.setMaxSalary(criteria.getMaxSalary());
            jobSearch.setBenefits(criteria.getBenefits());
            jobSearch.setLanguage(criteria.getLanguage());
            jobSearch.setSortBy(criteria.getSortBy());
            jobSearch.setSortOrder(criteria.getSortOrder());
            jobSearch.setIsSaved(criteria.isSaved() ? true : false);
            jobSearch.setSearchName(criteria.getSearchName());
            jobSearch.setResultCount(resultCount);
            jobSearch.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            jobSearch.setLastUsed(new Timestamp(System.currentTimeMillis()));

            jobSearchDAO.saveJobSearch(jobSearch);

            try {
                if (criteria.getKeyword() != null && !criteria.getKeyword().trim().isEmpty()) {
                    searchSuggestionDAO.updateKeywordFrequency(criteria.getKeyword(), "job_title");
                }
                if (criteria.getLocation() != null && !criteria.getLocation().trim().isEmpty()) {
                    searchSuggestionDAO.updateKeywordFrequency(criteria.getLocation(), "location");
                }
            } catch (Exception e) {
                System.err.println("Error updating search suggestions: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildQueryString(HttpServletRequest request) {
        StringBuilder queryString = new StringBuilder();

        String[] params = {
            "keyword", "location", "industry", "jobType", "experienceLevel",
            "workType", "minSalary", "maxSalary", "companySize", "benefits",
            "skills", "education", "language", "sortBy", "sortOrder"
        };

        for (String param : params) {
            String value = request.getParameter(param);
            if (value != null && !value.trim().isEmpty()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(param).append("=").append(value);
            }
        }
        return queryString.toString();
    }
}
