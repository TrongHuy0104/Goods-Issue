<%-- 
    Document   : admin
    Created on : Nov 21, 2023, 4:14:02 PM
    Author     : Trong Huy
--%>

<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute("admin");
    UserDAO userDao = new UserDAO();
    IssuesDAO issuesDAO = new IssuesDAO();
    ProductDAO productDao = new ProductDAO();
    StorageDAO storageDao = new StorageDAO();
    List<Issues> issuesList = issuesDAO.selectAllIssues();
    List<User> employees = userDao.selectAllEmployee();
    
    String customerName = (String)request.getAttribute("customerName");
    String customerAddress = (String)request.getAttribute("customerAddress");
    String customerPhone = (String)request.getAttribute("customerPhone");
    String customerEmail = (String)request.getAttribute("customerEmail");
    String employee = (String)request.getAttribute("employee");
    String productName = (String)request.getAttribute("product");
    String issuesID = (String)request.getAttribute("i_id");
    String isExist = (String)request.getAttribute("isExist");
    List<Issues> issuesItems = issuesDAO.getAllIssuesDetail(issuesID);
    
    String id = request.getParameter("i-id");
    Issues issues = issuesDAO.selectIssuesById(id);
    if (id != null) {
        issuesID = id;
    }
    User customer = userDao.selectUserById(issues.getuId());
    System.out.println(customer + customerName);
    if (customerName == null) {
        customerName = customer.getFullName();
    }
    if (customerAddress == null) {
        customerAddress = customer.getDeliveryAddress();
    }
    if (customerPhone == null) {
        customerPhone = customer.getPhone();
    }
    if (customerEmail == null) {
        customerEmail = customer.getEmail();
    }
    System.out.println("id:" + id);
   
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Admin|Grocerymart</title>
        <!-- Favicon -->
        <link rel="apple-touch-icon" sizes="76x76" href="./assets/favicon/apple-touch-icon.png" />
        <link rel="icon" type="image/png" sizes="32x32" href="./assets/favicon/favicon-32x32.png" />
        <link rel="icon" type="image/png" sizes="16x16" href="./assets/favicon/favicon-16x16.png" />
        <link rel="manifest" href="./assets/favicon/site.webmanifest" />
        <link rel="mask-icon" href="./assets/favicon/safari-pinned-tab.svg" color="#5bbad5" />
        <meta name="msapplication-TileColor" content="#da532c" />
        <meta name="theme-color" content="#ffffff" />
        <!-- Fonts -->
        <link rel="stylesheet" href="./assets/fonts/stylesheet.css" />
        <link rel="stylesheet" href="./assets/css/main.css" />
        <link rel="stylesheet" href="./assets/css/admin.css" />
        <!-- Scripts -->
        <!--<script src="./assets/js/scripts.js"></script>-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(function () {
                $("#product").on("input", function () {
                    var inputVal = $(this).val();
                    if (inputVal.length >= 2) {
                        $.ajax({
                            type: "get",
                            url: "AutoSuggestionServlet",
                            data: {input: inputVal},
                            contentType: "application/json",
                            success: function (data) {
                                $("#suggestionList").empty();

                                console.log(2);
                                for (var i = 0; i < data.length; i++) {
                                    $("#suggestionList").append("<li>" + data[i] + "</li>");
                                }
                            }
                        });
                    }
                });

                $(document).on("click", "#suggestionList li", function () {
                    var selectedValue = $(this).text();
                    $("#product").val(selectedValue);
                    $("#suggestionList").empty();
                });
            });
        </script>

    </head>
    <body>
        <%
            if (user == null) {
        %>
        <h3 style='color:crimson; font-size: 30px; font-weight: 500; text-align: center'>You are not logged into the system! <a href='./index.jsp'>Sign In</a></h3>")
        <%} else {%> 
        <%
            request.setAttribute("page", "issues");
        %>
        <jsp:include page="assets/templates/sidebar.jsp" /> 
        <!-- Navbar -->

        <jsp:include page="assets/templates/header.jsp" /> 
        <!-- Main -->
        <main class="main">
            <div class="order-right issues__list">
                <h3 class="order-right__heading">Issues Items Add Form</h3>
                <p class="form__note">${note == "" ? "" : note}</p>
                <p class="form__error">${error == "" ? "" : error}</p>
                <form action="issues-add" method="POST" class="form auth__form row row-cols-2 row-cols-md-1 g-4" id="sign-up-form" style="margin-left: 0;">
                    <input type="hidden" name="i_id" value="<%=issuesID%>"/>
                    <div class="col" style=" margin-top: 0;">
                        <%
                            if (issues.getiId() != null) {
                                isExist = "true";
                            }
                        %>
                        <div class="form__group">
                            <label for="customer-name">Customer Name: </label>
                            <div class="form__text-input">
                                <input type="text" class="<%=(isExist != null && !isExist.equals("")) ? "form__input disabled" : "form__input"%>" name="name" value="<%=customerName == null ? "" : customerName%>" id="customer-name" rules="required|minLength:2">
                                <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group">
                            <label for="customer-address">Customer Address: </label>
                            <div class="form__text-input">
                                <input type="text" class="<%=(isExist != null && !isExist.equals("")) ? "form__input disabled" : "form__input"%>" name="address" value="<%=customerAddress == null ? "" : customerAddress%>" rules="required|minLength:2" id="customer-address">
                                <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group">
                            <label for="customer-phone">Customer Phone: </label>
                            <div class="form__text-input">
                                <input type="text" class="<%=(isExist != null && !isExist.equals("")) ? "form__input disabled" : "form__input"%>" name="phone" value="<%=customerPhone == null ? "" : customerPhone%>" rules="required|phone" id="customer-phone">
                                <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group">
                            <label for="customer-email">Customer Email(optional): </label>
                            <div class="form__text-input">
                                <input type="text" class="<%=(isExist != null && !isExist.equals("")) ? "form__input disabled" : "form__input"%>" name="email" value="<%=customerEmail == null ? "" : customerEmail%>" id="customer-email">
                                <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                            </div>
                        </div>
                    </div>

                    <div class="col" style=" margin-top: 0;">
                        <div class="form__group">
                            <label>Product: </label>
                            <div class="form__text-input">
                                <input name="text" class="form__input" id="product" value="<%=productName == null ? "" : productName%>" rules="required|minLength:2">
                                <ul id="suggestionList"></ul>                            

                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group">
                            <label for="quantity">Quantity: </label>
                            <div class="form__text-input">
                                <input type="number" class="form__input" name="quantity" value="" rules="required|integer|isNonNegative" id="quantity">
                                <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group">
                            <label>Employee: </label>
                            <div class="form__text-input">
                                <%
                                if (issues.getiId() != null) {
                                    isExist = "true";
                                }
                                %>
                                <select name="employee" class="<%=(isExist != null && !isExist.equals("")) ? "form__input disabled" : "form__input"%>" rules="required">
                                    <option value="" hidden></option>
                                    <%
                                    if (isExist != null && !isExist.equals("") ) {
                                        User em = new User();
                                        if (issues.getiId() != null) {
                                            em = userDao.selectUserById(issues.geteId());
                                        } else {
                                            em = userDao.selectUserById(employee);
                                        }
                                    %>
                                    <option selected value="<%=em.getId()%>"><%=em.getFullName()%></option>
                                    <%} else {%>
                                    <%for(User e : employees) {%>
                                    <option value="<%=e.getId()%>"><%=e.getFullName()%></option>
                                    <%}}%>
                                </select>
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group auth__btn-group">
                            <button class="btn btn--primary auth__btn form__submit-btn">Add</button>
                        </div>
                    </div>
                </form> 
            </div>

            <div class="order-right issues__list">
                <h3 class="order-right__heading">Issues Items</h3>
                <table>
                    <thead>
                        <tr class="order-right__table-heading">
                            <th style="width: 100%; text-align: left">Product</th>
                            <th style="min-width: 150px; text-align: right">Quantity</th>
                            <th style="min-width: 150px; text-align: right">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (issuesItems == null) {
                        %>
                        <tr>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>        

                        <%
                            }
                            if (issuesItems.isEmpty()) {
                                issuesItems = issuesDAO.getAllIssuesDetail(id);
                            }
                            for(Issues item : issuesItems) {
                            String url;
                            String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                            + request.getContextPath();
                            String avatarURL= item.getpThumb();
                            if (avatarURL != null) {
                            url = root + "/assets/img/product/" + avatarURL;
                            } else url = "";
                        %>
                        <tr>
                            <td>
                                <div class="table__user">
                                    <img
                                        src="<%=url%>"
                                        alt=""
                                        class="table__user-product"
                                        />
                                    <div class="table__user-info">
                                        <span class="table__user-name"><%=item.getpName()%></span>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="table__data" style="text-align: right"><%=item.getQty()%></p>
                            </td>
                            <td>
                                <p class="table__data" style="text-align: right"><%=item.getQty() * item.getpPrice()%></p>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <%}%>
            </div>
    </body>
    <!--<script>window.dispatchEvent(new Event("template-loaded"));</script>-->
    <script src="./assets/js/validator.js"></script>
    <script>
            new Validator("#sign-up-form");
    </script>
</html>
