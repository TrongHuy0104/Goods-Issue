/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trong Huy
 */
public class UserDAO implements DAO<User> {

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM users";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("u_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setDeliveryAddress(rs.getString("ship_address"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("image"));
                result.add(user);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User selectById(User t) {
        User user = new User();
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM users WHERE u_id = ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getId());
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                user.setId(rs.getString("u_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setDeliveryAddress(rs.getString("ship_address"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("image"));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insert(User t) {
        try {
            String sql = "INSERT INTO users (u_id, username, password, address, full_name, gender, ship_address, phone, email, image) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getId());
            ptmt.setString(2, t.getUserName());
            ptmt.setString(3, t.getPassword());
            ptmt.setString(4, t.getAddress());
            ptmt.setString(5, t.getFullName());
            ptmt.setString(6, t.getGender());
            ptmt.setString(7, t.getDeliveryAddress());
            ptmt.setString(8, t.getPhone());
            ptmt.setString(9, t.getEmail());
            ptmt.setString(10, t.getAvatar());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAll(ArrayList<User> arr) {
        for (User user : arr) {
            this.insert(user);
        }
    }

    @Override
    public void delete(User t) {
        try {

            String sql = "DELETE from users WHERE u_id=?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getId());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(ArrayList<User> arr) {
        for (User u : arr) {
            this.delete(u);
        }
    }

    @Override
    public void update(User t) {
        try {

            String sql = "UPDATE users SET username = ?, password = ?, address = ?, full_name = ?, gender = ?, ship_address = ?, phone = ?, email = ?, image = ? "
                    + " WHERE u_id = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(10, t.getId());
            ptmt.setString(1, t.getUserName());
            ptmt.setString(2, t.getPassword());
            ptmt.setString(3, t.getAddress());
            ptmt.setString(4, t.getFullName());
            ptmt.setString(5, t.getGender());
            ptmt.setString(6, t.getDeliveryAddress());
            ptmt.setString(7, t.getPhone());
            ptmt.setString(8, t.getEmail());
            ptmt.setString(9, t.getAvatar());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User checkSignIn(User t) {
        User user = null;

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getUserName());
            ptmt.setString(2, t.getPassword());
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("u_id");
                String userName = rs.getString("username");
                String pass = rs.getString("password");
                String address = rs.getString("address");
                String name = rs.getString("full_name");
                String gender = rs.getString("gender");
                String deliveryAddress = rs.getString("ship_address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String avatar = rs.getString("image");
                int role = rs.getInt("role");

                user = new User(id, userName, pass, address, email, name, gender, phone, deliveryAddress, avatar, role);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean checkUsernameEmailIsDuplicated(String username, String email) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            String sql = "SELECT * FROM users WHERE username = ? AND email = ?";
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, username);
            ptmt.setString(2, email);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                result = true;
            }
            ptmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean resetPassword(User t) {
        boolean result = true;
        try {
            String sql = "UPDATE users SET users.password = ?  WHERE username = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getPassword());
            ptmt.setString(2, t.getUserName());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<User> selectAllCustomer() {
        ArrayList<User> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT * FROM users WHERE role = 0;";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("u_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setDeliveryAddress(rs.getString("ship_address"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("image"));

                result.add(user);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkUserIsDuplicated(String userName) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, userName);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                result = true;
            }
            ptmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkEmailIsDuplicated(String email) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                result = true;
            }
            ptmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateAvatar(User t
    ) {
        try {

            String sql = "UPDATE users SET image = ?" + " WHERE id = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(2, t.getId());
            ptmt.setString(1, t.getAvatar());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(User t
    ) {
        try {

            String sql = "UPDATE users SET address = ?, full_name = ?, gender = ?, ship_address = ?, phone = ?, email = ?, image = ? "
                    + " WHERE u_id = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(8, t.getId());
            ptmt.setString(1, t.getAddress());
            ptmt.setString(2, t.getFullName());
            ptmt.setString(3, t.getGender());
            ptmt.setString(4, t.getDeliveryAddress());
            ptmt.setString(5, t.getPhone());
            ptmt.setString(6, t.getEmail());
            ptmt.setString(7, t.getAvatar());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateUserInfoNonImg(User t
    ) {
        try {

            String sql = "UPDATE users SET address = ?, full_name = ?, gender = ?, ship_address = ?, phone = ?, email = ? "
                    + " WHERE u_id = ?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getAddress());
            ptmt.setString(2, t.getFullName());
            ptmt.setString(3, t.getGender());
            ptmt.setString(4, t.getDeliveryAddress());
            ptmt.setString(5, t.getPhone());
            ptmt.setString(6, t.getEmail());
            ptmt.setString(7, t.getId());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countTotal() {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sql = "select count(*) from users WHERE role = 0";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> paging(int index, int limit) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 0\n"
                + "ORDER BY u_id\n"
                + "LIMIT ?\n"
                + "OFFSET ?;";
        Connection conn = CreateConnection();
        try {
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, limit);
            ptmt.setInt(2, (index - 1) * limit);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("u_id"));
                user.setFullName(rs.getString("full_name"));
                user.setAddress(rs.getString("address"));
                user.setDeliveryAddress(rs.getString("ship_address"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("image"));

                list.add(user);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> searchByName(String data) {
        List<User> list = new ArrayList<>();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        String sql = "SELECT * from users\n"
                + "WHERE full_name LIKE ? AND role = 0; \n";

        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("u_id"));
                user.setFullName(rs.getString("full_name"));
                user.setAddress(rs.getString("address"));
                user.setDeliveryAddress(rs.getString("ship_address"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAvatar(rs.getString("image"));

                list.add(user);
            }

            ptmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void main(String[] args) {
        User us = new User();
        us.setId("1");
        UserDAO u = new UserDAO();
        System.out.println(u.searchByName(""));
    }
}
