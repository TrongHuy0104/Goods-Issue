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
    List<User> userList = userDao.selectAllCustomer();
    String dataSearch = request.getParameter("data-search");
    List<User> userListSearch = userDao.searchByName(dataSearch);
    
    String indexPage = request.getParameter("index");
    if (indexPage == null) {
        indexPage = "1";
    }
    int index = Integer.parseInt(indexPage);
    int pageLimit = 10;
    int pCount = !userListSearch.isEmpty() ? userListSearch.size() : userDao.countTotal();
    
    int endPage = pCount / pageLimit;
    if (endPage == 0 || endPage % pageLimit != 0) {
        endPage++;
    }

    int itemStart = (index - 1) * pageLimit + 1;
    int itemEnd;
    if (index == endPage || !userListSearch.isEmpty()) {
        itemEnd = pCount;
    } else {
        itemEnd = index * pageLimit;
    }
    List<User> customerList = new ArrayList<>();
    if (dataSearch != null) {
        customerList = userListSearch;
    } else {
        customerList = userDao.paging(index, pageLimit);
    }
    
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
            request.setAttribute("page", "customer");
        %>
        <jsp:include page="assets/templates/sidebar.jsp" /> 
        <!-- Navbar -->
        <jsp:include page="assets/templates/header.jsp" /> 
        <!-- Main -->
        <main class="main">
            <div class="admin-top">
                <h1 class="admin__heading">Customer</h1>
                <form action="admin.jsp" method="POST" class="search-bar d-flex admin__search-bar">
                    <input
                        type="text"
                        name="data-search"
                        id=""
                        value=""
                        placeholder="Search for item"
                        class="search-bar__input"
                        />
                    <button class="search-bar__submit">
                        <img src="./assets/icons/search.svg" alt="" class="search-bar__icon icon" />
                    </button>
                </form>
                <a href="customer-add.jsp" class="admin__add-btn">+ Add Customer</a>
            </div>
            <%
                if (customerList == null) {
            %>

            <div style="font-size: 3rem; font-weight: 700; display: flex; align-items: center; justify-content: center">
                Empty
            </div> 
            <%} else {
//            if(!userListSearch.isEmpty()) {
//            customerList = userListSearch}
//            if(!userListSearch.isEmpty()) {
//            customerList = userListSearch;
            %>
            <table class="table">
                <thead class="table__head">
                    <tr>
                        <th class="table__heading">Name</th>
                        <th class="table__heading">Email</th>
                        <th class="table__heading">Location</th>
                        <th class="table__heading">Phone</th>
                        <th class="table__heading">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (User u : customerList) {
                    %>
                    <tr>
                        <td>
                            <div class="table__user">
                                <%
                                    String url;
                                    String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                                    + request.getContextPath();
                                    String URL = u.getAvatar();
                                    if (URL != null) {
                                        url = root + "/assets/img/avatar/" + URL;
                                    } else
                                        url = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";
                                %>
                                <img
                                    src="<%=url%>"
                                    alt=""
                                    class="table__user-avatar"
                                    />
                                <div class="table__user-info">
                                    <span class="table__user-name"><%=u.getFullName()%></span>
                                </div>
                            </div>
                        </td>
                        <td>
                            <p class="table__data"><%=u.getEmail()%></p>
                        </td>
                        <td>
                            <p class="table__data"><%=u.getAddress()%></p>
                        </td>

                        <td>
                            <p class="table__data"><%=u.getPhone()%></p>
                        </td>
                        <td>
                            <div class="table__act">
                                <a href="customer-update.jsp?id=<%=u.getId()%>" class="table__act-btn table__act-btn-edit" title="edit">
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

                                <button  class="table__act-btn table__act-btn-remove js-toggle" data-id="<%=u.getId()%>" id="btn-del" toggle-target="#delete-confirm"  title="remove">
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
                            </div>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <%} if(customerList != null && !customerList.isEmpty()) {%>
            <div class="paging">
                <div class="paging-info">
                    Showing <span class="paging-start"><%=itemStart%></span> to <span class="paging-end"><%=itemEnd%></span> of <span class="paging-total"><%=pCount%></span> entries
                </div>
                <ul class="paging__list">
                    <li class="paging__item <%=(index > 1) ? "" : " paging__item--disable"%>">
                        <a class="paging__link" href="admin.jsp?index=1"><svg class="icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M41.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L109.3 256 246.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-160 160zm352-160l-160 160c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L301.3 256 438.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0z"/></svg></a>
                    </li>
                    <li class="paging__item <%=(index > 1) ? "" : " paging__item--disable"%>">
                        <a class="paging__link" href="admin.jsp?index=<%=index - 1%>"><img src="./assets/icons/previous.svg" class="icon" alt=""></a>
                    </li>
                    <li class="paging__item  paging__item--active"><a class="paging__link"><%=index%></a></li>
                    <li class="paging__item <%=(index < endPage) ? "" : " paging__item--disable"%>">
                        <a class="paging__link" href="admin.jsp?index=<%=index + 1%>"><img src="./assets/icons/next.svg" class="icon" alt=""></a>
                    </li>
                    <li class="paging__item <%=(index < endPage) ? "" : " paging__item--disable"%>">
                        <a class="paging__link" href="admin.jsp?index=<%=endPage%>"><svg class="icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M470.6 278.6c12.5-12.5 12.5-32.8 0-45.3l-160-160c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L402.7 256 265.4 393.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0l160-160zm-352 160l160-160c12.5-12.5 12.5-32.8 0-45.3l-160-160c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L210.7 256 73.4 393.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0z"/></svg></a>
                    </li>
                </ul>
            </div>
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
                window.location.href = 'customer-delete?id=' + curID;
        }
    </script>
</html>

