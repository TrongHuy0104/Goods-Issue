/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Issues;
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
public class IssuesDAO {

    public List<Issues> selectAllIssues() {
        List<Issues> list = new ArrayList<>();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {

            String sql = "select * from issues  order by i_id desc;";
            ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();;
            while (rs.next()) {
                Issues issues = new Issues();
                issues.setiId(rs.getString("i_id"));
                issues.setuId(rs.getString("u_id"));
                issues.setDate(rs.getString("i_date"));
                issues.seteId(rs.getString("e_id"));
                issues.setStatus(rs.getInt("status"));
                list.add(issues);
            }

            ptmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Issues selectIssuesById (String id) {
        Issues issues = new Issues();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {

            String sql = "select * from issues  WHERE i_id = ?;";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, id);
            ResultSet rs = ptmt.executeQuery();;
            while (rs.next()) {
                issues.setiId(rs.getString("i_id"));
                issues.setuId(rs.getString("u_id"));
                issues.setDate(rs.getString("i_date"));
                issues.seteId(rs.getString("e_id"));
                issues.setStatus(rs.getInt("status"));
            }

            ptmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }

    public List<Issues> getAllIssuesDetail(String iId, String uId) {
        List<Issues> list = new ArrayList<>();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {

            String sql = "SELECT * from issues, `issue-detail` WHERE issues.i_id = `issue-detail`.`i_id` AND issues.i_id = ? AND issues.u_id = ? order by issues.i_id desc;";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, iId);
            ptmt.setString(2, uId);
            ResultSet rs = ptmt.executeQuery();;
            while (rs.next()) {
                Issues issues = new Issues();
                ProductDAO pDao = new ProductDAO();
                String pId = rs.getString("p_id");
                Product product = pDao.selectById(new Product(pId));
                issues.setiId(rs.getString("i_id"));
                issues.setpId(pId);
                issues.setpName(product.getpName());
                issues.setpPrice(product.getpPrice() * rs.getInt("quantity"));
                issues.setQty(rs.getInt("quantity"));
                issues.setDate(rs.getString("i_date"));
                issues.setpPrice(product.getpPrice());
                issues.setpThumb(product.getpThumb());
                list.add(issues);
            }

            ptmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Issues> getAllIssuesDetail(String iId) {
        List<Issues> list = new ArrayList<>();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {

            String sql = "SELECT * from issues, `issue-detail` WHERE issues.i_id = `issue-detail`.`i_id` AND issues.i_id = ? order by issues.i_id desc;";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, iId);
            ResultSet rs = ptmt.executeQuery();;
            while (rs.next()) {
                Issues issues = new Issues();
                ProductDAO pDao = new ProductDAO();
                String pId = rs.getString("p_id");
                Product product = pDao.selectById(new Product(pId));
                issues.setiId(rs.getString("i_id"));
                issues.setpId(pId);
                issues.setpName(product.getpName());
                issues.setpPrice(product.getpPrice() * rs.getInt("quantity"));
                issues.setQty(rs.getInt("quantity"));
                issues.setDate(rs.getString("i_date"));
                issues.setpPrice(product.getpPrice());
                issues.setpThumb(product.getpThumb());
                issues.setpOrigin(product.getpOrigin());
                issues.setsId(rs.getString("s_id"));
                list.add(issues);
            }

            ptmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalIssuesPrice(List<Issues> issuesList) {
        double sum = 0;
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            if (issuesList.size() > 0) {
                for (Issues issues : issuesList) {
                    String sql = "SELECT pd.price \n"
                            + "FROM  products p, product_detail pd \n"
                            + "WHERE p.p_id = pd.p_id AND p.p_id = ? \n";
                    ptmt = conn.prepareStatement(sql);
                    ptmt.setString(1, issues.getpId());
                    ResultSet rs = ptmt.executeQuery();
                    while (rs.next()) {
                        sum += rs.getDouble("price") * issues.getQty();
                    }

                }
                ptmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public Issues getIssuesByIssuesID(String id) {
        Issues issues = new Issues();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {

            String sql = "SELECT * from issues WHERE issues.i_id = ? order by i_id desc;";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, id);
            ResultSet rs = ptmt.executeQuery();;
            while (rs.next()) {
                issues.setiId(rs.getString("i_id"));
                issues.setuId(rs.getString("u_id"));
                issues.seteId(rs.getString("e_id"));
                issues.setDate(rs.getString("i_date"));
                issues.setStatus(rs.getInt("status"));
            }

            ptmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }

    public boolean checkIssuesDetailExist(Issues t) {
        boolean result = false;
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("SELECT * FROM `issue-detail` WHERE i_id = ? AND p_id = ?");
            smt.setString(1, t.getiId());
            smt.setString(2, t.getpId());
            ResultSet rs = smt.executeQuery();;
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

    public void insertIssues(Issues t) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("INSERT INTO issues (e_id, i_date, i_id, u_id) VALUES ( ?, ?, ?, ?)");
            smt.setString(1, t.geteId());
            smt.setString(2, t.getDate());
            smt.setString(3, t.getiId());
            smt.setString(4, t.getuId());
            smt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIssuesDetail(Issues t) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("INSERT INTO `issue-detail` (id_id, i_id, quantity, p_id, s_id) VALUES ( ?, ?, ?, ?, ?)");
            smt.setString(1, t.getid_Id());
            smt.setString(2, t.getiId());
            smt.setInt(3, t.getQty());
            smt.setString(4, t.getpId());
            smt.setString(5, t.getsId());
            smt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIssuesDetail(Issues t) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement smt = conn.prepareStatement("UPDATE `issue-detail` SET quantity = quantity + ? WHERE i_id = ? AND p_id = ?");
            smt.setInt(1, t.getQty());
            smt.setString(2, t.getiId());
            smt.setString(3, t.getpId());
            smt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void updateStatus(String id, int status) {
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {
            String sql = "UPDATE issues\n"
                    + "SET status = ?\n"
                    + "WHERE i_id=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, status);
            ptmt.setString(2, id);
            ptmt.execute();
            
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }

    public static void main(String[] args) {
        IssuesDAO dao = new IssuesDAO();
        System.out.println(dao.getAllIssuesDetail("I_17102339613sdsds55755").isEmpty());
    }
}
