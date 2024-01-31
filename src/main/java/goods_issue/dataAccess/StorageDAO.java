package goods_issue.dataAccess;

import goods_issue.context.DBContext;
import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Storage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StorageDAO implements DAO<Storage> {

    private static final StorageDAO instance = new StorageDAO();

    public static StorageDAO getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Storage> selectAll() {
        ArrayList<Storage> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM storage";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("s_id");
                String name = rs.getString("s_name");
                int size = rs.getInt("s_size");
                String address = rs.getString("s_address");
                String type = rs.getString("type");
                String status = rs.getString("status");

                result.add(new Storage(id, name, size, address, type, status));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Storage selectById(Storage t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Storage t) {

        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("INSERT INTO storage (s_id, s_name, s_size, s_address, type, status) VALUES ( ?, ?, ?, ?, ?, ?)");
            smt.setString(1, t.getId());
            smt.setString(2, t.getName());
            smt.setInt(3, t.getSize());
            smt.setString(4, t.getAddress());
            smt.setString(5, t.getType());
            smt.setString(6, t.getStatus());
            smt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkStorageIsDuplicated(String id) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            PreparedStatement smt = conn.prepareStatement("SELECT * FROM storage WHERE s_id = ?");

            smt.setString(1, id);
            ResultSet rs = smt.executeQuery();

            while (rs.next()) {
                result = true;
            }
            smt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void insertAll(ArrayList<Storage> arr) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(String id) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement(""
                    + "DELETE from storage WHERE s_id = ?;");
            smt.setString(1, id);
            smt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(ArrayList<Storage> arr) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int update(String id, String name, int size, String address, String type, String status) {
        int result = 0;

        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("UPDATE storage SET s_id = ?, s_name = ?, s_size = ?, s_address = ?, TYPE = ?, STATUS = ? WHERE s_id = ?");
            smt.setString(1, id);
            smt.setString(2, name);
            smt.setInt(3, size);
            smt.setString(4, address);
            smt.setString(5, type);
            smt.setString(6, status);
            smt.setString(7, id);

            int rowsAffected = smt.executeUpdate();

            if (rowsAffected > 0) {
                result = 1;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void delete(Storage t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addStorage(Storage storage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Storage t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
