<%-- 
    Document   : sidebar
    Created on : Feb 28, 2024, 11:47:08 AM
    Author     : Trong Huy
--%>
<%@page import="java.util.*"%>
<%@page import="goods_issue.model.*" %>
<%@page import="goods_issue.dataAccess.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="admin-sidebar">
    <!-- Logo -->
    <a href="dashboard.jsp" class="logo top-bar__logo admin-logo">
        <img src="./assets/icons/logo.svg" alt="grocerymart" class="logo__img top-bar__logo-img" />
        <h1 class="logo__title top-bar__logo-title">grocerymart</h1>
    </a>
    <div class="${page eq 'dashboard' ? 'sidebar__item sidebar__item--active' : 'sidebar__item'}" >
        <a href="dashboard.jsp" class="sidebar__link">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M3 13h1v7c0 1.103.897 2 2 2h12c1.103 0 2-.897 2-2v-7h1a1 1 0 0 0 .707-1.707l-9-9a.999.999 0 0 0-1.414 0l-9 9A1 1 0 0 0 3 13zm7 7v-5h4v5h-4zm2-15.586 6 6V15l.001 5H16v-5c0-1.103-.897-2-2-2h-4c-1.103 0-2 .897-2 2v5H6v-9.586l6-6z"></path></svg>

            Dashboard</a
        >
    </div>
    <h3 class="sidebar__heading">APPLICATION</h3>
    <ul class="sidebar__list">
        <li class="${page eq 'customer' ? 'sidebar__item sidebar__item--active' : 'sidebar__item'}">
            <a href="admin.jsp" class="sidebar__link">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M12 2a5 5 0 1 0 5 5 5 5 0 0 0-5-5zm0 8a3 3 0 1 1 3-3 3 3 0 0 1-3 3zm9 11v-1a7 7 0 0 0-7-7h-4a7 7 0 0 0-7 7v1h2v-1a5 5 0 0 1 5-5h4a5 5 0 0 1 5 5v1z"></path></svg>

                Customer</a
            >
        </li>
        <li class="${page eq 'storage' ? 'sidebar__item sidebar__item--active' : 'sidebar__item'}">
            <a href="storage.jsp" class="sidebar__link">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M22 5c0-1.654-1.346-3-3-3H5C3.346 2 2 3.346 2 5v2.831c0 1.053.382 2.01 1 2.746V19c0 1.103.897 2 2 2h14c1.103 0 2-.897 2-2v-8.424c.618-.735 1-1.692 1-2.746V5zm-2 0v2.831c0 1.14-.849 2.112-1.891 2.167L18 10c-1.103 0-2-.897-2-2V4h3c.552 0 1 .449 1 1zM10 4h4v4c0 1.103-.897 2-2 2s-2-.897-2-2V4zM4 5c0-.551.448-1 1-1h3v4c0 1.103-.897 2-2 2l-.109-.003C4.849 9.943 4 8.971 4 7.831V5zm6 14v-3h4v3h-4zm6 0v-3c0-1.103-.897-2-2-2h-4c-1.103 0-2 .897-2 2v3H5v-7.131c.254.067.517.111.787.125A3.988 3.988 0 0 0 9 10.643c.733.832 1.807 1.357 3 1.357s2.267-.525 3-1.357a3.988 3.988 0 0 0 3.213 1.351c.271-.014.533-.058.787-.125V19h-3z"></path></svg>
                Storage</a
            >
        </li>
        <li class="${page eq 'product' ? 'sidebar__item sidebar__item--active' : 'sidebar__item'}">
            <a href="paging-search-product" class="sidebar__link">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M13.337 9h-2.838v3h2.838a1.501 1.501 0 1 0 0-3z"></path><path d="M12 2C6.477 2 2 6.477 2 12s4.477 10 10 10 10-4.477 10-10S17.523 2 12 2zm1.337 12h-2.838v3H8.501V7h4.837a3.498 3.498 0 0 1 3.499 3.499 3.499 3.499 0 0 1-3.5 3.501z"></path></svg>

                Product</a
            >
        </li>
        <li class="${page eq 'issues' ? 'sidebar__item sidebar__item--active' : 'sidebar__item'}">
            <a href="PagingSearchIssuesControl" class="sidebar__link">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" style="fill: rgb(143, 159, 188);transform: ;msFilter:;"><path d="M21.822 7.431A1 1 0 0 0 21 7H7.333L6.179 4.23A1.994 1.994 0 0 0 4.333 3H2v2h2.333l4.744 11.385A1 1 0 0 0 10 17h8c.417 0 .79-.259.937-.648l3-8a1 1 0 0 0-.115-.921zM17.307 15h-6.64l-2.5-6h11.39l-2.25 6z"></path><circle cx="10.5" cy="19.5" r="1.5"></circle><circle cx="17.5" cy="19.5" r="1.5"></circle></svg>


                Issues</a
            >
        </li>
    </ul>
</div>
