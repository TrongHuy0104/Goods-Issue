/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Trong Huy
 */
public class DBContext {
     public static Connection CreateConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:8888/exported"; 
//        ae doi sang localhost 3306 de chay nha
        String name = "root";
        String pass = "";

        //load driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //create connection
        try {
            conn = DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
     
}
