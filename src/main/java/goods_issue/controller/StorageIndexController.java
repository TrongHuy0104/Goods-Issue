package goods_issue.controller;

import goods_issue.dataAccess.DAO;
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

@WebServlet(name = "StorageIndexController", urlPatterns = {"/StorageIndexController"})
public class StorageIndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Storage> listStorage = StorageDAO.getInstance().selectAll();
        request.setAttribute("listS", listStorage);
        
        RequestDispatcher rd = request.getRequestDispatcher("/storage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
