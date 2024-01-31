package goods_issue.controller;

import static goods_issue.context.DBContext.CreateConnection;
import goods_issue.model.Storage;
import goods_issue.dataAccess.StorageDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/storage-add")
public class StorageAddControl extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/storage-add.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        boolean checkid = StorageDAO.getInstance().checkStorageIsDuplicated(id);
        
        String storageName = request.getParameter("storageName");

        String size = request.getParameter("size");
        int sizedata = Integer.parseInt(size);

        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
              
        
        
        try {
            Storage t = new Storage(id, storageName, sizedata, address, type, status);
            if (t == null || checkid ) {  
                String err = "Storage ID already exists, please choose another ID!";
                request.setAttribute("err", err);
                request.getRequestDispatcher("/storage-add.jsp").forward(request, response);
            } else {
                StorageDAO.getInstance().insert(t);
                response.sendRedirect("StorageIndexController");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkStorageIsDuplicated(String id) {
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
}
