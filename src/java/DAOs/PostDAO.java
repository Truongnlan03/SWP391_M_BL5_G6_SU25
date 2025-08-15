package DAOs;

import DBContext.DBContext;
import Models.Post;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDAO extends DBContext {

    public boolean insertPost(Post post) {
        String sql = """
        INSERT INTO Posts (
            user_id, user_type, post_type, title, content, status, created_at, 
            company_name, salary, location, job_type, experience, deadline, 
            job_description, requirements, benefits, contact_address, 
            application_method, quantity
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null || conn.isClosed()) {
                Logger.getLogger(PostDAO.class.getName())
                        .log(Level.SEVERE, "Connection is null/closed");
                return false;
            }

            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getUserType());
            ps.setString(3, post.getPostType());
            ps.setString(4, post.getTitle());
            ps.setString(5, post.getContent());
            ps.setString(6, post.getStatus());

            // created_at
            ps.setTimestamp(7, post.getCreatedAt() != null
                    ? post.getCreatedAt()
                    : new Timestamp(System.currentTimeMillis()));

            // company_name
            if (post.getCompanyName() != null && !post.getCompanyName().trim().isEmpty()) {
                ps.setString(8, post.getCompanyName());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }

            // salary (varchar)
            if (post.getSalary() != null && !post.getSalary().trim().isEmpty()) {
                ps.setString(9, post.getSalary());
            } else {
                ps.setNull(9, Types.VARCHAR);
            }

            // location
            if (post.getLocation() != null && !post.getLocation().trim().isEmpty()) {
                ps.setString(10, post.getLocation());
            } else {
                ps.setNull(10, Types.VARCHAR);
            }

            // job_type
            if (post.getJobType() != null && !post.getJobType().trim().isEmpty()) {
                ps.setString(11, post.getJobType());
            } else {
                ps.setNull(11, Types.VARCHAR);
            }

            // experience
            if (post.getExperience() != null && !post.getExperience().trim().isEmpty()) {
                ps.setString(12, post.getExperience());
            } else {
                ps.setNull(12, Types.VARCHAR);
            }

            // ✅ deadline: nếu null phải dùng setNull
            if (post.getDeadline() != null) {
                ps.setDate(13, post.getDeadline());
            } else {
                ps.setNull(13, Types.DATE);
            }

            // job_description
            if (post.getJobDescription() != null && !post.getJobDescription().trim().isEmpty()) {
                ps.setString(14, post.getJobDescription());
            } else {
                ps.setNull(14, Types.VARCHAR);
            }

            // requirements
            if (post.getRequirements() != null && !post.getRequirements().trim().isEmpty()) {
                ps.setString(15, post.getRequirements());
            } else {
                ps.setNull(15, Types.VARCHAR);
            }

            // benefits
            if (post.getBenefits() != null && !post.getBenefits().trim().isEmpty()) {
                ps.setString(16, post.getBenefits());
            } else {
                ps.setNull(16, Types.VARCHAR);
            }

            // contact_address
            if (post.getContactAddress() != null && !post.getContactAddress().trim().isEmpty()) {
                ps.setString(17, post.getContactAddress());
            } else {
                ps.setNull(17, Types.VARCHAR);
            }

            // application_method
            if (post.getApplicationMethod() != null && !post.getApplicationMethod().trim().isEmpty()) {
                ps.setString(18, post.getApplicationMethod());
            } else {
                ps.setNull(18, Types.VARCHAR);
            }

            // quantity
            if (post.getQuantity() != null) {
                ps.setInt(19, post.getQuantity());
            } else {
                ps.setNull(19, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(PostDAO.class.getName())
                    .log(Level.SEVERE, "Error inserting post", e);
            return false;
        }
    }

    public List<Post> getPostsByUserId(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setTitle(rs.getString("title"));
                    p.setDescription(rs.getString("description"));
                    p.setLocation(rs.getString("location"));
                    p.setSalary(rs.getString("salary"));

                    BigDecimal salaryMin = rs.getBigDecimal("salary_min");
                    BigDecimal salaryMax = rs.getBigDecimal("salary_max");

                    p.setSalaryMin(salaryMin != null ? salaryMin : BigDecimal.ZERO);
                    p.setSalaryMax(salaryMax != null ? salaryMax : BigDecimal.ZERO);

                    p.setDeadline(rs.getDate("deadline"));
                    p.setStatus(rs.getString("status"));
                    p.setExperienceYears(rs.getObject("experience_years") != null ? rs.getInt("experience_years") : 0);
                    p.setEducationLevel(rs.getString("education_level"));
                    p.setSkillsRequired(rs.getString("skills_required"));
                    p.setLanguagesRequired(rs.getString("languages_required"));
                    p.setWorkEnvironment(rs.getString("work_environment"));
                    p.setJobLevel(rs.getString("job_level"));
                    p.setContractType(rs.getString("contract_type"));
                    p.setProbationPeriod(rs.getString("probation_period"));
                    p.setApplicationDeadline(rs.getDate("application_deadline"));
                    p.setIsFeatured(rs.getBoolean("is_featured"));
                    p.setIsUrgent(rs.getBoolean("is_urgent"));
                    p.setSearchPriority(rs.getObject("search_priority") != null ? rs.getInt("search_priority") : 0);
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    p.setUpdatedAt(rs.getTimestamp("updated_at"));

                    list.add(p);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PostDAO.class.getName())
                    .log(Level.SEVERE, "Error fetching posts", e);
        }

        return list;
    }
}
