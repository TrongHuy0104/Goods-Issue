<%-- 
    Document   : admin
    Created on : Nov 21, 2023, 4:14:02 PM
    Author     : Trong Huy
--%>

<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="org.apache.commons.fileupload.FileItem" %>
<%@page import="org.apache.commons.fileupload.FileUploadException" %>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UserDAO userDao = new UserDAO();
    List<User> userList = userDao.selectAllCustomer();
    User user = (User) request.getSession().getAttribute("admin");
    User tempUser = new User();
    User currentUser = new User();
    String id = request.getParameter("id");
    tempUser.setId(id);
    currentUser = userDao.selectById(tempUser);
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
        <%     
               String uID= currentUser.getId();
               
               String name= currentUser.getFullName();
		
               String gender = currentUser.getGender();
		
               String address= currentUser.getAddress();
		
               String email= currentUser.getEmail();
		
               String phone= currentUser.getPhone();
		
               String deliveryAddress= currentUser.getDeliveryAddress();
               
               String avatar = currentUser.getAvatar();
		
        %>
        <!-- Sidebar -->
        <div class="admin-sidebar">
            <!-- Logo -->
            <a href="admin.jsp" class="logo top-bar__logo admin-logo">
                <img src="./assets/icons/logo.svg" alt="grocerymart" class="logo__img top-bar__logo-img" />
                <h1 class="logo__title top-bar__logo-title">grocerymart</h1>
            </a>
            <h3 class="sidebar__heading">APPLICATION</h3>
            <ul class="sidebar__list">
                <li class="sidebar__item">
                    <a href="dashboard.jsp" class="sidebar__link">
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

                        Dashboard</a
                    >
                </li>
                <li class="sidebar__item sidebar__item--active">
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

                        Customer</a
                    >
                </li>
                <li class="sidebar__item">
                    <a href="storage.jsp" class="sidebar__link">
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

                        Storage</a
                    >
                </li>
                <li class="sidebar__item">
                    <a href="product.jsp" class="sidebar__link">
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
                    <a href="#!" class="sidebar__link">
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
        <jsp:include page="assets/templates/header.jsp" /> 
        <!-- Main -->
        <main class="auth main">

            <!-- Auth Content -->
            <div id="auth-content" class="auth__content">
                <div class="auth__content-inner admin-content-inner">
                    <p class="form__error">${error == "" ? "" : error}</p>
                    <p class="form__note">${note == "" ? "" : note}</p>
                    <form
                        action="customer-update"
                        method="POST"
                        enctype="multipart/form-data"
                        class="form auth__form row row-cols-2 row-cols-md-1 gx-5"
                        id="sign-up-form"
                        >

                        <input type="hidden" name="id" value="<%=uID%>"/>
                        <div class="col">
                            <p class="form__title">User Information</p>
                            <div class="form__group">
                                <label for="fullname" class="form__label">Full name</label>
                                <div class="form__text-input">
                                    <input
                                        id="fullname"
                                        type="text"
                                        placeholder="Name"
                                        class="form__input"
                                        name="name"
                                        value="<%=name%>"
                                        rules="required"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <label for="gender" class="form__label">Gender</label>
                                <div class="gender-container">
                                    <div class="gender-group">
                                        <label>
                                            <input
                                                name="gender"
                                                type="radio"
                                                rules="required"
                                                value="male"
                                                <%=(gender.equals("male"))?"checked":"" %>
                                                class="form__input"
                                                />
                                            Male
                                        </label>
                                    </div>
                                    <div class="gender-group">
                                        <label>
                                            <input
                                                name="gender"
                                                type="radio"
                                                rules="required"
                                                value="female"
                                                <%=(gender.equals("female"))?"checked":"" %>
                                                class="form__input"
                                                />Female
                                        </label>
                                    </div>
                                    <div class="gender-group">
                                        <label>
                                            <input
                                                name="gender"
                                                type="radio"
                                                rules="required"
                                                value="other"
                                                <%=(gender.equals("other"))?"checked":"" %>
                                                class="form__input"
                                                />Other
                                        </label>
                                    </div>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group admin-upload-image">
                                <label for="avatar" class="form__label ">Avatar</label>
                                <input id="avatar" name="avatar" type="file" class="form__input" 
                                       accept="image/png, image/gif, image/jpeg" hidden>
                                <label for="avatar">
                                    <%
                                    String url;
                                    String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                                    + request.getContextPath();
                                    String URL = currentUser.getAvatar();
                                        if (URL != null) {
                                        url = root + "/assets/img/avatar/" + URL;
                                    } else url = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";
                                    %>
                                    <img
                                        id="preview"
                                        class="avatar-preview"
                                        src="<%=url%>"
                                        alt=""
                                        />
                                </label>
                                <span class="form__message"></span>
                            </div> 
                        </div>
                        <div class="col">
                            <p class="form__title">Address</p>
                            <div class="form__group">
                                <label for="address" class="form__label">Address</label>
                                <div class="form__text-input">
                                    <input
                                        id="address"
                                        type="text"
                                        placeholder="Address"
                                        class="form__input"
                                        name="address"
                                        value="<%=address%>"
                                        rules="required"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <label for="delivery-address" class="form__label">Delivery address</label>
                                <div class="form__text-input">
                                    <input
                                        id="delivery-address"
                                        type="text"
                                        placeholder="Delivery Address"
                                        class="form__input"
                                        name="delivery-address"
                                        value="<%=deliveryAddress%>"
                                        rules="required"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <label for="phone" class="form__label">Phone</label>
                                <div class="form__text-input">
                                    <input
                                        id="phone"
                                        type="text"
                                        placeholder="Phone number"
                                        class="form__input"
                                        name="phone"
                                        value="<%=phone%>"
                                        rules="required|phone"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>

                            <div class="form__group">
                                <label for="email" class="form__label">Email</label>
                                <div class="form__text-input">
                                    <input
                                        id="email"
                                        type="email"
                                        placeholder="Email"
                                        class="form__input"
                                        name="email"
                                        value="<%=email%>"
                                        rules="required|email"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group auth__btn-group">
                                <button class="btn btn--primary auth__btn form__submit-btn">Update</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </main>
        <%}%>                                
        <script>
            window.dispatchEvent(new Event("template-loaded"));
        </script>
        <script src="./assets/js/validator.js"></script>
        <script>
            new Validator("#sign-up-form");
        </script>
        <script>
            let avatar = document.querySelector("#preview");
            let file = document.querySelector("#avatar");
            function updateImg() {
                avatar.src = window.URL.createObjectURL(file.files[0]);
            }
            file.onchange = function () {
                updateImg();
            };
        </script>
    </body>
</html>

