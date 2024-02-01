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
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Trong Huy
 */
public class CustomerUpdateControl extends HttpServlet {

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
            out.println("<title>Servlet CustomerUpdateControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerUpdateControl at " + request.getContextPath() + "</h1>");
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
                Boolean isValid = false;
                String url = "";
                String note = "";
                
                String contentType = request.getContentType();
                if (contentType.indexOf(contentType) >= 0) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setSizeThreshold(maxMemSize);
                    
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    
                    upload.setSizeMax(maxFileSize);
                    
                    List<FileItem> files = upload.parseRequest(request);
                    for (FileItem fileItem : files) {
                        if (fileItem.isFormField()) {
                            switch (fileItem.getFieldName()) {
                                
                                case "id": {
                                    String id = fileItem.getString("UTF-8");
                                    tempUser.setId(id);
                                    request.setAttribute("id", id);
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
                        }
                        if (!fileItem.isFormField()) {
//                            if (isValid) {
                            if (!fileItem.getName().equals("")) {
                                isValid = true;
                                String fileName = System.currentTimeMillis() + fileItem.getName();
                                String path = folder + "\\" + fileName;
                                file = new File(path);
                                fileItem.write(file);
                                tempUser.setAvatar(fileName);
                            }
//                            }
                        }
                        
                    }
                    if (isValid) {
                        userDAO.updateUserInfo(tempUser);
                    } else {
                        userDAO.updateUserInfoNonImg(tempUser);
                    }
                }
                request.setAttribute("note", "Information has been updated!");
                request.getRequestDispatcher("customer-update.jsp?id=" + tempUser.getId()).forward(request, response);
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
