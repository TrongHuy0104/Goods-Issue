/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.IssuesDAO;
import goods_issue.dataAccess.ProductDAO;
import goods_issue.dataAccess.UserDAO;
import goods_issue.model.Issues;
import goods_issue.model.Product;
import goods_issue.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Trong Huy
 */
public class IssuesAddServlet extends HttpServlet {

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
            out.println("<title>Servlet IssuesAddServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IssuesAddServlet at " + request.getContextPath() + "</h1>");
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
        String customerName = request.getParameter("name");
        String customerAddress = request.getParameter("address");
        String customerPhone = request.getParameter("phone");
        String customerEmail = request.getParameter("email");
        String product = request.getParameter("text");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String employeeID = request.getParameter("employee");
        String issuesID = request.getParameter("i_id");

        UserDAO userDao = new UserDAO();
        IssuesDAO issuesDao = new IssuesDAO();
        ProductDAO productDao = new ProductDAO();
        String note = "";
        String error = "";

        if (productDao.checkProductExistByName(product)) {

            Product p = productDao.selectByName(product);

            if (p.getpNumberLeft() >= quantity) {

                String p_id = p.getpId();
                String s_id = p.getsId();

                String i_id;
                Random rd = new Random();
                if (issuesID == null || issuesID.equals("") || issuesID.equals("null")) {
                    i_id = "I_" + System.currentTimeMillis() + rd.nextInt(1000);
                } else {
                    i_id = issuesID;
                }

                String id_id = "ID_" + System.currentTimeMillis() + rd.nextInt(1000);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(); // Current date
                String formattedDate = sdf.format(date);

                boolean isCustomerExisted = userDao.checkCustomerDuplicated(customerName, customerPhone);
                String u_id;
                if (!isCustomerExisted) {
                    u_id = System.currentTimeMillis() + rd.nextInt(1000) + "";
                    User u = new User(u_id, customerEmail, customerName, customerPhone, customerAddress);
                    userDao.insertCustomer(u);
                } else {
                    u_id = userDao.selectCustomer(customerName, customerPhone).getId();
                }

                Issues tempIssues = new Issues();
                tempIssues.seteId(employeeID);
                tempIssues.setDate(formattedDate);
                tempIssues.setiId(i_id);
                tempIssues.setuId(u_id);
                tempIssues.setid_Id(id_id);
                tempIssues.setQty(quantity);
                tempIssues.setpId(p_id);
                tempIssues.setsId(s_id);

                if (issuesID == null || issuesID.equals("") || issuesID.equals("null")) {
                    issuesDao.insertIssues(tempIssues);
                    issuesDao.insertIssuesDetail(tempIssues);
                    request.setAttribute("isExist", "true");
                } else {
                        request.setAttribute("isExist", "true");
                    if (issuesDao.checkIssuesDetailExist(tempIssues)) {
                        issuesDao.updateIssuesDetail(tempIssues);
                    } else {
                        issuesDao.insertIssuesDetail(tempIssues);
                    }
                }
                productDao.updateNumberLeft(quantity, p_id);

                request.setAttribute("i_id", i_id);
                note = "Issues created successfully!";
            } else {
                error = "There are only " + p.getpNumberLeft() + " products left in stock";
                request.setAttribute("product", product);
            }
        } else {
            error = "Product name is not exist!";
        }

        request.setAttribute("note", note);
        request.setAttribute("error", error);
        request.setAttribute("customerName", customerName);
        request.setAttribute("customerAddress", customerAddress);
        request.setAttribute("customerPhone", customerPhone);
        request.setAttribute("customerEmail", customerEmail);
        request.setAttribute("quantity", quantity);
        request.setAttribute("employee", employeeID);
        request.getRequestDispatcher("issues-add.jsp").forward(request, response);

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
