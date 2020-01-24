<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<fmt:setLocale value="${currentLanguage.locale}"/>
<fmt:setBundle basename="locale" var="lang" scope="session"/>

<html>
<head>
    <title>MangaLib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="shortcut icon" href="/img/favicon.gif" type="image/x-icon">
</head>
<body>
<div id="app">
    <nav class="navbar navbar-expand-lg navabar-light bg-white" id="header">
        <div class="container">
            <div class="navbar-nav flex-row d-flex my-nav justify-content-center ">
                <a href="/controller/showMain" class="router-link-active navbar-brand brand-font">
                    MangaLib
                </a>
            </div>
            <div class="navbar-nav collapse navbar-collapse flex-grow-0 order-last order-lg-0" id="navbar">
                <a href="/controller/showMain" class="nav-item nav-link router-link-active">
                    <jsp:include page="/img/svg/main.svg"/>
                    <fmt:message key="key.main" bundle="${lang}"/>
                </a>
                <a href="/controller/showMangaList" class="nav-item nav-link router-link-active">
                    <jsp:include page="/img/svg/manga.svg"/>
                    <fmt:message key="key.manga" bundle="${lang}"/>
                </a>
                <a href="/controller/showGenreList" class="nav-item nav-link router-link-active">
                    <jsp:include page="/img/svg/genre.svg"/>
                    <fmt:message key="key.genre" bundle="${lang}"/>
                </a>
                <a href="/controller/showAuthorList" class="nav-item nav-link router-link-active">
                    <jsp:include page="/img/svg/author.svg"/>
                    <fmt:message key="key.author" bundle="${lang}"/>
                </a>
                <a href="/controller/showArtistList" class="nav-item nav-link router-link-active">
                    <jsp:include page="/img/svg/artist.svg"/>
                    <fmt:message key="key.artist" bundle="${lang}"/>
                </a>
                <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                    <a href="/controller/showUserList" class="nav-item nav-link router-link-active">
                        <jsp:include page="/img/svg/name.svg"/>
                        <fmt:message key="key.user" bundle="${lang}"/>
                    </a>
                </custom:checkAcces>
            </div>
            <div class="navbar-nav flex-row d-flex my-nav float-left">
                <a href="#navbar" class="nav-item btn btn-link d-lg-none" data-toggle="collapse">
                    <jsp:include page="/img/svg/navbar.svg"/>
                </a>
                <a href="/controller/showSearch" class="nav-item btn btn-link ml-auto ml-lg-0" >
                    <jsp:include page="/img/svg/search.svg"/>
                </a>
                <c:choose>
                    <c:when test="${authorizedUser == null}">
                        <a href="/controller/showLogin" class="nav-item btn btn-link">
                            <fmt:message key="key.login" bundle="${lang}"/>
                        </a>
                        <a href="/controller/showRegister" class="nav-item btn btn-primary">
                            <fmt:message key="key.register" bundle="${lang}"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <div class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle pt-1" data-toggle="dropdown">
                                <jsp:include page="/img/svg/name.svg"/>
                            </a>
                            <div class="dropdown-menu">
                                <a href="/controller/showUser?userId=${authorizedUser.id}" class="dropdown-item ">
                                    <label class="my-image-small"
                                           style="background-image: url('/controller/loadFile?fileName=${authorizedUser.avatarURL}');">
                                    </label>
                                    ${authorizedUser.name}
                                </a>
                                <div class="dropdown-divider"></div>
                                <a href="/controller/logout" class="dropdown-item">
                                    <jsp:include page="/img/svg/logout.svg"/>
                                    <fmt:message key="key.logout" bundle="${lang}"/>
                                </a>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>

    <main id="main" class="py-4" style="min-height: 90%">
