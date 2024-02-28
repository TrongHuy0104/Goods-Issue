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

