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
    String i_id = request.getParameter("id");
    IssuesDAO issuesDao = new IssuesDAO();
    StorageDAO storageDao = new StorageDAO();
    Issues i = issuesDao.getIssuesByIssuesID(i_id);
    User u = userDao.selectById(new User(i.getuId()));
    Storage s = storageDao.selectById(new Storage(i.getsId()));
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
        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th,
            td {
                border: 1px solid #000;
                padding: 8px;
                text-align: center;
            }
            th {
                text-align: center;
            }
            h1 {
                float: right;
            }
            .invoice-header {
                margin-bottom: 20px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: flex-end;
            }
            .invoice-number {
                font-size: 24px;
                margin-bottom: 10px;
            }
            .bill-to-details p {
                /*margin-left: 30px;*/
            }
            .warning p {
                text-align: left;
            }
            .price-wrap {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .price-wrap span {
                margin: 0 20px;
            }
            .notice {
                text-align: center;
            }
        </style>
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
            <div class="invoice-header">
                <h1>Goods delivery note</h1>
                <div class="invoice-header-body">
                    <table>
                        <tr>
                            <th>Note No:</th>
                            <th>Note Date:</th>
                        </tr>
                        <tr>
                            <td><%=i.getiId()%></td>
                            <td><%=i.getDate()%></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="bill-to-details">
                <h2>Bill To</h2>
                <p>Name: <%=u.getFullName()%></p>
                <p>Phone: <%=u.getPhone()%></p>
                <p>Email: <%=u.getEmail()%></p>
                <p>Address: <%=u.getDeliveryAddress()%></p>
            </div>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Item</th>
                    <th>Origin</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Amount</th>
                </tr>
                <%
                    int index = 0;
                    for(Issues item : issuesItems) {
                    index++;
                %>
                <tr>
                    <td><%=index%></td>
                    <td><%=item.getpName()%></td>
                    <td><%=item.getpOrigin()%></td>
                    <td><%=item.getpPrice()%></td>
                    <td><%=item.getQty()%></td>
                    <td><%=item.getpPrice() * item.getQty()%></td>
                </tr>
                <%}%>
                <tr>
                    <td colspan="3" rowspan="4" class="warning">
                        <p>Warranty</p>
                        <p>
                            * Products purchased comes with 1 year national warranty
                            (if applicable)
                        </p>
                        <p>
                            * Warranty should be claimed only from the respective
                            manufactures
                        </p>
                    </td>
                    <td colspan="3">
                        <div class="price-wrap">
                            <span>SubTotal:</span><span><%=total%></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div class="price-wrap">
                            <span>Discount(0%):</span><span>0</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div class="price-wrap">
                            <span>Tax(10%):</span><span><%=total * 10 / 100%></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <div class="price-wrap">
                            <span>Total:</span><span><%=total + total * 10 / 100%></span>
                        </div>
                    </td>
                </tr>
            </table>

            <p class="notice">
                Goods once sold will not be taken back or exchanged - Subject to
                product justification - Product damage not responsible - Service
                only at concerned authorized Service centers
            </p>
            <div class="button-wrap"><a href="issues-detail.jsp?id=<%=i_id%>"  class="admin__add-btn">← Back </a>
                <a href="generateIssuesPdf?id=<%=i_id%>"  class="admin__add-btn"> Export Note (PDF) </a>
            </div>
        </main>
        <%}%>
    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
</html>

