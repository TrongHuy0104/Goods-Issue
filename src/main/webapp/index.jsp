<%-- 
    Document   : index
    Created on : Jan 15, 2024, 9:43:47 PM
    Author     : Trong Huy
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Sign In | Grocery Mart</title>
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
        <!-- Styles -->
        <link rel="stylesheet" href="./assets/css/main.css" />
        <!-- Scripts -->
        <script src="./assets/js/scripts.js"></script>
        <style>
            .form__error, .form__note {
                font-size: 1.6rem;
                color: red;
                margin-top: 20px;
                font-weight: 500;
                align-self: flex-start;
                margin-left: 15px;
            }
            
            .form__note {
                color: green;
            }
        </style>
    </head>
    <body>
        <!-- Main -->
        <main class="auth">
            <!-- Auth Intro -->
            <div class="auth__intro d-md-none">
                <img src="./assets/icons/intro.svg" alt="" class="auth__intro-img" />
                <p class="auth__intro-text">
                    The best of luxury brand values, high quality products, and innovative services
                </p>
            </div>
            <!-- Auth Content -->
            <div class="auth__content">
                <div class="auth__content-inner">
                    <!-- Logo -->
                    <a href="index.jsp" class="logo">
                        <img src="./assets/icons/logo.svg" alt="grocerymart" class="logo__img top-bar__logo-img" />
                        <h2 class="logo__title top-bar__logo-title">grocerymart</h2>
                    </a>
                    <h1 class="auth__heading">Hello Again!</h1>
                    <p class="auth__desc">
                        Welcome back to sign in. As a returning customer, you have access to your previously saved all
                        information.
                    </p>
                    <p class="form__error">${error == "" ? "" : error}</p>
                    <p class="form__note">${note == "" ? "" : note}</p>
                    <form action="user" class="form auth__form" method="POST" id="sign-up-form">
                        <input type="hidden" name="action" value="sign-in"/>
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
                                <img src="./assets/icons/letter.svg" alt="" class="form__input-icon" />
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
                                <img src="./assets/icons/eye.svg" alt="" class="form__input-icon" id="togglePassword" />
                            </div>
                            <span class="form__message"></span>
                        </div>
                        <div class="form__group form__group--inline">
                            <label class="form__checkbox">
                                <input type="checkbox" name="" id="" class="form__checkbox-input d-none" />
                                <span class="form__checkbox-label">Set as default card</span>
                            </label>
                            <a href="./reset-password.jsp" class="auth__link form__pull-right" previewlistener="true"
                               >Forgot password?</a
                            >
                        </div>
                        <div class="form__group auth__btn-group">
                            <button class="btn btn--primary auth__btn form__submit-btn">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
        <script>
            window.dispatchEvent(new Event("template-loaded"));
        </script>
        <script src="./assets/js/validator.js"></script>
        <script>
            new Validator("#sign-up-form");
        </script>
        <script src="assets/js/theme.js" type="text/javascript"></script>
    </body>
</html>


