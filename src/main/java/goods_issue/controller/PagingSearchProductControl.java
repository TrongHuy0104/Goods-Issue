/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.ProductDAO;
import goods_issue.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Trong Huy
 */
public class PagingSearchProductControl extends HttpServlet {

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
        ProductDAO productDao = new ProductDAO();
        String id = request.getParameter("d-id");
        Product product = new Product();
        if (id != null && !id.equals("")) {
            product.setpId(id);
            productDao.deleteProductDetail(product);
            productDao.deleteProductCategory(product);
            productDao.deleteProduct(product);
        }
        String dataSearch = request.getParameter("dataSearch");
        String searchValue = (String) request.getParameter("data-search");
        if (searchValue != null) {
            dataSearch = searchValue;
        }
        List<Product> productListSearch = productDao.searchAllByName(dataSearch);

        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }

        int index = Integer.parseInt(indexPage);
        int pageLimit = 10;
        int pCount = !productListSearch.isEmpty() ? productListSearch.size() : productDao.countTotal();

        int endPage = pCount / pageLimit;
        if (endPage == 0 || pCount % pageLimit != 0) {
            endPage++;
        }

        int itemStart = (index - 1) * pageLimit + 1;
        int itemEnd;
        if (index == endPage) {
            itemEnd = pCount;
        } else {
            itemEnd = index * pageLimit;
        }
        List<Product> productList = new ArrayList<>();
        if (dataSearch != null) {
            productList = productDao.searchByName(dataSearch, index, pageLimit);

            request.setAttribute("data-search", dataSearch);
        } else {
            productList = productDao.paging(index, pageLimit);
        }

        request.setAttribute("productList", productList);
        request.setAttribute("pCount", pCount);
        request.setAttribute("itemStart", itemStart);
        request.setAttribute("itemEnd", itemEnd);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        request.getRequestDispatcher("product.jsp").forward(request, response);
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
