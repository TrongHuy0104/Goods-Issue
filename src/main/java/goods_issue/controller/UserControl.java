/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import goods_issue.dataAccess.UserDAO;
import goods_issue.model.User;
import goods_issue.util.Email;
import goods_issue.util.Encode;
import static goods_issue.util.Encode.encodeToSHA256;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Trong Huy
 */
public class UserControl extends HttpServlet {

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
            out.println("<title>Servlet UserControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserControl at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("sign-in")) {
            signIn(request, response);
        }
        if (action.equals("validate-email")) {
            validateEmail(request, response);
        }
        if (action.equals("resetpassword")) {
            resetPassword(request, response);
        }
        if (action.equals("check-otp")) {
            checkOTP(request, response);
        }
        if (action.equals("log-out")) {
            logOut(request, response);
        }
    }

    public Boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
        Boolean result = false;
        Object obj = request.getSession().getAttribute("user");
        User user = null;
        if (obj != null) {
            user = (User) obj;
            if (user != null) {
                int isAdmin = user.getRole();
                if (isAdmin == 0) {
                    result = false;
                } else {
                    result = true;
                }
            }
        }
        return result;
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            request.setAttribute("username", username);
            password = encodeToSHA256(password);
            User tempUser = new User();
            tempUser.setUserName(username);
            tempUser.setPassword(password);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.checkSignIn(tempUser);
            String url = "";
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if (isAdmin(request, response)) {
                    session.setAttribute("admin", user);
                    url = "admin.jsp";
                } else {
                    request.setAttribute("error", "You are not a manager of this website!");
                    url = "/index.jsp";
                }
            } else {
                request.setAttribute("error", "Username or password is incorrect!");
                url = "/index.jsp";
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validateEmail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String note = "";
            String url = "";

            UserDAO userDAO = new UserDAO();
            User user = new User();
            user.setUserName(userName);
            user.setEmail(email);

            if (!userDAO.checkUsernameEmailIsDuplicated(userName, email)) {
                note = "Username or email is incorrect!";
                url = "/reset-password.jsp";
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("reset-key", userName);
                note = "Email and username is correctly";
                String OTP = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
                session.setAttribute("otp", OTP);
                Email.sendEmail(user.getEmail(), "Verify Goods Issue Account.", OTP);
                url = "/reset-password-emailed.jsp";
            }
            request.setAttribute("note", note);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String password = request.getParameter("password");
            password = Encode.encodeToSHA256(password);
            String username = (String) request.getSession().getAttribute("reset-key");
            User user = new User();
            user.setPassword(password);
            user.setUserName(username);

            UserDAO userDAO = new UserDAO();
            userDAO.resetPassword(user);
            request.setAttribute("note", "Reset password successfully. Please sign in again!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkOTP(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String curOTP = (String) session.getAttribute("otp");
            System.out.println(curOTP);
            String userOTP = (String) request.getParameter("otp");
            System.out.println(userOTP);
            String url = "";
            if (curOTP.equals(userOTP)) {
                request.setAttribute("note", "Reset your password!");
                url = "/update-password.jsp";
            } else {
                request.setAttribute("note", "OTP is not correct");
                url = "/reset-password-emailed.jsp";
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            response.sendRedirect("./index.jsp");
        } catch (IOException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
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
