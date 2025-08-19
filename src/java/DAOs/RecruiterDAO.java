/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBContext.DBContext;
import java.sql.*;
import Models.Recruiter;

/**
 *
 * @author DELL
 */
public class RecruiterDAO extends DBContext {

    private String getAll = "SELECT *"
            + "  FROM [project_SWP391].[dbo].[Recruiter]";

    public boolean updateRecruiter(Recruiter recruiter) throws SQLException {
        String sql = "UPDATE [project_SWP391].[dbo].[Recruiter] SET "
                + "full_name = ?, phone = ?, date_of_birth = ?, gender = ?, address = ?, "
                + "profile_picture = ?, company_name = ?, company_description = ?, logo = ?, "
                + "website = ?, company_address = ?, company_size = ?, industry = ?, updated_at = GETDATE() "
                + "WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, recruiter.getFullName());
            ps.setString(2, recruiter.getPhone());
            if (recruiter.getDateOfBirth() != null) {
                ps.setDate(3, new java.sql.Date(recruiter.getDateOfBirth().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            ps.setString(4, recruiter.getGender());
            ps.setString(5, recruiter.getAddress());
            ps.setString(6, recruiter.getProfilePicture());
            ps.setString(7, recruiter.getCompanyName());
            ps.setString(8, recruiter.getCompanyDescription());
            ps.setString(9, recruiter.getLogo());
            ps.setString(10, recruiter.getWebsite());
            ps.setString(11, recruiter.getCompanyAddress());
            ps.setString(12, recruiter.getCompanySize());
            ps.setString(13, recruiter.getIndustry());
            ps.setInt(14, recruiter.getId());
            return ps.executeUpdate() > 0;
        }
    }
}
