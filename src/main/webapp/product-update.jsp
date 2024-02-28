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
    ProductDAO productDao = new ProductDAO();
    StorageDAO strDao = new StorageDAO();
    List<Storage> strList = strDao.selectAll();
    List<Storage> strList1 = strDao.selectAllActiveStorage();
    List<Product> productList = productDao.selectAll();
    Product tempProduct = new Product();
    Product currentProduct = new Product();
    String id = request.getParameter("id");
    tempProduct.setpId(id);
    currentProduct = productDao.selectById(tempProduct);
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
               String pId = currentProduct.getpId();
               
               String pName = currentProduct.getpName();
		
               String pFrom = currentProduct.getpOrigin();
		
               double pPrice = currentProduct.getpPrice();
		
               double pQuantity = currentProduct.getpNumberLeft();
		
               String pThumb = currentProduct.getpThumb();
		
               String pDesc= currentProduct.getpDescription();
               
               String psId = currentProduct.getsId();
               
               String cId = String.valueOf(currentProduct.getpCateId());
               
		
               
        %>
        <!-- Sidebar -->
        <%
            request.setAttribute("page", "product");
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
                    <p class="form__title">BASIC INFORMATION</p>
                    <form
                        action="updateProduct"
                        method="POST"
                        enctype="multipart/form-data"
                        class="form auth__form row row-cols-2 row-cols-md-1 gx-5"
                        id="sign-up-form"
                        >
                        <input type="hidden" name="id" value="<%=pId%>"/>
                        <div class="col">
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Product Name"
                                        class="form__input"
                                        name="productName"
                                        value="<%=pName%>"
                                        rules="required|minLength:2"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="From"
                                        class="form__input"
                                        name="from"
                                        value="<%=pFrom%>"
                                        rules="required"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="text"
                                        placeholder="Price"
                                        class="form__input"
                                        name="price"
                                        value="<%=pPrice%>"
                                        rules="required|isNonNegative"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group admin-upload-image">
                                <label for="avatar" class="form__label ">Thumb</label>
                                <input id="avatar" name="avatar" type="file" class="form__input" 
                                       accept="image/png, image/gif, image/jpeg" hidden>
                                <label for="avatar">
                                    <%
                                        String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                                        + request.getContextPath();
                                        String url;
                                        if (pThumb != null) url = root + "/assets/img/product/" + pThumb;
                                        else {
                                        url = "http://localhost:8080/Goods_Issue/assets/img/avatar/images.png";}
                                    %>
                                    <img
                                        id="preview"
                                        class="thumb-preview"
                                        src="<%=url%>"
                                        alt=""
                                        />
                                </label>
                                <span class="form__message"></span>
                            </div> 

                        </div>
                        <div class="col">
                            <div class="form__group">
                                <div class="form__text-input">
                                    <input
                                        type="number"
                                        placeholder="Quantity"
                                        class="form__input"
                                        name="quantity"
                                        value="<%=pQuantity %>"
                                        rules="required|integer"
                                        />
                                    <svg class="icon form__input-icon" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"/></svg>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <select name="category" class="form__input"
                                            rules="required">
                                        <option value="" hidden="">Category</option>
                                        <optgroup label="Mobile">
                                            <option value="4" <%=!cId.equals("4") ? "" : "selected"%>>IPhone</option>
                                            <option value="5" <%=!cId.equals("5") ? "" : "selected"%>>Samsung</option>
                                            <option value="6" <%=!cId.equals("6") ? "" : "selected"%>>Oppo</option>
                                            <option value="7" <%=!cId.equals("7") ? "" : "selected"%>>Xiaomi</option>
                                            <option value="8" <%=!cId.equals("8") ? "" : "selected"%>>Vivo</option>
                                            <option value="9" <%=!cId.equals("9") ? "" : "selected"%>>Masstel</option>
                                            <option value="10" <%=!cId.equals("10") ? "" : "selected"%>>Realme</option>
                                            <option value="11" <%=!cId.equals("11") ? "" : "selected"%>>Itel</option>
                                        </optgroup>
                                        <optgroup label="Laptop">
                                            <option value="12" <%=!cId.equals("12") ? "" : "selected"%>>HP</option>
                                            <option value="13" <%=!cId.equals("13") ? "" : "selected"%>>Asus</option>
                                            <option value="14" <%=!cId.equals("14") ? "" : "selected"%>>Acer</option>
                                            <option value="15" <%=!cId.equals("15") ? "" : "selected"%>>Lenovo</option>
                                            <option value="16" <%=!cId.equals("16") ? "" : "selected"%>>Dell</option>
                                            <option value="17" <%=!cId.equals("17") ? "" : "selected"%>>Microsoft</option>
                                            <option value="18" <%=!cId.equals("18") ? "" : "selected"%>>LG</option>
                                            <option value="19" <%=!cId.equals("19") ? "" : "selected"%>>Macbook</option>
                                        </optgroup>
                                        <optgroup label="Tablet">
                                            <option value="20" <%=!cId.equals("20") ? "" : "selected"%>>Razer</option>
                                            <option value="21" <%=!cId.equals("21") ? "" : "selected"%>>ASUS</option>
                                            <option value="22" <%=!cId.equals("22") ? "" : "selected"%>>Ipad</option>
                                            <option value="23" <%=!cId.equals("23") ? "" : "selected"%>>Samsung</option>
                                            <option value="24" <%=!cId.equals("24") ? "" : "selected"%>>Lenovo</option>
                                            <option value="25" <%=!cId.equals("25") ? "" : "selected"%>>Huawei</option>
                                            <option value="26" <%=!cId.equals("26") ? "" : "selected"%>>Microsoft</option>
                                            <option value="27" <%=!cId.equals("27") ? "" : "selected"%>>Sony</option>
                                            <option value="28" <%=!cId.equals("28") ? "" : "selected"%>>Acer</option>
                                        </optgroup>
                                    </select>
                                </div>
                                <span class="form__message"></span>
                            </div>
                            <div class="form__group">
                                <div class="form__text-area form__text-input">
                                    <textarea name="desc" placeholder="Description..." class="form__text-area-input"><%=pDesc%></textarea>
                                    <img src="./assets/icons/form-error.svg" alt="" class="form__input-icon-error">
                                </div>
                            </div>
                            <div class="form__group">
                                <div class="form__text-input">
                                    <select name="store" class="form__input"
                                            rules="required">
                                        <option value="" hidden="">Store</option>
                                        <optgroup label="Store">
                                            
                                            <%
                                                for(Storage s : strList1){
                                            %>
                                            <option value="<%=s.getsID()%>" <%=!psId.equals(s.getsID()) ? "" : "selected"%>><%=s.getsName()%></option>
                                            <%
                                                }
                                            %>
                                        </optgroup>
                                    </select>
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

    </body>
    <script>window.dispatchEvent(new Event("template-loaded"));</script>
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
</html>

