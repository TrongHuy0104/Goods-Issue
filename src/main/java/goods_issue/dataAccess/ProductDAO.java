/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trong Huy
 */
public class ProductDAO implements DAO<Product> {

    public List<Product> pagingProduct(int index) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.p_id, p.name, p.rating, p.thumb,pd.p_id, pd.price, "
                + "pd.description, pd.place_product,pd.number_of_product, pd.number_left "
                + "FROM products p, product_detail pd WHERE p.p_id = pd.p_id "
                + "ORDER BY `p`.`p_id` ASC LIMIT 10 OFFSET ?";
        Connection conn = CreateConnection();
        try {
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, (index - 1) * 10);
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
                list.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, origin));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> paging(int index, int limit) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                + "pd.p_id, pd.price, pd.description, pd.place_product,\n"
                + "pd.number_of_product, pd.number_left, pd.status,p.s_id\n"
                + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                + "WHERE p.p_id = pd.p_id AND pc.p_id = p.p_id AND c.c_id = pc.c_id \n"
                + "ORDER BY `p`.`p_id` LIMIT ? OFFSET ?";
        Connection conn = CreateConnection();
        try {
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, limit);
            ptmt.setInt(2, (index - 1) * limit);
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
                String store = rs.getString("s_id");
                list.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, category, status, store));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTotalByName(String data) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sql = "select count(*) from products WHERE name LIKE ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
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

    public int countTotal() {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sql = "select count(*)"
                    + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                    + "WHERE p.p_id = pd.p_id AND pc.p_id = p.p_id AND c.c_id = pc.c_id";
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

    @Override
    public ArrayList<Product> selectAll() {
        ArrayList<Product> result = new ArrayList<>();

        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;

            String sql = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                    + "pd.p_id, pd.price, pd.description, pd.place_product,\n"
                    + "pd.number_of_product, pd.number_left, pd.status,p.s_id\n"
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
                String store = rs.getString("s_id");
                result.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, category, status, store));
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

    public List<Product> searchByName(String data, int index) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.p_id, p.name, p.rating, p.thumb,\n"
                + " pd.p_id, pd.price, pd.description, pd.place_product, \n"
                + " pd.number_of_product, pd.number_left, pd.status\n"
                + "FROM  products p, product_detail pd \n"
                + "WHERE p.p_id = pd.p_id AND p.name LIKE ? \n"
                + "ORDER BY p.p_id\n"
                + "LIMIT 16 \n"
                + "OFFSET ?;";
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
            ptmt.setInt(2, (index - 1) * 16);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double rating = rs.getInt("rating");
                String thumb = rs.getString("thumb");
                double price = rs.getDouble("price");
                String desc = rs.getString("description");
                int numberLeft = rs.getInt("number_left");
                int totalNumber = rs.getInt("number_of_product");
                String origin = rs.getString("place_product");
                int status = rs.getInt("status");
                list.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, origin, status));
            }

            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> searchAllByName(String data) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                + "pd.p_id, pd.price, pd.description, pd.place_product,p.s_id, \n"
                + "pd.number_of_product, pd.number_left, pd.status\n"
                + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                + "WHERE p.p_id = pd.p_id AND pc.p_id = p.p_id AND c.c_id = pc.c_id AND p.name LIKE ? \n"
                + "ORDER BY p.p_id\n";
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
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
                String store = rs.getString("s_id");
                list.add(new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, category, status, store));

            }

            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product selectById(Product t) {
        Product p = null;

        String sql
                = "SELECT p.p_id, p.name, p.rating, p.thumb, c.name_category, \n"
                + "pd.p_id, pd.price, pd.description, pd.place_product,\n"
                + "pd.number_of_product, pd.number_left, pd.status, p.s_id, pc.c_id\n"
                + "FROM  products p, product_detail pd, product_category pc, categories c \n"
                + "WHERE p.p_id = pd.p_id AND pc.p_id = p.p_id AND c.c_id = pc.c_id AND p.p_id = ?";
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
                String store = rs.getString("s_id");
                int cId = rs.getInt("c_id");
                p = new Product(id, name, rating, thumb, price, desc, numberLeft, totalNumber, origin, category, status, store, cId);
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
            String sql = "INSERT INTO products (p_id, name, code, thumb,s_id) "
                    + " VALUES (?,?,?,?,?)";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpId());
            ptmt.setString(2, t.getpName());
            ptmt.setString(3, t.getpCode());
            ptmt.setString(4, t.getpThumb());
            ptmt.setString(5, t.getsId());
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

    public void insertProductCate(Product t) {

        try {
            String sql = "INSERT INTO product_category (p_id, c_id) "
                    + " VALUES (?,?)";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpId());
            ptmt.setInt(2, t.getpCateId());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAll(ArrayList<Product> arr) {
//        for (Product product : arr) {
//            this.insert(product);
//        } //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Product t) {
        try {
            Connection conn = CreateConnection();

//            PreparedStatement ptmt;
            String sql = "DELETE from products WHERE p_id=?";

//            Connection conn = CreateConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, t.getpId());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
            System.out.println(t.getpId());
        } catch (SQLException e) {
            e.printStackTrace();
        }//To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param arr
     */
    @Override
    public void deleteAll(ArrayList<Product> arr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Product t) {
        try {
            String sql = "UPDATE products SET name = ?, code = ?, s_id = ?, thumb = ? "
                    + "WHERE p_id = ?;";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpName());
            ptmt.setString(2, t.getpCode());
            ptmt.setString(3, t.getsId());
            ptmt.setString(4, t.getpThumb());
            ptmt.setString(5, t.getpId());
            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }//To change body of generated methods, choose Tools | Templates.
    }

    public void updateNonImg(Product t) {
        try {
            String sql = "UPDATE products SET name = ?, code = ?, s_id = ? "
                    + "WHERE p_id = ?;";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(1, t.getpName());
            ptmt.setString(2, t.getpCode());
            ptmt.setString(3, t.getsId());
            ptmt.setString(4, t.getpId());
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
                    + "WHERE p_id = ?;";
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

    public void updateProductCate(Product t) {

        try {
            String sql = "UPDATE product_category SET  c_id = ? "
                    + "WHERE p_id = ?;";
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setString(2, t.getpId());
            ptmt.setInt(1, t.getpCateId());

            ptmt.executeUpdate();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product t) {
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
        }
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

    public void deleteProductCategory(Product t
    ) {
        try {

            String sql = "DELETE from product_category WHERE p_id=?";
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

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.countTotal());

    }

}
