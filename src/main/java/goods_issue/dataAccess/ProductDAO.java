/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;
import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Product;
/**
 *
 * @author ACER
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;/**
 *
 * @author ACER
 */
public class ProductDAO implements DAO<Product>{

    @Override
    public ArrayList<Product> selectAll() {
       ArrayList<Product> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                    + "pd.p_id, pd.price, pd.description, pd.place_product,\n"
                    + "pd.number_of_product, pd.number_left, pd.status\n"
                    + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                    + "WHERE p.p_id = pd.p_id AND pc.p_id = p.p_id AND c.c_id = pc.c_id \n"
                    + "ORDER BY p.p_id\n";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("p_id");
                String name = rs.getString("name");
                double rating = rs.getInt("rating");
                String thumb = rs.getString("thumb");
                double price = rs.getDouble("price");
                String desc = rs.getString("description");
                int numberLeft = rs.getInt("number_left");
                int totalNumber = rs.getInt("number_of_product");
                String origin = rs.getString("place_product");
                String category = rs.getString("name_category");
                int status = rs.getInt("status");
                result.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, origin, category, status));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result; //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean checkProductIsDuplicated(String name) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            String sql = "SELECT * FROM products WHERE name = ?";
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, name);
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
    
    public boolean checkProductIsDuplicated2(String name, String id) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();

            String sql = "SELECT * FROM products WHERE name = ? AND p_id != ?";
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, name);
            ptmt.setString(1, id);
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

    @Override
    public Product selectById(Product t) {
        Product p = null;

        String sql
                = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                + "pd.p_id, pd.price, pd.description, pd.place_product,\n"
                + "pd.number_of_product, pd.number_left, pd.status\n"
                + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                + "WHERE p.p_id = pd.p_id AND pc.p_id = p.id AND c.c_id = pc.c_id AND p.p_id = ?";
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getpId());
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("p_id");
                String name = rs.getString("name");
                double rating = rs.getInt("rating");
                String thumb = rs.getString("thumb");
                double price = rs.getDouble("price");
                String desc = rs.getString("description");
                int numberLeft = rs.getInt("number_left");
                int totalNumber = rs.getInt("number_of_product");
                String origin = rs.getString("place_product");
                String category = rs.getString("name_category");
                int status = rs.getInt("status");
                p = new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, origin, category, status);
            }

            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Product t) {
        try {
            String sql = "INSERT INTO products (p_id, name, code) "
                    + " VALUES (?,?,?)";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpId());
            ptmt.setString(2, t.getpName());
            ptmt.setString(3, t.getpCode());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } //To change body of generated methods, choose Tools | Templates.
    }

    public void insertProductDetail(Product t) {

        try {
            String sql = "INSERT INTO product_detail (p_id, description, number_left, place_product, price, status) "
                    + " VALUES (?,?,?,?,?,?)";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpId());
            ptmt.setString(2, t.getpDescription());
            ptmt.setInt(3, t.getpNumberLeft());
            ptmt.setString(4, t.getpOrigin());
            ptmt.setDouble(5, t.getpPrice());
            ptmt.setInt(6, t.getpStatus());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void insertAll(ArrayList<Product> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Product t) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt;
            String sql = "DELETE from products WHERE p_id=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getpId());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }//To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteProductDetail(Product t
    ) {
        try {

            String sql = "DELETE from product_detail WHERE p_id=?";
            Connection conn = CreateConnection();
            PreparedStatement ptmt;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getpId());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(ArrayList<Product> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Product t) {
        try {
            String sql = "UPDATE products SET name = ?, code = ? "
                    + "WHERE id = ?;";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(3, t.getpId());
            ptmt.setString(1, t.getpName());
            ptmt.setString(2, t.getpCode());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }//To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateProductDetail(Product t) {

        try {
            String sql = "UPDATE product_detail SET description = ?, number_left = ?, place_product = ?, price = ?, status = ? "
                    + "WHERE product_id = ?;";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(6, t.getpId());
            ptmt.setString(1, t.getpDescription());
            ptmt.setInt(2, t.getpNumberLeft());
            ptmt.setString(3, t.getpOrigin());
            ptmt.setDouble(4, t.getpPrice());
            ptmt.setInt(5, t.getpStatus());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}