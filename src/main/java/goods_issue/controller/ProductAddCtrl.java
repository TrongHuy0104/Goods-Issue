/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.ProductDAO;
import goods_issue.model.Product;
import goods_issue.model.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Trong Huy
 */
public class ProductAddCtrl extends HttpServlet {

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
            out.println("<title>Servlet ProductAddCtrl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductAddCtrl at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        Object obj = request.getSession().getAttribute("user");
        User user = null;
        if (obj != null) {
            user = (User) obj;
        }
        if (user != null) {
            try {
                ProductDAO pDao = new ProductDAO();
                Product tempProduct = new Product();
                String folder = getServletContext().getRealPath("\\assets\\img\\product");
                File file;
                int maxFileSize = 5000 * 1024;
                int maxMemSize = 5000 * 1024;
                Boolean isValid = true;
                String url = "";
                String error = "";

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
                                case "productName": {
                                    String productName = fileItem.getString("UTF-8");
                                    tempProduct.setpName(productName);
                                    request.setAttribute("productName", productName);
                                    break;
                                }
                                case "from": {
                                    String from = fileItem.getString("UTF-8");
                                    tempProduct.setpOrigin(from);
                                    request.setAttribute("from", from);
                                    break;
                                }
                                case "price": {
                                    String price = fileItem.getString("UTF-8");
                                    tempProduct.setpPrice(Double.parseDouble(price));
                                    request.setAttribute("price", price);
                                    break;
                                }
                                case "quantity": {
                                    int quantity = Integer.parseInt(fileItem.getString("UTF-8"));

                                    int status;
                                    if (quantity == 0) {
                                        status = 0;
                                    } else if (quantity > 10) {
                                        status = 2;
                                    } else {
                                        status = 1;
                                    }
                                    tempProduct.setpNumberLeft(quantity);
                                    tempProduct.setpStatus(status);
                                    request.setAttribute("quantity", quantity);
                                    request.setAttribute("status", status);
                                    break;
                                }
                                case "desc": {
                                    String desc = fileItem.getString("UTF-8");
                                    tempProduct.setpDescription(desc);
                                    request.setAttribute("desc", desc);
                                    break;
                                }
                                case "store": {
                                    String store = fileItem.getString("UTF-8");
                                    tempProduct.setsId(store);
                                    request.setAttribute("store", store);
                                    break;
                                }
                                case "category": {
                                    int cId = Integer.valueOf(fileItem.getString("UTF-8"));
                                    String category = null;
                                    String code = null;
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
                                    tempProduct.setpCateId(cId);
                                    tempProduct.setpCategory(category);
                                    tempProduct.setpCode(code);
                                    request.setAttribute("category", category);
                                    request.setAttribute("code", code);

                                    break;
                                }
                            }

                            if (pDao.checkProductIsDuplicated(tempProduct.getpName())) {
                                error = "Product already exists, please choose another product name!";
                                isValid = false;
                            } else {
                                isValid = true;
                            }

                            request.setAttribute("error", error);
                            if (error.length() > 0) {
                                url = "/product-add.jsp";
                            } else {
                                Random rd = new Random();
                                String id = System.currentTimeMillis() + rd.nextInt(1000) + "";
                                tempProduct.setpId(id);
                            }
                        }
                        if (!fileItem.isFormField()) {
                            if (isValid) {
                                if (!fileItem.getName().equals("")) {
                                    String fileName = System.currentTimeMillis() + fileItem.getName();
                                    String path = folder + "\\" + fileName;
                                    file = new File(path);
                                    fileItem.write(file);
                                    tempProduct.setpThumb(fileName);
                                }
                            }
                        }
                    }
                }
                if (isValid) {
                    request.setAttribute("productName", "");
                    request.setAttribute("from", "");
                    request.setAttribute("price", "");
                    request.setAttribute("quantity", "");
                    request.setAttribute("category", "");
                    request.setAttribute("desc", "");
                    request.setAttribute("store", "");
//                    request.setAttribute("status", 1);
                    request.setAttribute("code", "");
                    request.setAttribute("store", "");
                    request.setAttribute("note", "Account created successfully!");

                    pDao.insert(tempProduct);
                    pDao.insertProductCate(tempProduct);
                    pDao.insertProductDetail(tempProduct);
                }
                request.getRequestDispatcher("product-add.jsp").forward(request, response);
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
