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
    StorageDAO storageDao = new StorageDAO();
    List<Storage> storageList = storageDao.selectAll();
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
        <!-- Sidebar -->
        <%
            request.setAttribute("page", "storage");
        %>
        <jsp:include page="assets/templates/sidebar.jsp" /> 
        <!-- Navbar -->
        <jsp:include page="assets/templates/header.jsp" /> 
        <!-- Main -->
        <main class="main">
            <div class="admin-top">
                <h1 class="admin__heading">Storage</h1>
                
                <a href="storage-add.jsp" class="admin__add-btn">+ Add Storage</a>
            </div>
            <%
            if(storageList == null) {
            %>

            <div style="font-size: 3rem; font-weight: 700; display: flex; align-items: center; justify-content: center">
                Empty
            </div> 
            <%} else {
//            if(!productListSearch.isEmpty()) {
//            productList = productListSearch;
//            }
            %>
            <table class="table">
                <thead class="table__head">
                    <tr>
                        <th class="table__heading">ID</th>
                        <th class="table__heading">Name</th>
                        <th class="table__heading">Size(m2)</th>
                        <th class="table__heading">Area</th>
                        <!--<th class="table__heading">Inventory</th>-->
                        <th class="table__heading">Type</th>
                        <th class="table__heading">Status</th>
                        <th class="table__heading">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for(Storage s : storageList) {
                        if(!s.getStatus().equals("closed") ) {
                    %>
                    <tr>

                        <td>
                            <p class="table__data"><%=s.getsID()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=s.getsName()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=s.getsSize()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=s.getsAddress()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=s.getsType()%></p>
                        </td>
                        <td>
                            <%if (s.getStatus().equals("empty")) {%>
                            <p class="table__data" style="color: #db7e06; font-weight: 500"><%=s.getStatus()%></p>
                            <% } else if (s.getStatus().equals("full")) {%>
                            <p class="table__data" style="color: red; font-weight: 500"><%=s.getStatus()%></p>
                            <% } else {%>
                            <p class="table__data" style="color: #3cb72c; font-weight: 500"><%=s.getStatus()%></p>
                            <%}%>
                        </td>
                        <td>
                            <div class="table__act">

                                <%if(!s.getStatus().equals("closed") ){%>
                                <a href="storage-update.jsp?id=<%=s.getsID()%>" class="table__act-btn table__act-btn-edit" title="edit">
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
                                </a>
                                <button  class="table__act-btn table__act-btn-remove js-toggle" data-id="<%=s.getsID()%>" id="btn-del" toggle-target="#delete-confirm"  title="remove">
                                    <svg
                                        fill="#fff"
                                        xmlns="http://www.w3.org/2000/svg"
                                        height="1em"
                                        viewBox="0 0 448 512"
                                        >
                                    <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                                    <path
                                        d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"
                                        />
                                    </svg>
                                    <button/>
                                    <%}%>
                            </div>
                        </td>
                    </tr>
                    <%}}%>
                </tbody>
            </table>
            <%}%>
        </main>
        <%}%>
        <div id="delete-confirm" class="modal modal--small hide">
            <div class="modal__content">
                <p class="modal__text">Do you want to remove this item</p>
                <div class="modal__bottom">
                    <button class="btn btn--small btn--outline modal__btn js-toggle" toggle-target="#delete-confirm">
                        Cancel
                    </button>
                    <button onclick="handleDelete(event)" class="btn btn--small btn--danger btn--primary modal__btn btn--no-margin">
                        Delete
                    </button>
                </div>
            </div>
            <div class="modal__overlay js-toggle" toggle-target="#delete-confirm"></div>
        </div>
    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
    <script>
        function handleDelete(e) {
            if (curID)
                window.location.href = 'storage-delete?id=' + curID;
        }
    </script>
</html>

