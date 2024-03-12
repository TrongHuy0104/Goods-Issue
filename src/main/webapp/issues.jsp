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
    IssuesDAO issuesDAO = new IssuesDAO();
    List<Issues> issuesList = issuesDAO.selectAllIssues();
    
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
                <h1 class="admin__heading">Issues</h1>
                <form action="customer.jsp" method="POST" class="search-bar d-flex admin__search-bar">
                    <input
                        type="text"
                        name="data"
                        id=""
                        value=""
                        placeholder="Search for item"
                        class="search-bar__input"
                        />
                    <button class="search-bar__submit">
                        <img src="./assets/icons/search.svg" alt="" class="search-bar__icon icon" />
                    </button>
                </form>
                <a href="issues-add.jsp" class="admin__add-btn">+ Add Issues</a>
            </div>
            <%
            if(issuesList == null) {
            %>

            <div style="font-size: 3rem; font-weight: 700; display: flex; align-items: center; justify-content: center">
                Empty
            </div> 
            <%} else {%>
            <table class="table">
                <thead class="table__head">
                    <tr>
                        <th class="table__heading">ID</th>
                        <th class="table__heading">Date</th>
                        <th class="table__heading">Customer</th>
                        <th class="table__heading">Total</th>
                        <th class="table__heading">Status</th>
                        <th class="table__heading">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for(Issues i : issuesList) {
                        User u = new User();
                        u = userDao.selectUserById(i.getuId());
                        List<Issues> issuesItems = null;
                        issuesItems = issuesDAO.getAllIssuesDetail(i.getiId(), u.getId());
                        double total = issuesDAO.getTotalIssuesPrice(issuesItems);
                    %>
                    <tr>
                        <td>
                            <p class="table__data"><%=i.getiId()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=i.getDate()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=u.getFullName()%></p>
                        </td>

                        <td>
                            <p class="table__data"><%=total%></p>
                        </td>
                        <td>
                            <%
                            if(i.getStatus() == 1) {
                            %>

                            <p class="table__data" style="color: #db7e06; font-weight: 600">In Progress</p>
                            <%} else if (i.getStatus() == 2) {%>
                            <p class="table__data" style="color: #3cb72c; font-weight: 600">Completed</p>
                            <% } else if (i.getStatus() == 0) {%>
                            <p class="table__data" style="color: red; font-weight: 600">Canceled</p>
                            <%}%>
                        </td>
                        <td>
                            <div class="table__act">

                                <a href="issues-detail.jsp?id=<%=i.getiId()%>" class="table__act-btn table__act-btn-avatar"  title="view">
                                    <svg fill="rgb(143, 159, 188)" xmlns="http://www.w3.org/2000/svg" height="20" width="20" viewBox="0 0 576 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M288 80c-65.2 0-118.8 29.6-159.9 67.7C89.6 183.5 63 226 49.4 256c13.6 30 40.2 72.5 78.6 108.3C169.2 402.4 222.8 432 288 432s118.8-29.6 159.9-67.7C486.4 328.5 513 286 526.6 256c-13.6-30-40.2-72.5-78.6-108.3C406.8 109.6 353.2 80 288 80zM95.4 112.6C142.5 68.8 207.2 32 288 32s145.5 36.8 192.6 80.6c46.8 43.5 78.1 95.4 93 131.1c3.3 7.9 3.3 16.7 0 24.6c-14.9 35.7-46.2 87.7-93 131.1C433.5 443.2 368.8 480 288 480s-145.5-36.8-192.6-80.6C48.6 356 17.3 304 2.5 268.3c-3.3-7.9-3.3-16.7 0-24.6C17.3 208 48.6 156 95.4 112.6zM288 336c44.2 0 80-35.8 80-80s-35.8-80-80-80c-.7 0-1.3 0-2 0c1.3 5.1 2 10.5 2 16c0 35.3-28.7 64-64 64c-5.5 0-10.9-.7-16-2c0 .7 0 1.3 0 2c0 44.2 35.8 80 80 80zm0-208a128 128 0 1 1 0 256 128 128 0 1 1 0-256z"/></svg></a>

                                <div class="table__act-btn table__act-btn-status"  title="update status">
                                    <svg
                                        fill="rgb(143, 159, 188)"
                                        xmlns="http://www.w3.org/2000/svg"
                                        height="1em"
                                        viewBox="0 0 512 512"
                                        >
                                    <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                                    <path
                                        d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"
                                        />
                                    </svg>
                                    <ul class="order-status__list">
                                        <li class="order-status__item">
                                            <a href="update-issues-status?action=completed&id=<%=i.getiId()%>" class="order-status__link">
                                                Completed
                                            </a>
                                        </li>
                                        <li class="order-status__item">
                                            <a href="update-issues-status?action=cancel&id=<%=i.getiId()%>" class="order-status__link">
                                                Cancel
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <%
                                if(i.getStatus() == 1) {
                                %>
                                <a href="issues-add.jsp?i-id=<%=i.getiId()%>" class="table__act-btn table__act-btn-avatar"  title="add">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"></path></svg> 
                                <%}%>
                            </div>
                        </td>
                        <%}%>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <%}%>
        </main>
    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
</html>

