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
            Model totalMonth = new Model();
            sqlMonth = "SELECT YEAR(i.i_date) AS year, MONTH(i.i_date) AS month, SUM(id.quantity) AS total_products\n"
                    + "FROM db.`issue-detail` id\n"
                    + "JOIN issues i ON id.i_id = i.i_id\n"
                    + "GROUP BY YEAR(i.i_date), MONTH(i.i_date)\n"
                    + "ORDER BY year, month;";
//            if (month.equals("january")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db.issues WHERE DATEPART(month, i_date) = 1;";
//            }
//            if (month.equals("february")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 2;";
//            }
//            if (month.equals("march")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 3;";
//            }
//            if (month.equals("april")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 4;";
//            }
//            if (month.equals("may")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 5;";
//            }
//            if (month.equals("june")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 6;";
//            }
//            if (month.equals("july")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 7;";
//            }
//            if (month.equals("august")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 8;";
//            }
//            if (month.equals("september")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 9;";
//            }
//            if (month.equals("october")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 10;";
//            }
//            if (month.equals("november")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 11;";
//            }
//            if (month.equals("december")) {
//
//                sqlMonth = "SELECT COUNT(*) FROM db WHERE DATEPART(month, ) = 12;";
//            }
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

    public int countExportedByDays(String days) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sqlDays = "";
            if (days.equals("monday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 2;";
            }
            if (days.equals("tuesday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 3;";
            }
            if (days.equals("wednesday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 4;";
            }
            if (days.equals("thursday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 5;";
            }
            if (days.equals("friday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 6;";
            }
            if (days.equals("saturday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 7;";
            }
            if (days.equals("sunday")) {

                sqlDays = "SELECT COUNT(*) FROM db WHERE DAYOFWEEK(product_date) = 1;";
            }
            ptmt = conn.prepareStatement(sqlDays);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.close();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countExportedByYear(String year) {
        try {
            Connection conn = CreateConnection();
            PreparedStatement ptmt = null;
            String sqlYear = "";
            if (year.equals("year2020")) {

                sqlYear = "SELECT COUNT(*) FROM db WHERE YEAR(product_date) = 2023;";
            }
            if (year.equals("year2021")) {

                sqlYear = "SELECT COUNT(*) FROM db WHERE YEAR(product_date) = 2023;";
            }
            if (year.equals("year2022")) {

                sqlYear = "SELECT COUNT(*) FROM db WHERE YEAR(product_date) = 2023;";
            }
            if (year.equals("year2023")) {

                sqlYear = "SELECT COUNT(*) FROM db WHERE YEAR(product_date) = 2023;";
            }
            if (year.equals("year2024")) {

                sqlYear = "SELECT COUNT(*) FROM db WHERE YEAR(product_date) = 2023;";
            }
            ptmt = conn.prepareStatement(sqlYear);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.close();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        IssuesDAO dao = new IssuesDAO();
        System.out.println(dao.getAllIssuesDetail("I_17102339613sdsds55755").isEmpty());

        ArrayList<Model> modelList = dao.countExportedByMonth();
        for (Model m : modelList) {
            System.out.println(m.toString());
        }
        int jan = 0;
        for (Model m : modelList) {
            if (m.getMonth().equals("1")) {
                jan = Integer.parseInt(m.getTotal_products());
                
            }
        }
        System.out.println(jan);
    }
}
