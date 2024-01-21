/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.DAO;
import goods_issue.dataAccess.ProductDAO;
import goods_issue.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
public class ProductAddControl extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProductControl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            String productName = request.getParameter("productName");
            String from = request.getParameter("from");
            String code = null;
            Double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int cId = Integer.parseInt(request.getParameter("category"));
            String desc = request.getParameter("desc");
            String category = null;
            if (cId >= 4 && cId <= 11) {
                category = "Mobile";
                code = "1";
            } else if ((cId >= 12 && cId <= 19) || cId == 27 || cId == 28) {
                category = "Laptop";
                code = "2";
            } else if (cId >= 20 && cId <= 26) {
                category = "Tablet";
                code = "3";
            }
            int status = 0;
            if (quantity == 0) {
                status = 0;
            } else if (quantity > 10) {
                status = 2;
            }  else {
                status = 1;
            }
            System.out.println(price);

            request.setAttribute("productName", productName);
            request.setAttribute("from", from);
            request.setAttribute("price", price);
            request.setAttribute("quantity", quantity);
            request.setAttribute("category", category);
            request.setAttribute("desc", desc);

            String url = "";
            String error = "";

            ProductDAO dao = new ProductDAO();

            if (dao.checkProductIsDuplicated(productName)) {
                error += "Product already exists, please choose another product name!";
            }

            request.setAttribute("error", error);

            if (error.length() > 0) {
                url = "/product-add.jsp";
            } else {
                Random rd = new Random();
                long id = Instant.now().getEpochSecond();
                String idString = Long.toString(id);
                
                Product p = new Product();
                p.setpId(idString);
                p.setpName(productName);
                p.setpCategory(category);
                p.setpOrigin(from);
                p.setpPrice(price);
                p.setpNumberLeft(quantity);
                p.setpDescription(desc);
                p.setpCode(code);
                p.setpCateId(cId);
                p.setpStatus(status);

                dao.insert(p);
//                dao.insertProductCate(p);
                dao.insertProductDetail(p);
                request.setAttribute("note", "Product created successfully");
                url = "/product-add.jsp";
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
