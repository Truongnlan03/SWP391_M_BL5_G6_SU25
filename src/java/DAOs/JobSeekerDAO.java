/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import DBContext.DBContext;
import Models.JobSeeker;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author DELL
 */
public class JobSeekerDAO extends DBContext {

    private String getAll = "SELECT *\n"
            + "  FROM [project_SWP391].[dbo].[Job_Seekers]";

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

    public boolean updateProfilePicture(int jobSeekerId, String profilePicturePath) {
        String sql = "UPDATE Job_Seekers SET profile_picture = ?, updated_at = GETDATE() WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, profilePicturePath);
            ps.setInt(2, jobSeekerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Vector<JobSeeker> searchJobSeekers(JobSeeker criteria) {
        Vector<JobSeeker> result = new Vector<>();
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
            sql.append(" AND experience_years >= ?");
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

        try {
            PreparedStatement ptm = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ptm.setObject(i + 1, params.get(i));
            }
            ResultSet res = ptm.executeQuery();
            while (res.next()) {
                JobSeeker p = mapResultSetToJobSeeker(res);
                result.add(p);
            }
        } catch (SQLException ex) {
            //System.out.println(ex);
            ex.getStackTrace();
        }

        return result;
    }

    public JobSeeker mapResultSetToJobSeeker(ResultSet res) throws SQLException {
        return new JobSeeker(
                res.getInt(1),
                res.getString(2),
                res.getString(3),
                res.getString(4),
                res.getString(5),
                res.getString(6),
                res.getDate(7),
                res.getString(8),
                res.getString(9),
                res.getString(10),
                res.getString(11),
                res.getString(12),
                res.getInt(13),
                res.getString(14),
                res.getString(15),
                res.getDouble(16),
                res.getString(17),
                res.getString(18),
                res.getString(19),
                res.getString(20),
                res.getString(21),
                res.getString(22),
                res.getString(23),
                res.getDate(24),
                res.getDate(25),
                res.getBoolean(26)
        );
    }

    public Vector<JobSeeker> getAllJobSeeker() {
        String sql = getAll;
        Vector<JobSeeker> listJobSeekers = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet res = ptm.executeQuery();
            while (res.next()) {
                JobSeeker p = new JobSeeker(res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getDate(7),
                        res.getString(8),
                        res.getString(9),
                        res.getString(10),
                        res.getString(11),
                        res.getString(12),
                        res.getInt(13),
                        res.getString(14),
                        res.getString(15),
                        res.getDouble(16),
                        res.getString(17),
                        res.getString(18),
                        res.getString(19),
                        res.getString(20),
                        res.getString(21),
                        res.getString(22),
                        res.getString(23),
                        res.getDate(24),
                        res.getDate(25),
                        res.getBoolean(26));

                listJobSeekers.add(p);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return listJobSeekers;
    }

    public void changeStatus(int ID, boolean status) {
        String sql = "UPDATE [dbo].[Job_Seekers]\n"
                + "   SET [is_active] = ?\n"
                + " WHERE id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setBoolean(1, status == true ? false : true);
            ptm.setInt(2, ID);
            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    public JobSeeker getSpeccificJobSeeker(int id) {
        String sql = "SELECT *\n"
                + "  FROM [project_SWP391].[dbo].[Job_Seekers]"
                + " WHERE id = ?";

        JobSeeker p = new JobSeeker();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, id);
            ResultSet res = ptm.executeQuery();
            while (res.next()) {

                p = mapResultSetToJobSeeker(res);

            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return p;
    }

}
