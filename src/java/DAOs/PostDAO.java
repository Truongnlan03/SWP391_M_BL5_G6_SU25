package DAOs;

import DBContext.DBContext;
import Models.Posts;
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

    // Insert bài viết mới
    public boolean insertPost(Posts post) {
        String sql = """
        INSERT INTO Posts (
            user_id, user_type, post_type, title, content, status, created_at, 
            company_name, salary, location, job_type, experience, deadline, 
            job_description, requirements, benefits, contact_address, 
            application_method, quantity
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

            // salary
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

            // deadline
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

    // Lấy danh sách bài viết theo user_id
    public List<Posts> getPostsByUserId(int userId) {
        List<Posts> posts = new ArrayList<>();
        String sql = "SELECT * FROM Posts WHERE user_id = ? ";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Posts p = new Posts();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
                p.setLocation(rs.getString("location"));
                p.setSalary(rs.getString("salary"));
                p.setSalaryMin(rs.getDouble("salary_min"));
                p.setSalaryMax(rs.getDouble("salary_max"));
                p.setDeadline(rs.getDate("deadline"));
                p.setStatus(rs.getString("status"));
                p.setExperienceYears(rs.getInt("experience_years"));
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
                p.setSearchPriority(rs.getInt("search_priority"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setUpdatedAt(rs.getTimestamp("updated_at"));
                // map thêm nếu cần

                posts.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    public boolean deletePost(int postId, int userId) throws SQLException {
    String sql = "DELETE FROM posts WHERE id = ? AND user_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, postId);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    }
}
public boolean updatePost(Posts post) throws SQLException {
    String sql = "UPDATE posts SET title=?, content=?, salary=?, location=?, job_type=?, " +
                 "experience=?, deadline=?, working_time=?, job_description=?, requirements=?, " +
                 "benefits=?, contact_address=?, application_method=?, quantity=?, rank=?, " +
                 "industry=?, contact_person=?, company_size=?, company_website=?, " +
                 "company_description=?, keywords=?, updated_at=NOW() " +
                 "WHERE id=? AND user_id=?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, post.getTitle());
        ps.setString(2, post.getContent());
        ps.setString(3, post.getSalary());
        ps.setString(4, post.getLocation());
        ps.setString(5, post.getJobType());
        ps.setString(6, post.getExperience());
        ps.setDate(7, post.getDeadline());
        ps.setString(8, post.getWorkingTime());
        ps.setString(9, post.getJobDescription());
        ps.setString(10, post.getRequirements());
        ps.setString(11, post.getBenefits());
        ps.setString(12, post.getContactAddress());
        ps.setString(13, post.getApplicationMethod());
        if (post.getQuantity() != null) {
            ps.setInt(14, post.getQuantity());
        } else {
            ps.setNull(14, java.sql.Types.INTEGER);
        }
        ps.setString(15, post.getRank());
        ps.setString(16, post.getIndustry());
        ps.setString(17, post.getContactPerson());
        ps.setString(18, post.getCompanySize());
        ps.setString(19, post.getCompanyWebsite());
        ps.setString(20, post.getCompanyDescription());
        ps.setString(21, post.getKeywords());
        ps.setInt(22, post.getId());
        ps.setInt(23, post.getUserId());
        return ps.executeUpdate() > 0;
    }
}
public Posts getPostById(int postId) {
    String sql = "SELECT * FROM Posts WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, postId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Posts p = new Posts();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setUserType(rs.getString("user_type"));
                p.setPostType(rs.getString("post_type"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
                p.setStatus(rs.getString("status"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setUpdatedAt(rs.getTimestamp("updated_at"));

                p.setCompanyName(rs.getString("company_name"));
                p.setSalary(rs.getString("salary"));
                p.setLocation(rs.getString("location"));
                p.setJobType(rs.getString("job_type"));
                p.setExperience(rs.getString("experience"));
                p.setDeadline(rs.getDate("deadline"));

                p.setJobDescription(rs.getString("job_description"));
                p.setRequirements(rs.getString("requirements"));
                p.setBenefits(rs.getString("benefits"));
                p.setContactAddress(rs.getString("contact_address"));
                p.setApplicationMethod(rs.getString("application_method"));

                int quantity = rs.getInt("quantity");
                if (!rs.wasNull()) {
                    p.setQuantity(quantity);
                }

                // các field bổ sung từ updatePost
                p.setWorkingTime(rs.getString("working_time"));
                p.setRank(rs.getString("rank"));
                p.setIndustry(rs.getString("industry"));
                p.setContactPerson(rs.getString("contact_person"));
                p.setCompanySize(rs.getString("company_size"));
                p.setCompanyWebsite(rs.getString("company_website"));
                p.setCompanyDescription(rs.getString("company_description"));
                p.setKeywords(rs.getString("keywords"));

                // các field mở rộng nếu có
                p.setSalaryMin(rs.getDouble("salary_min"));
                p.setSalaryMax(rs.getDouble("salary_max"));
                p.setExperienceYears(rs.getInt("experience_years"));
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
                p.setSearchPriority(rs.getInt("search_priority"));

                return p;
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(PostDAO.class.getName())
              .log(Level.SEVERE, "Error fetching post detail", e);
    }
    return null;
}
}
