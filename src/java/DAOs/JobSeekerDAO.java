/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import DBContext.DBContext;
import Models.JobSeeker;

/**
 *
 * @author DELL
 */
public class JobSeekerDAO extends DBContext {

    public boolean updateContactInfo(JobSeeker jobSeeker) {
        String sql = "UPDATE [project_SWP391].[dbo].[Job_Seekers] SET full_name = ?, email = ?, phone = ?, address = ?, updated_at = GETDATE() WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, jobSeeker.getFullName());
            ps.setString(2, jobSeeker.getEmail());
            ps.setString(3, jobSeeker.getPhone());
            ps.setString(4, jobSeeker.getAddress());
            ps.setInt(5, jobSeeker.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
