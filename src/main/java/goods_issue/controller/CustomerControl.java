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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Trong Huy
 */
public class CustomerControl extends HttpServlet {

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

    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            try {
                String userName = request.getParameter("username");
                String pass = request.getParameter("password");
                String name = request.getParameter("name");
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                String deliveryAddress = request.getParameter("delivery-address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");

                request.setAttribute("username", userName);
                request.setAttribute("password", pass);
                request.setAttribute("name", name);
                request.setAttribute("gender", gender);
                request.setAttribute("address", address);
                request.setAttribute("deliveryAddress", deliveryAddress);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);

                String url = "";
                String error = "";

                UserDAO userDAO = new UserDAO();

                if (userDAO.checkUserIsDuplicated(userName)) {
                    error += "Username already exists, please choose another username!";
                }
                if (userDAO.checkEmailIsDuplicated(email)) {
                    error += "Email already exists, please choose another email!";
                }

                request.setAttribute("error", error);

                if (error.length() > 0) {
                    url = "/customer-add.jsp";
                } else {
                    Random rd = new Random();
                    String id = System.currentTimeMillis() + rd.nextInt(1000) + "";
                    pass = encodeToSHA256(pass);

                    User u = new User(id, userName, pass, address, email, name, gender, phone, deliveryAddress);
                    userDAO.insert(u);
                    request.setAttribute("note", "Account created successfully, please log in again!");
                    url = "/customer-add.jsp";
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CustomerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleAddCustomer(HttpServletRequest request, HttpServletResponse response) {
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

                String contentType = request.getContentType();
                if (contentType.indexOf(contentType) >= 0) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    System.out.println(2);
                    factory.setSizeThreshold(maxMemSize);

                    ServletFileUpload upload = new ServletFileUpload(factory);

                    upload.setSizeMax(maxFileSize);

                    List<FileItem> files = upload.parseRequest(request);
                    System.out.println(files);
                    for (FileItem fileItem : files) {
                        System.out.println(fileItem.getFieldName());
                        if (fileItem.isFormField()) {
                            System.out.println(3);
                            switch (fileItem.getFieldName()) {
                                
                                case "username": {
                                    String userName = fileItem.getString("UTF-8");
                                    tempUser.setUserName(userName);
                                    break;
                                }
                                case "password": {
                                    String pass = fileItem.getString("UTF-8");
                                    tempUser.setPassword(pass);
                                    break;
                                }
                                case "name": {
                                    String name = fileItem.getString("UTF-8");
                                    tempUser.setFullName(name);
                                    break;
                                }
                                case "gender": {
                                    String gender = fileItem.getString("UTF-8");
                                    tempUser.setGender(gender);
                                    break;
                                }
                                case "address": {
                                    String address = fileItem.getString("UTF-8");
                                    tempUser.setAddress(address);
                                    break;
                                }
                                case "delivery-address": {
                                    String shipAddress = fileItem.getString("UTF-8");
                                    tempUser.setDeliveryAddress(shipAddress);
                                    break;
                                }
                                case "phone": {
                                    String phone = fileItem.getString("UTF-8");
                                    tempUser.setPhone(phone);
                                    break;
                                }
                                case "email": {
                                    String email = fileItem.getString("UTF-8");
                                    tempUser.setPhone(email);
                                    break;
                                }
                            }
//                            String name = fileItem.getFieldName();
//                            String value = fileItem.getString();
//
//                            tempUser.setId(value);
//                            user = userDAO.selectById(tempUser);
                            String url = "";
                            String error = "";

                            if (userDAO.checkUserIsDuplicated(tempUser.getUserName())) {
                                error += "Username already exists, please choose another username!";
                            }
                            if (userDAO.checkEmailIsDuplicated(tempUser.getEmail())) {
                                error += "Email already exists, please choose another email!";
                            }

                            request.setAttribute("error", error);

                            if (error.length() > 0) {
                                url = "/customer-add.jsp";
                            } else {
                                Random rd = new Random();
                                String id = System.currentTimeMillis() + rd.nextInt(1000) + "";
                                tempUser.setPassword(encodeToSHA256(tempUser.getPassword()));
                                tempUser.setId(id);
                                request.setAttribute("note", "Account created successfully!");
                                url = "/customer-add.jsp";
                            }
                        }else
                        if (!fileItem.isFormField()) {
                            if (!fileItem.getName().equals("")) {
                                String fileName = System.currentTimeMillis() + fileItem.getName();
                                String path = folder + "\\" + fileName;
                                file = new File(path);

                            System.out.println(4);
                                fileItem.write(file);

                                tempUser.setAvatar(fileName);
//                                userDAO.updateAvatar(tempUser);
                                user = userDAO.selectById(user);

                            }
                        }
                        System.out.println("1");
                        userDAO.insert(tempUser);

                    }
                }
                request.setAttribute("note", "Update succesfully!");
                request.getRequestDispatcher("customer-add.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String contentType = request.getContentType();
        if (contentType.contains(contentType)) {
            try {
                DiskFileItemFactory fac = new DiskFileItemFactory();

                ServletFileUpload upl = new ServletFileUpload(fac);

                List<FileItem> files = upl.parseRequest(request);

                for (FileItem fileItem : files) {
                System.out.println(fileItem);
                    if (fileItem.isFormField()) {

                        String name = fileItem.getFieldName();
                        if (name.equals("action")) {
                            String value = fileItem.getString();
                            if (value.equals("add")) {
                                handleAddCustomer(request, response);
                            }
                        }

                    }
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(CustomerControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String action = request.getParameter("action");
//            if (action.equals("add")) {
//                handleAddCustomer(request, response);
//            }
        }
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
        doGet(request, response);
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
