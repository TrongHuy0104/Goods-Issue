package goods_issue.controller;

import goods_issue.dataAccess.StorageDAO;
import goods_issue.model.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "StorageUpdateControl", urlPatterns = {"/StorageUpdateControl"})
public class StorageUpdateControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        List<Storage> listS = StorageDAO.getInstance().selectAll();

        for (Storage s : listS) {
            if (s.getId().equalsIgnoreCase(id)) {
                request.setAttribute("s", s);
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/storage-update.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String storageName = request.getParameter("storageName");

        String size = request.getParameter("size");
        int sizedata = Integer.parseInt(size);

        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String status = request.getParameter("status");

        try {
            int update = StorageDAO.getInstance().update(id, storageName, sizedata, address, type, status);
            if (update == 1) {
                response.sendRedirect("StorageIndexController");
            } else {
                response.sendRedirect("StorageUpdateControl");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
