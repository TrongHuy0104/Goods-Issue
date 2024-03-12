<%-- 
    Document   : admin
    Created on : Nov 21, 2023, 4:14:02 PM
    Author     : Trong Huy
--%>

<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute("admin");
    UserDAO userDao = new UserDAO();
    IssuesDAO issuesDao = new IssuesDAO();
    StorageDAO storageDao = new StorageDAO();
    String i_id = request.getParameter("id");
    Issues i = issuesDao.getIssuesByIssuesID(i_id);
    User u = userDao.selectById(new User(i.getuId()));
    User e = userDao.selectById(new User(i.geteId()));
    List<Issues> issuesItems = issuesDao.getAllIssuesDetail(i_id);
    double total = issuesDao.getTotalIssuesPrice(issuesItems);
    
    
%>
<!DOCTYPE html>
<html lang="en">
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
        <!-- Stylesheets -->
        <link rel="stylesheet" href="./assets/css/main.css" />
        <link rel="stylesheet" href="./assets/css/admin.css" />
        <!-- Scripts -->
        <script src="./assets/js/scripts.js"></script>
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
            <div class="admin-top">
                <h1 class="admin__heading">Issues Details</h1>
            </div>
            <div class="row">
                <div class="col-4">
                    <div class="order-left">
                        <div class="order-left__block">
                            <h3 class="order-left__heading">Issues Info</h3>
                            <div class="order-left__item">
                                <span class="order-left__item-text">ID</span>
                                <span class="order-left__item-text"><%=i.getiId()%></span>
                            </div>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Date</span>
                                <span class="order-left__item-text"><%=i.getDate()%></span>
                            </div>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Status</span>
                                <%
                                if(i.getStatus() == 1) {
                                %>
                                <p class="table__data order-left__item-text" style="color: #3cb72c; font-weight: 600">Completed</p>
                                <%}%>
                                <%--<p class="table__data order-left__item-text" style="color: red; font-weight: 600">Canceled</p>
                                <%} else if (o.getStatus() == 1) {%>
                                <p class="table__data order-left__item-text" style="color: #db7e06; font-weight: 600">Pending</p>
                                <% } else if (o.getStatus() == 2) {%>
                                <p class="table__data order-left__item-text" style="color: #db7e06; font-weight: 600">Confirmed</p>
                                <% } else if (o.getStatus() == 3) {%>
                                <p class="table__data order-left__item-text" style="color: #db7e06; font-weight: 600">Packed</p>
                                <% } else if (o.getStatus() == 4) {%>
                                <p class="table__data order-left__item-text" style="color: #db7e06; font-weight: 600">In Transit</p>
                                <%} else {%>
                                <p class="table__data order-left__item-text" style="color: #3cb72c; font-weight: 600">Completed</p>
                                <%}%>--%>
                            </div>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Employee</span>
                                <span class="order-left__item-text"><%=e.getFullName()%></span>
                            </div>
                        </div>
                    </div>
                    <div class="order-left">
                        <div class="order-left__block">
                            <h3 class="order-left__heading">Customer Detail</h3>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Name</span>
                                <span class="order-left__item-text"><%=u.getFullName()%></span>
                            </div>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Phone</span>
                                <span class="order-left__item-text"><%=u.getPhone()%></span>
                            </div>
                            <div class="order-left__item">
                                <span class="order-left__item-text">Email</span>
                                <span class="order-left__item-text"><%=u.getEmail()%></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-8">
                    <div class="order-right">
                        <h3 class="order-right__heading">Issues Items</h3>
                        <table>
                            <thead>
                                <tr class="order-right__table-heading">
                                    <th style="width: 100%; text-align: left">Product</th>
                                    <th style="min-width: 150px; text-align: right">Storage</th>
                                    <th style="min-width: 150px; text-align: right">Quantity</th>
                                    <th style="min-width: 150px; text-align: right">Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for(Issues item : issuesItems) {
//                                    Storage s = storageDao.selectById(new Storage(item.getsID()));
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
                                        <p class="table__data" style="text-align: right"><%=item.getsId()%></p>
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
                        <!--<div class="order-right__price">Shipping: <span>$10.00 </span></div>-->
                        <div class="order-right__price">Total: <span><%=total%> </span></div>
                        <div class="order-right__price"> <a href="issues-preview.jsp?id=<%=i_id%>"  class="admin__add-btn">Preview Export Note (PDF) </a></div>
                        <%}%>
                        <!--generateIssuesPdf-->
                    </div>
                </div>
            </div>




        </main>
    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
</html>

