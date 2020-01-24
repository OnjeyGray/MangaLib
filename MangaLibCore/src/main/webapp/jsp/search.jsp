<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-12 mb-2">
            <form action="/controller/showSearch" method="post">
                <c:if test="${param.error != null}">
                    <div role="alert" class="alert alert-danger">
                        <fmt:message key="${param.error}" bundle="${lang}"/>
                    </div>
                </c:if>
                <div class="form-group input-group w-100">
                    <div class="input-group-prepend w-100">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/search.svg"/>
                                </span>
                        <input type="text" class="form-control my-form" name="search"
                               required pattern="^[^<>]{1,100}$"
                               placeholder="<fmt:message key="key.enterKeyword" bundle="${lang}"/>">
                    </div>
                </div>
            </form>
        </div>
        <c:if test="${mangaList[0] != null}">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <jsp:include page="/img/svg/manga.svg"/>
                        <fmt:message key="key.manga" bundle="${lang}"/>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${mangaList}" var="manga">
                        <div class="col-6 col-sm-4 col-md-3 col-lg-2 p-2">
                            <div class="card">
                                <div class="card-body">
                                    <a href="/controller/showManga?mangaId=${manga.id}">
                                        <div class="my-image" style="background-image: url('/controller/loadFile?fileName=${manga.imgURL}');
                                                background-size: cover; background-position: center;">
                                        </div>
                                    </a>
                                    <h6 class="text-truncate mb-0 mt-2">${manga.name}</h6>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${genreList[0] != null}">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <jsp:include page="/img/svg/genre.svg"/>
                        <fmt:message key="key.genre" bundle="${lang}"/>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${genreList}" var="genre">
                        <div class="col-6 col-sm-4 col-md-3 col-lg-2 p-2">
                            <div class="card">
                                <div class="card-body">
                                    <a href="/controller/showGenre?genreId=${genre.id}">
                                        <h6 class="text-truncate mb-0 mt-2">${genre.name}</h6>
                                    </a>
                                    <div>
                                            ${genre.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${authorList[0] != null}">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <jsp:include page="/img/svg/author.svg"/>
                        <fmt:message key="key.author" bundle="${lang}"/>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${authorList}" var="author">
                        <div class="col-6 col-sm-4 col-md-3 col-lg-2 p-2">
                            <div class="card">
                                <div class="card-body">
                                    <a href="/controller/showAuthor?authorId=${author.id}">
                                        <h6 class="text-truncate mb-0 mt-2">${author.name}</h6>
                                    </a>
                                    <div>
                                            ${author.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${artistList[0] != null}">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <jsp:include page="/img/svg/artist.svg"/>
                        <fmt:message key="key.artist" bundle="${lang}"/>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${artistList}" var="artist">
                        <div class="col-6 col-sm-4 col-md-3 col-lg-2 p-2">
                            <div class="card">
                                <div class="card-body">
                                    <a href="/controller/showArtist?artistId=${artist.id}">
                                        <h6 class="text-truncate mb-0 mt-2">${artist.name}</h6>
                                    </a>
                                    <div>
                                            ${artist.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp"/>