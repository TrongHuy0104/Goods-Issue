/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.UserDAO;
import goods_issue.model.User;
import static goods_issue.util.Encode.encodeToSHA256;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Trong Huy
 */
public class CustomerAddControl extends HttpServlet {

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
            out.println("<title>Servlet CustomerAddControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerAddControl at " + request.getContextPath() + "</h1>");
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
        Object obj = request.getSession().getAttribute("user");
        User user = null;
        if (obj != null) {
            user = (User) obj;
        }
        if (user != null) {
            try {
                UserDAO userDAO = new UserDAO();
                User tempUser = new User();
                String folder = getServletContext().getRealPath("\\assets\\img\\avatar");
                File file;
                int maxFileSize = 5000 * 1024;
                int maxMemSize = 5000 * 1024;
                Boolean isValid = true;
                String url = "";
                String error = "";

                String contentType = request.getContentType();
                if (contentType.indexOf(contentType) >= 0) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    System.out.println(2);
                    factory.setSizeThreshold(maxMemSize);

                    ServletFileUpload upload = new ServletFileUpload(factory);

                    upload.setSizeMax(maxFileSize);

                    List<FileItem> files = upload.parseRequest(request);
                    for (FileItem fileItem : files) {
                        if (fileItem.isFormField()) {
                            switch (fileItem.getFieldName()) {

                                case "username": {
                                    String userName = fileItem.getString("UTF-8");
                                    tempUser.setUserName(userName);
                                    request.setAttribute("username", userName);
                                    break;
                                }
                                case "password": {
                                    String pass = fileItem.getString("UTF-8");
                                    tempUser.setPassword(pass);
                                    request.setAttribute("password", pass);
                                    break;
                                }
                                case "name": {
                                    String name = fileItem.getString("UTF-8");
                                    tempUser.setFullName(name);
                                    request.setAttribute("name", name);
                                    break;
                                }
                                case "gender": {
                                    String gender = fileItem.getString("UTF-8");
                                    tempUser.setGender(gender);
                                    request.setAttribute("gender", gender);
                                    break;
                                }
                                case "address": {
                                    String address = fileItem.getString("UTF-8");
                                    tempUser.setAddress(address);
                                    request.setAttribute("address", address);
                                    break;
                                }
                                case "delivery-address": {
                                    String shipAddress = fileItem.getString("UTF-8");
                                    tempUser.setDeliveryAddress(shipAddress);
                                    request.setAttribute("deliveryAddress", shipAddress);
                                    break;
                                }
                                case "phone": {
                                    String phone = fileItem.getString("UTF-8");
                                    tempUser.setPhone(phone);
                                    request.setAttribute("phone", phone);
                                    break;
                                }
                                case "email": {
                                    String email = fileItem.getString("UTF-8");
                                    tempUser.setEmail(email);
                                    request.setAttribute("email", email);
                                    break;
                                }
                            }

                            if (userDAO.checkUserIsDuplicated(tempUser.getUserName())) {
                                error = "Username already exists, please choose another username!";
                                isValid = false;
                            } else {
                                isValid = true;
                                if (userDAO.checkEmailIsDuplicated(tempUser.getEmail())) {
                                    error = "Email already exists, please choose another email!";
                                    isValid = false;
                                }
                            }

                            request.setAttribute("error", error);

                            if (error.length() > 0) {
                                url = "/customer-add.jsp";
                            } else {
                                Random rd = new Random();
                                String id = System.currentTimeMillis() + rd.nextInt(1000) + "";
                                tempUser.setPassword(encodeToSHA256(tempUser.getPassword()));
                                tempUser.setId(id);
                            }
                        }
                        if (!fileItem.isFormField()) {
                            if (isValid) {
                                if (!fileItem.getName().equals("")) {
                                    String fileName = System.currentTimeMillis() + fileItem.getName();
                                    String path = folder + "\\" + fileName;
                                    file = new File(path);
                                    fileItem.write(file);
                                    tempUser.setAvatar(fileName);
                                }
                            }
                        }

                    }
                    if (isValid) {
                        request.setAttribute("username", "");
                        request.setAttribute("password", "");
                        request.setAttribute("name", "");
                        request.setAttribute("gender", "");
                        request.setAttribute("address", "");
                        request.setAttribute("deliveryAddress", "");
                        request.setAttribute("phone", "");
                        request.setAttribute("email", "");
                        request.setAttribute("policy", "");
                        request.setAttribute("note", "Account created successfully!");
                        userDAO.insert(tempUser);
                    }
                }
                request.getRequestDispatcher("customer-add.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
