/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package goods_issue.controller;

import goods_issue.dataAccess.UserDAO;
import goods_issue.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anhph
 */
public class PagingSearchCustomerControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("d-id");
        UserDAO userDao = new UserDAO();
        if (id != null && !id.equals("")) {
            User user = new User();
            user.setId(id);
            userDao.delete(user);
        }
        String dataSearch = request.getParameter("dataSearch");
        String searchValue = (String) request.getParameter("data-search");
        if (searchValue != null) {
            dataSearch = searchValue;
        }
        List<User> customerListSearch = userDao.searchByName(dataSearch);

        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }

        int index = Integer.parseInt(indexPage);
        int pageLimit = 10;
        int pCount = !customerListSearch.isEmpty() ? customerListSearch.size() : userDao.countTotal();

        int endPage = pCount / pageLimit;
        System.out.println("endPage " + endPage);
        System.out.println("pageLimit " + pageLimit);
        System.out.println("endPage % pageLimit " + (endPage % pageLimit));
        if ((endPage == 0 || pCount % pageLimit != 0)) {
            endPage++;
        }

        int itemStart = (index - 1) * pageLimit + 1;
        int itemEnd;
        if (index == endPage) {
            itemEnd = pCount;
        } else {
            itemEnd = index * pageLimit;
        }
        List<User> userList = new ArrayList<>();
        if (dataSearch != null) {
            userList = userDao.searchByName(dataSearch, index, pageLimit);

            request.setAttribute("data-search", dataSearch);
        } else {
            userList = userDao.paging(index, pageLimit);
        }

        request.setAttribute("userList", userList);
        request.setAttribute("pCount", pCount);
        request.setAttribute("itemStart", itemStart);
        request.setAttribute("itemEnd", itemEnd);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
