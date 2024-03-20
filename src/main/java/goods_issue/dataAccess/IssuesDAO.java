/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.dataAccess;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Issues;
import goods_issue.model.Model;
import goods_issue.model.Product;
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
public class IssuesDAO {

    public List<Issues> pagingIssues(String data, int index, int limit) {
        List<Issues> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    u.u_id,\n"
                + "    u.full_name,\n"
                + "    i.i_id,\n"
                + "    i.i_date,\n"
                + "    i.status,\n"
                + "    i.description,\n"
                + "    i.e_id,  \n"
                + "    GROUP_CONCAT(\n"
                + "        CONCAT_WS('|', id.id_id, id.quantity, id.p_id, id.s_id)\n"
                + "        SEPARATOR '; '\n"
                + "    ) AS IssueDetails\n"
                + "FROM users u\n"
                + "JOIN issues i ON u.u_id = i.u_id\n"
                + "JOIN `issue-detail` id ON i.i_id = id.i_id\n"
                + "WHERE u.full_name LIKE ?\n"
                + "GROUP BY i.i_id\n"
                + "LIMIT ? OFFSET ?;";
        Connection conn = CreateConnection();
        try {
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
            ptmt.setInt(2, limit);
            ptmt.setInt(3, (index - 1) * limit);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Issues issue = new Issues();
                issue.setiId(rs.getString("i_id"));
                issue.setuId(rs.getString("u_id"));
                issue.setDate(rs.getString("i_date"));
                issue.setStatus(rs.getInt("status"));
                issue.setDescription(rs.getString("description"));
                issue.seteId(rs.getString("e_id"));

                list.add(issue);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Issues> pagingIssues(int index, int limit) {
        List<Issues> list = new ArrayList<>();
        String sql = "SELECT *"
                + "FROM issues u\n"
                + "LIMIT ? OFFSET ?;";
        Connection conn = CreateConnection();
        try {
            PreparedStatement ptmt = null;
            ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1, limit);
            ptmt.setInt(2, (index - 1) * limit);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Issues issue = new Issues();
                issue.setiId(rs.getString("i_id"));
                issue.setuId(rs.getString("u_id"));
                issue.setDate(rs.getString("i_date"));
                issue.setStatus(rs.getInt("status"));
                issue.setDescription(rs.getString("description"));
                issue.seteId(rs.getString("e_id"));

                list.add(issue);
            }
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTotal() {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sql = "SELECT COUNT(*) FROM issues";
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

    public List<Issues> searchAllById(String data) {
        List<Issues> list = new ArrayList<>();
        String sql = "SELECT i.i_id, i.u_id, i.i_date, i.status, i.description, i.e_id\n"
                + "FROM issues i\n"
                + "WHERE i.i_id LIKE ? \n"
                + "ORDER BY i.i_id\n";
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("i_id");
                String uId = rs.getString("u_id");
                String iDate = rs.getString("i_date");
                int status = rs.getInt("status");
                int qty = rs.getInt("qty");
                String description = rs.getString("description");
                String eId = rs.getString("e_id");
                list.add(new Issues(id, uId, iDate, status, qty, description, eId));

            }

            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Issues> searchByName(String data) {
        List<Issues> list = new ArrayList<>();
        Connection conn = CreateConnection();
        PreparedStatement ptmt = null;
        String sql = "SELECT \n"
                + "    u.u_id,\n"
                + "    u.full_name,\n"
                + "    i.i_id,\n"
                + "    i.i_date,\n"
                + "    i.status,\n"
                + "    i.description,\n"
                + "    i.e_id,  \n"
                + "    GROUP_CONCAT(\n"
                + "        CONCAT_WS('|', id.id_id, id.quantity, id.p_id, id.s_id)\n"
                + "        SEPARATOR '; '\n"
                + "    ) AS IssueDetails\n"
                + "FROM users u\n"
                + "JOIN issues i ON u.u_id = i.u_id\n"
                + "JOIN `issue-detail` id ON i.i_id = id.i_id\n"
                + "WHERE u.full_name LIKE ?\n"
                + "GROUP BY i.i_id;";

        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, "%" + data + "%");
            ResultSet rs = ptmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

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

    public Issues selectIssuesById(String id) {
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

//     Dashboard
    public ArrayList<Model> countExportedByMonth() {
        ArrayList<Model> result = new ArrayList<>();
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sqlMonth = "";
            sqlMonth = "SELECT YEAR(i.i_date) AS year, MONTH(i.i_date) AS month, SUM(id.quantity) AS total_products\n"
                    + "FROM `issue-detail` id\n"
                    + "JOIN issues i ON id.i_id = i.i_id\n"
                    + "GROUP BY YEAR(i.i_date), MONTH(i.i_date)\n"
                    + "ORDER BY year, month;";
            ptmt = conn.prepareStatement(sqlMonth);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
//                return rs.getInt(1);
                String totalyear = rs.getString("year");
                String totalmonth = rs.getString("month");
                String total_product = rs.getString("total_products");

                result.add(new Model(totalyear, totalmonth, total_product));
            }
            rs.close();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Model> countExportedByDays() {
        ArrayList<Model> result = new ArrayList<>();
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sqlDays = "";
            sqlDays = "SELECT DAYOFWEEK(DATE(i.i_date)) AS day_of_week, SUM(id.quantity) AS total_products\n"
                    + "FROM `db`.`issue-detail` id\n"
                    + "JOIN `db`.`issues` i ON id.i_id = i.i_id\n"
                    + "WHERE DATE(i.i_date) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)\n"
                    + "GROUP BY DAYOFWEEK(DATE(i.i_date))\n"
                    + "ORDER BY DAYOFWEEK(DATE(i.i_date));";
            ptmt = conn.prepareStatement(sqlDays);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                String dayOfWeek = rs.getString("day_of_week");
                String total_product = rs.getString("total_products");

                result.add(new Model(dayOfWeek, total_product));
            }
            rs.close();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
    }

}
