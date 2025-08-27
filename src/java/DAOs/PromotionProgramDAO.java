    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DBContext.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Models.PromotionProgram;

/**
 *
 * @author DELL
 */
public class PromotionProgramDAO extends DBContext {

    public List<PromotionProgram> getAllPromotionPrograms() {
        List<PromotionProgram> programs = new ArrayList<>();
        String sql = "SELECT * FROM Promotion_Programs WHERE is_active = 1 ORDER BY cost ASC";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PromotionProgram program = new PromotionProgram();
                program.setId(rs.getInt("id"));
                program.setName(rs.getString("name"));
                program.setCost(rs.getDouble("cost"));
                program.setDurationDays(rs.getInt("duration_days"));
                program.setDescription(rs.getString("description"));
                program.setActive(rs.getBoolean("is_active"));
                program.setCreatedAt(rs.getTimestamp("created_at"));
                program.setUpdatedAt(rs.getTimestamp("updated_at"));
                program.setAdminId(rs.getInt("admin_id"));
                program.setPositionType(rs.getString("position_type"));
                program.setQuantity(rs.getInt("quantity"));

                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return programs;
    }

    public PromotionProgram getPromotionProgramById(int id) {
        String sql = "SELECT * FROM Promotion_Programs WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PromotionProgram program = new PromotionProgram();
                program.setId(rs.getInt("id"));
                program.setName(rs.getString("name"));
                program.setCost(rs.getDouble("cost"));
                program.setDurationDays(rs.getInt("duration_days"));
                program.setDescription(rs.getString("description"));
                program.setActive(rs.getBoolean("is_active"));
                program.setCreatedAt(rs.getTimestamp("created_at"));
                program.setUpdatedAt(rs.getTimestamp("updated_at"));
                program.setAdminId(rs.getInt("admin_id"));
                program.setPositionType(rs.getString("position_type"));
                program.setQuantity(rs.getInt("quantity"));

                return program;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updatePromotionProgram(PromotionProgram program) {
        String sql = "UPDATE Promotion_Programs SET cost = ?, duration_days = ?, "
                + "updated_at = GETDATE(), quantity = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, program.getCost());
            stmt.setInt(2, program.getDurationDays());
            stmt.setInt(3, program.getQuantity());
            stmt.setInt(4, program.getId());

            PostPricingDAO dao = new PostPricingDAO();
            boolean flag = dao.updatePostPricings(program.getPositionType(), program.getCost(), program.getDurationDays());

            return flag && stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addPromotionProgram(PromotionProgram program) {
        String sql = "INSERT INTO Promotion_Programs (name, cost, duration_days, description, "
                + "is_active, admin_id, position_type, quantity) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, program.getName());
            stmt.setDouble(2, program.getCost());
            stmt.setInt(3, program.getDurationDays());
            stmt.setString(4, program.getDescription());
            stmt.setBoolean(5, program.isActive());
            stmt.setInt(6, program.getAdminId());
            stmt.setString(7, program.getPositionType());
            stmt.setInt(8, program.getQuantity());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public PromotionProgram getPromotionProgramByType(String positionType) {
        String sql = "SELECT * FROM Promotion_Programs WHERE position_type = ? AND is_active = 1";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, positionType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PromotionProgram program = new PromotionProgram();
                program.setId(rs.getInt("id"));
                program.setName(rs.getString("name"));
                program.setCost(rs.getDouble("cost"));
                program.setDurationDays(rs.getInt("duration_days"));
                program.setDescription(rs.getString("description"));
                program.setActive(rs.getBoolean("is_active"));
                program.setCreatedAt(rs.getTimestamp("created_at"));
                program.setUpdatedAt(rs.getTimestamp("updated_at"));
                program.setAdminId(rs.getInt("admin_id"));
                program.setPositionType(rs.getString("position_type"));
                program.setQuantity(rs.getInt("quantity"));

                return program;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int findProgramIDBy(String type) {
        String sql = "SELECT [id] FROM [projectg6_SWP391].[dbo].[Promotion_Programs] WHERE [position_type] = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countActiveRecruiters() {
        String sql = "SELECT COUNT(*) FROM Recruiter WHERE is_active = 1";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
