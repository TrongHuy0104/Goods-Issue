<%-- 
    Document   : admin
    Created on : Nov 21, 2023, 4:14:02 PM
    Author     : Trong Huy
--%>


<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UserDAO userDao = new UserDAO();
    List<User> userList = userDao.selectAllCustomer();
    User user = (User) request.getSession().getAttribute("admin");
    
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
        <main class="auth main">

            <!-- Auth Content -->
            <div id="auth-content" class="auth__content">
                <div class="auth__content-inner admin-content-inner">
                    <p class="form__error">${error == "" ? "" : error}</p>
                    <p class="form__note">${note == "" ? "" : note}</p>
                    <p class="form__title">ADD CUSTOMER FORM</p>
                    <form
                        action="customer-add"
                        method="POST"
                        enctype="multipart/form-data"
                        class="form auth__form row row-cols-2 row-cols-md-1 gx-5"
                        id="sign-up-form"
                        >
                        <!--<input type="hidden" name="action" value="add"/>-->
                        <div class="col">
                            <p class="form__title">Account</p>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Username"
                                        class="form__input"
                                        name="username"
                                        value="${username != null ? username : ""}"
                                        rules="required|minLength:2"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16px" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M304 128a80 80 0 1 0 -160 0 80 80 0 1 0 160 0zM96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM49.3 464H398.7c-8.9-63.3-63.3-112-129-112H178.3c-65.7 0-120.1 48.7-129 112zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        id="password"
                                        type="password"
                                        placeholder="Password"
                                        class="form__input"
                                        name="password"
                                        rules="required|minLength:6"
                                        />
                                    <img
                                        src="./assets/icons/eye.svg"
                                        alt=""
                                        class="form__input-icon"
                                        id="togglePassword"
                                        />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        id="password_confirmation"
                                        type="password"
                                        placeholder="Confirm password"
                                        class="form__input"
                                        name="password_confirmation"
                                        rules="required|confirm:#password"
                                        />
                                    <img
                                        src="./assets/icons/eye.svg"
                                        alt=""
                                        class="form__input-icon"
                                        id="togglePasswordConfirm"
                                        />
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <p class="form__title">User Information</p>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Name"
                                        class="form__input"
                                        name="name"
                                        value="${name != null ? name : ""}"
                                        rules="required"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16px" viewBox="0 0 640 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M192 128c0-17.7 14.3-32 32-32s32 14.3 32 32v7.8c0 27.7-2.4 55.3-7.1 82.5l-84.4 25.3c-40.6 12.2-68.4 49.6-68.4 92v71.9c0 40 32.5 72.5 72.5 72.5c26 0 50-13.9 62.9-36.5l13.9-24.3c26.8-47 46.5-97.7 58.4-150.5l94.4-28.3-12.5 37.5c-3.3 9.8-1.6 20.5 4.4 28.8s15.7 13.3 26 13.3H544c17.7 0 32-14.3 32-32s-14.3-32-32-32H460.4l18-53.9c3.8-11.3 .9-23.8-7.4-32.4s-20.7-11.8-32.2-8.4L316.4 198.1c2.4-20.7 3.6-41.4 3.6-62.3V128c0-53-43-96-96-96s-96 43-96 96v32c0 17.7 14.3 32 32 32s32-14.3 32-32V128zm-9.2 177l49-14.7c-10.4 33.8-24.5 66.4-42.1 97.2l-13.9 24.3c-1.5 2.6-4.3 4.3-7.4 4.3c-4.7 0-8.5-3.8-8.5-8.5V335.6c0-14.1 9.3-26.6 22.8-30.7zM24 368c-13.3 0-24 10.7-24 24s10.7 24 24 24H64.3c-.2-2.8-.3-5.6-.3-8.5V368H24zm592 48c13.3 0 24-10.7 24-24s-10.7-24-24-24H305.9c-6.7 16.3-14.2 32.3-22.3 48H616z"/></svg>
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
                                                ${gender == "male" ? "checked" : ""}
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
                                                ${gender == "female" ? "checked" : ""}
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
                                                ${gender == "other" ? "checked" : ""}
                                                class="form__input"
                                                />Other
                                        </label>
                                    </div>
                                </div>
                                <span class="form__message"></span>
                            </div>
                        </div>
                        <div class="col">
                            <p class="form__title">Address</p>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Address"
                                        class="form__input"
                                        name="address"
                                        value="${address != null ? address : ""}"
                                        rules="required"
                                        />
                                    <svg  class="form__input-icon icon"  xmlns="http://www.w3.org/2000/svg" height="16px" viewBox="0 0 384 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Delivery Address"
                                        class="form__input"
                                        name="delivery-address"
                                        value="${delivery-Adress != null ? deliveryAddress : ""}"
                                        rules="required"
                                        /><svg  class="form__input-icon icon"  xmlns="http://www.w3.org/2000/svg" height="16px" viewBox="0 0 384 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Phone number"
                                        class="form__input"
                                        name="phone"
                                        value="${phone != null ? phone : ""}"
                                        rules="required|phone"
                                        />
                                    <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
                                </div>
                                <span class="form__message"></span>
                            </div>

                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="email"
                                        placeholder="Email"
                                        class="form__input"
                                        name="email"
                                        value="${email != null ? email : ""}"
                                        rules="required|email"
                                        />
                                    <svg class="form__input-icon icon" xmlns="http://www.w3.org/2000/svg" height="16px" viewBox="0 0 512 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M164.9 24.6c-7.7-18.6-28-28.5-47.4-23.2l-88 24C12.1 30.2 0 46 0 64C0 311.4 200.6 512 448 512c18 0 33.8-12.1 38.6-29.5l24-88c5.3-19.4-4.6-39.7-23.2-47.4l-96-40c-16.3-6.8-35.2-2.1-46.3 11.6L304.7 368C234.3 334.7 177.3 277.7 144 207.3L193.3 167c13.7-11.2 18.4-30 11.6-46.3l-40-96z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group admin-upload-image">
                                <label for="avatar" class="form__label ">Avatar</label>
                                <input id="avatar" name="avatar" type="file" class="form__input" 
                                       accept="image/png, image/gif, image/jpeg" hidden rules="required">
                                <label for="avatar">
                                    <%
                                        String url = "http://localhost:8080/Goods_Issue/assets/img/avatar/images.png";
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
                            <div class="form__group auth__btn-group">
                                <button class="btn btn--primary auth__btn form__submit-btn">Add</button>
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

