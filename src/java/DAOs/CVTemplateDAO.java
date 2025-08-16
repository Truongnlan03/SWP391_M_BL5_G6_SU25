package DAOs;

import DBContext.DBContext;
import Models.CVTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class CVTemplateDAO extends DBContext {

    public boolean insertCV(CVTemplate cv) {
        String sql = "INSERT INTO cv_templates ("
                + "job_seeker_id, full_name, job_position, birth_date, gender, phone, email, website, address, "
                + "career_goal, education, work_experience, certificates, image_path, created_at, updated_at"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cv.getJobSeekerId());
            ps.setString(2, cv.getFullName());
            ps.setString(3, cv.getJobPosition());

            // birth_date có thể null
            if (cv.getBirthDate() != null) {
                ps.setDate(4, (cv.getBirthDate()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, cv.getGender());
            ps.setString(6, cv.getPhone());
            ps.setString(7, cv.getEmail());
            ps.setString(8, cv.getWebsite());
            ps.setString(9, cv.getAddress());
            ps.setString(10, cv.getCareerGoal());
            ps.setString(11, cv.getEducation());
            ps.setString(12, cv.getWorkExperience());
            ps.setString(13, cv.getCertificates());
            ps.setString(14, cv.getPdfFilePath());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CVTemplate> getCVsByUserId(Integer userId, String keyword, String sort, int offset, int limit) {
        List<CVTemplate> list = new ArrayList<>();
        String sql = "SELECT * FROM cv_templates WHERE job_seeker_id = ?";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND job_position LIKE ?";
        }

        if ("asc".equalsIgnoreCase(sort)) {
            sql += " ORDER BY created_at ASC";
        } else {
            sql += " ORDER BY created_at DESC";
        }

        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // SQL Server phân trang

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            ps.setInt(index++, userId);

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword.trim() + "%");
            }

            ps.setInt(index++, offset);
            ps.setInt(index, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CVTemplate cv = new CVTemplate();
                cv.setId(rs.getInt("id"));
                cv.setJobSeekerId(rs.getInt("job_seeker_id"));
                cv.setFullName(rs.getString("full_name"));
                cv.setJobPosition(rs.getString("job_position"));
                cv.setBirthDate(rs.getDate("birth_date"));
                cv.setGender(rs.getString("gender"));
                cv.setPhone(rs.getString("phone"));
                cv.setEmail(rs.getString("email"));
                cv.setWebsite(rs.getString("website"));
                cv.setAddress(rs.getString("address"));
                cv.setCareerGoal(rs.getString("career_goal"));
                cv.setEducation(rs.getString("education"));
                cv.setWorkExperience(rs.getString("work_experience"));
                cv.setCertificates(rs.getString("certificates"));
                cv.setPdfFilePath(rs.getString("image_path"));
                cv.setCreatedAt(rs.getTimestamp("created_at"));
                cv.setUpdatedAt(rs.getTimestamp("updated_at"));
                cv.setCv_link(rs.getString("cv_link"));

                list.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countCVsByUserId(Integer userId, String keyword) {
        String sql = "SELECT COUNT(*) FROM cv_templates WHERE job_seeker_id = ?";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND job_position LIKE ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(2, "%" + keyword.trim() + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

        public CVTemplate getCVById(int id) {
        String sql = "SELECT * FROM cv_templates WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CVTemplate cv = new CVTemplate();
                cv.setId(rs.getInt("id"));
                cv.setJobSeekerId(rs.getInt("job_seeker_id"));
                cv.setFullName(rs.getString("full_name"));
                cv.setJobPosition(rs.getString("job_position"));
                cv.setBirthDate(rs.getDate("birth_date"));
                cv.setGender(rs.getString("gender"));
                cv.setPhone(rs.getString("phone"));
                cv.setEmail(rs.getString("email"));
                cv.setWebsite(rs.getString("website"));
                cv.setAddress(rs.getString("address"));
                cv.setCareerGoal(rs.getString("career_goal"));
                cv.setEducation(rs.getString("education"));
                cv.setWorkExperience(rs.getString("work_experience"));
                cv.setCertificates(rs.getString("certificates"));
                cv.setPdfFilePath(rs.getString("image_path"));
                cv.setCreatedAt(rs.getTimestamp("created_at"));
                cv.setUpdatedAt(rs.getTimestamp("updated_at"));
                return cv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCV(CVTemplate cv) {
        String sql = "UPDATE cv_templates SET "
                + "full_name = ?, "
                + "job_position = ?, "
                + "birth_date = ?, "
                + "gender = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "website = ?, "
                + "address = ?, "
                + "career_goal = ?, "
                + "education = ?, "
                + "work_experience = ?, "
                + "certificates = ?, "
                + "image_path = ?, "
                + "updated_at = GETDATE() "
                + "WHERE id = ? AND job_seeker_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cv.getFullName());
            ps.setString(2, cv.getJobPosition());

            if (cv.getBirthDate() != null) {
                ps.setDate(3, cv.getBirthDate());
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            ps.setString(4, cv.getGender());
            ps.setString(5, cv.getPhone());
            ps.setString(6, cv.getEmail());
            ps.setString(7, cv.getWebsite());
            ps.setString(8, cv.getAddress());
            ps.setString(9, cv.getCareerGoal());
            ps.setString(10, cv.getEducation());
            ps.setString(11, cv.getWorkExperience());
            ps.setString(12, cv.getCertificates());
            ps.setString(13, cv.getPdfFilePath());
            ps.setInt(14, cv.getId());
            ps.setInt(15, cv.getJobSeekerId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                System.err.println("[updateCV] Không tìm thấy CV để cập nhật. "
                        + "ID: " + cv.getId()
                        + ", JobSeekerId: " + cv.getJobSeekerId());
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("[updateCV] Lỗi SQL khi cập nhật CV:");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCV(int id, int jobSeekerId) {
        String sql = "DELETE FROM cv_templates WHERE id = ? AND job_seeker_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, jobSeekerId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                System.err.println("[deleteCV] Không tìm thấy CV để xóa. ID: " + id + ", JobSeekerId: " + jobSeekerId);
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("[deleteCV] Lỗi SQL khi xóa CV:");
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCVLink(int jobSeekerId, String cvLink, int id) {
        try {
            if (id == 0) {
                String insertSql = "INSERT INTO cv_templates (job_seeker_id, cv_link, created_at, updated_at) "
                        + "VALUES (?, ?, GETDATE(), GETDATE())";
                try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                    ps.setInt(1, jobSeekerId);
                    ps.setString(2, cvLink);
                    return ps.executeUpdate() > 0;
                }
            } else {
                String updateSql = "UPDATE cv_templates SET cv_link = ?, updated_at = GETDATE() "
                        + "WHERE id = ? AND job_seeker_id = ?";
                try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                    ps.setString(1, cvLink);
                    ps.setInt(2, id);
                    ps.setInt(3, jobSeekerId);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
