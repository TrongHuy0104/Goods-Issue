/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Storage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trong Huy
 */
public class StorageDAO implements DAO<Storage> {

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

    public ArrayList<Storage> selectAllActiveStorage() {
        ArrayList<Storage> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT *\n"
                    + "FROM storage\n"
                    + "WHERE status <> 'closed' AND status <> 'full' ;";
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
        Storage storage = null;
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM storage WHERE s_id = ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getsID());
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("s_id");
                String name = rs.getString("s_name");
                int size = rs.getInt("s_size");
                String address = rs.getString("s_address");
                String type = rs.getString("type");
                String status = rs.getString("status");

                storage = new Storage(id, name, size, address, type, status);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storage;
    }

    @Override
    public void insert(Storage t) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("INSERT INTO storage (s_id, s_name, s_size, s_address, type) VALUES ( ?, ?, ?, ?, ?)");
            smt.setString(1, t.getsID());
            smt.setString(2, t.getsName());
            smt.setInt(3, t.getsSize());
            smt.setString(4, t.getsAddress());
            smt.setString(5, t.getsType());
            smt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAll(ArrayList<Storage> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Storage t) {
        try {
            String sql = "UPDATE storage SET status = 'closed' WHERE s_id=?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getsID());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StorageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAll(ArrayList<Storage> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Storage t) {
        try {

            String sql = "UPDATE storage SET s_name = ?, s_size = ?, s_address = ?, type = ?, status = ? "
                    + " WHERE s_id = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getsName());
            ptmt.setInt(2, t.getsSize());
            ptmt.setString(3, t.getsAddress());
            ptmt.setString(4, t.getsType());
            ptmt.setString(5, t.getStatus());
            ptmt.setString(6, t.getsID());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StorageDAO sdao = new StorageDAO();
        System.out.println(sdao.selectAll());
    }
}
