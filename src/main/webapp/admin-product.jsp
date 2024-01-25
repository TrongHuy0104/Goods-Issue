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
    ProductDAO Dao = new ProductDAO();
    List<Product> productList = Dao.selectAll();
    User user = (User) request.getSession().getAttribute("admin");
    String dataSearch = request.getParameter("data");
    if(dataSearch == null){
        dataSearch = "";
    }
//    String searchValue = request.setAttribute("data");
    List<Product> productListSearch = Dao.searchAllByName(dataSearch);
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
        if(user == null) {
        %>
        <h3 style='color:crimson; font-size: 30px; font-weight: 500; text-align: center'>You are not logged into the system! <a href='index.jsp'>Sign In</a></h3>")
        <%} else {%>   
        <!-- Sidebar -->
        <div class="admin-sidebar">
            <!-- Logo -->
            <a href="customer.jsp" class="logo top-bar__logo admin-logo">
                <img src="./assets/icons/logo.svg" alt="grocerymart" class="logo__img top-bar__logo-img" />
                <h1 class="logo__title top-bar__logo-title">grocerymart</h1>
            </a>
            <h3 class="sidebar__heading">APPLICATION</h3>
            <ul class="sidebar__list">
                <li class="sidebar__item">
                    <a href="customer.jsp" class="sidebar__link">
                        <svg
                            fill="rgb(143, 159, 188)"
                            xmlns="http://www.w3.org/2000/svg"
                            height="1em"
                            viewBox="0 0 448 512"
                            >
                        <path
                            d="M304 128a80 80 0 1 0 -160 0 80 80 0 1 0 160 0zM96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM49.3 464H398.7c-8.9-63.3-63.3-112-129-112H178.3c-65.7 0-120.1 48.7-129 112zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3z"
                            />
                        </svg>

                        Customer</a
                    >
                </li>
                <li class="sidebar__item ">
                    <a href="admin.jsp" class="sidebar__link">
                        <svg
                            fill="rgb(143, 159, 188)"
                            xmlns="http://www.w3.org/2000/svg"
                            height="1em"
                            viewBox="0 0 448 512"
                            >
                        <path
                            d="M304 128a80 80 0 1 0 -160 0 80 80 0 1 0 160 0zM96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM49.3 464H398.7c-8.9-63.3-63.3-112-129-112H178.3c-65.7 0-120.1 48.7-129 112zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3z"
                            />
                        </svg>

                        Admin</a
                    >
                </li>
                <li class="sidebar__item sidebar__item--active">
                    <a href="admin-product.jsp" class="sidebar__link">
                        <svg
                            fill="rgb(143, 159, 188)"
                            xmlns="http://www.w3.org/2000/svg"
                            height="1em"
                            viewBox="0 0 512 512"
                            >
                        <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                        <path
                            d="M326.3 218.8c0 20.5-16.7 37.2-37.2 37.2h-70.3v-74.4h70.3c20.5 0 37.2 16.7 37.2 37.2zM504 256c0 137-111 248-248 248S8 393 8 256 119 8 256 8s248 111 248 248zm-128.1-37.2c0-47.9-38.9-86.8-86.8-86.8H169.2v248h49.6v-74.4h70.3c47.9 0 86.8-38.9 86.8-86.8z"
                            />
                        </svg>

                        Product</a
                    >
                </li>
                <li class="sidebar__item">
                    <a href="admin-order.jsp" class="sidebar__link">
                        <svg
                            fill="rgb(143, 159, 188)"
                            xmlns="http://www.w3.org/2000/svg"
                            height="1em"
                            viewBox="0 0 576 512"
                            >
                        <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                        <path
                            d="M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z"
                            />
                        </svg>

                        Order</a
                    >
                </li>
            </ul>
        </div>
        <!-- Navbar -->
        <nav class="admin-navbar">
            <ul class="admin-navbar__list">
                <li class="admin-navbar__item">
                    <a href="#!" class="admin-navbar__link">
                        <img
                            src="https://templates.iqonic.design/datum/html/assets/images/user/1.jpg"
                            alt=""
                            class="admin-navbar__img"
                            />
                        <span class="admin-navbar__text"><%=user.getFullName()%></span>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- Main -->
        <main class="main">
            <div class="admin-top">
                <h1 class="admin__heading">Product</h1>
                <form action="admin-product.jsp" method="POST" class="search-bar d-flex admin__search-bar">
                    <input
                        type="text"
                        name="data"
                        id=""
                        value="<%if(dataSearch == null) {
                            dataSearch = "";
                        } else {
                            dataSearch = dataSearch;
                        }%><%=dataSearch%>"
                        placeholder="Search for item"
                        class="search-bar__input"
                        />
                    <button class="search-bar__submit">
                        <img src="./assets/icons/search.svg" alt="" class="search-bar__icon icon" />
                    </button>
                </form>
                <a href="product-add.jsp" class="admin__add-btn">+ Add Product</a>
            </div>
            <%
            if(productList == null) {
            %>

            <div style="font-size: 3rem; font-weight: 700; display: flex; align-items: center; justify-content: center">
                Empty
            </div> 
            <%} else {
            if(!productListSearch.isEmpty()) {
            productList = productListSearch;
            }
            %>
            <table class="table">
                <thead class="table__head">
                    <tr>
                        <th class="table__heading">Product Name</th>
                        <th class="table__heading">Category</th>
                        <th class="table__heading">Price</th>
                        <th class="table__heading">Quantity</th>
                        <th class="table__heading">Inventory</th>
                        <th class="table__heading">Store</th>
                        <th class="table__heading">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for(Product p : productList) {
                    %>
                    <tr>
                        <td>
                            <div class="table__user">
                                <%
                                    String url;
                                    String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                                    + request.getContextPath();
                                    String avatarURL= p.getpThumb();
                                        if (avatarURL != null) {
                                        url = root + "/assets/img/product/" + avatarURL;
                                    } else url = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";
                                %>
                                <img
                                    src="<%=url%>"
                                    alt=""
                                    class="table__user-product"
                                    />
                                <div class="table__user-info">
                                    <span class="table__user-name"><%=p.getpName()%></span>
                                </div>
                            </div>
                        </td>
                        <td>
                            <p class="table__data"><%=p.getpCategory()%></p>
                        </td>
                        <td>
                            <p class="table__data">$<%=p.getpPrice()%></p>
                        </td>

                        <td>
                            <p class="table__data"><%=p.getpNumberLeft()%></p>
                        </td>
                        <td>
                            <%
                                System.out.println(p.getpStatus());
                            if( p.getpNumberLeft() == 0) {
                            %>
                            <p class="table__data" style="color: red; font-weight: 500">Out Of Stock</p>
                            <%} else if (p.getpStatus() == 1 || (p.getpNumberLeft() > 0 && p.getpNumberLeft() <= 10)) {%>
                            <p class="table__data" style="color: #db7e06; font-weight: 500">Limited</p>
                            <% } else {%>
                            <p class="table__data" style="color: #3cb72c; font-weight: 500">In Stock</p>
                            <%}%>
                        </td>
                        <td>
                            <p class="table__data"><%=p.getsId()%></p>
                        </td>
                        <td>
                            <div class="table__act">
                                <a href="product-update.jsp?id=<%=p.getpId()%>" class="table__act-btn table__act-btn-edit" title="edit">
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


                                <a href="ProductDeleteControl?id=<%=p.getpId()%>" onclick="confirmRemove(<%=p.getpId()%>)" class="table__act-btn table__act-btn-remove"  title="remove">
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
                                </a>
                            </div>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <%}%>
        </main>
        <%}%>  
    </body>

    <script>
        function confirmRemove(id) {
            var option = confirm("Are you sure to remove!");
            if (option === true) {
                window.location.href = 'ProductDeleteControl?id=' + id;
            }
        }
    </script>
</html>

