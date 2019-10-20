<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.09.2019
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-12 col-sm-6 col-md-6 col-lg-4 col-xl-3">
            <div class="card mb-4">
                <div class="card-body">
                    <div class="my-image" style="background-image: url('/controller/loadFile?fileName=${manga.imgURL}');
                            background-size: cover; background-position: center;"></div>
                    <h5 class="text-center mb-0 mt-2">${manga.name}</h5>
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-header">
                    <jsp:include page="/img/svg/details.svg"/>
                    <fmt:message key="key.details" bundle="${lang}"/>
                </div>
                <div class="card-body">
                    <fmt:message key="key.genres" bundle="${lang}"/>:
                    <c:forEach items="${manga.genreList}" var="genre">
                        <a href="/controller/showGenre?genreId=${genre.id}" class="badge badge-primary mb-1"
                           style="background-color: rgb(${genre.id}0,16,240)">
                            <jsp:include page="/img/svg/genre.svg"/>
                            ${genre.name}
                        </a>
                    </c:forEach>
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-header">
                    <jsp:include page="/img/svg/creators.svg"/>
                    <fmt:message key="key.creators" bundle="${lang}"/>
                </div>
                <div class="card-body">
                    <c:forEach items="${manga.artistList}" var="artist">
                        <div class="d-flex justify-content-between mb-1">
                            <a href="/controller/showArtist?artistId=${artist.id}" class="my-color">
                                ${artist.name}
                            </a>
                            <div class="badge badge-primary">
                                <fmt:message key="key.artist" bundle="${lang}"/>
                            </div>
                        </div>
                    </c:forEach>
                    <c:forEach items="${manga.authorList}" var="author">
                        <div class="d-flex justify-content-between mb-1">
                            <a href="/controller/showAuthor?authorId=${author.id}" class="my-color">
                                ${author.name}
                            </a>
                            <div class="badge badge-primary">
                                <fmt:message key="key.author" bundle="${lang}"/>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-6 col-lg-8 col-xl-9">
            <div class="card mb-4">
                <div class="card-header">
                    <jsp:include page="/img/svg/description.svg"/>
                    <fmt:message key="key.description" bundle="${lang}"/>
                </div>
                <div class="card-body">
                    ${manga.description}
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-header">
                    <jsp:include page="/img/svg/chapter.svg"/>
                    <fmt:message key="key.chapters" bundle="${lang}"/>
                </div>
                <div class="card-body">
                    <c:forEach items="${manga.chapterList}" var="chapter">
                        <a href="/controller/showChapter?chapterId=${chapter.id}" class="btn btn-block btn-outline-primary">
                            ${chapter.name}
                        </a>
                    </c:forEach>
                    <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                        <a href="/controller/editChapter?mangaId=${manga.id}&attribute=chapter&action=add"
                           class="btn btn-block btn-success">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.chapter" bundle="${lang}"/>
                        </a>
                    </custom:checkAcces>
                </div>
            </div>
            <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="USER">
                <c:choose>
                    <c:when test="${manga.userList.contains(authorizedUser)}">
                        <div class="my-4">
                            <a href="/controller/editFavorite?action=delete&mangaId=${manga.id}&userId=${authorizedUser.id}"
                               class="btn btn-danger btn-block">
                                <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.from" bundle="${lang}"/> <fmt:message key="key.favorite" bundle="${lang}"/>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="my-4">
                            <a href="/controller/editFavorite?action=add&mangaId=${manga.id}&userId=${authorizedUser.id}"
                               class="btn btn-success btn-block">
                                <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.to" bundle="${lang}"/> <fmt:message key="key.favorite" bundle="${lang}"/>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </custom:checkAcces>
            <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
            <div class="card">
                <div class="card-header text-center">
                    <h5 class="mb-0">
                        <fmt:message key="key.settings" bundle="${lang}"/>
                    </h5>
                </div>
                <div class="card-body">
                    <c:if test="${param.error != null}">
                        <div role="alert" class="alert alert-danger">
                            <fmt:message key="${param.error}" bundle="${lang}"/>
                        </div>
                    </c:if>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.name" bundle="${lang}"/>
                    </h6>
                    <form action="/controller/editManga?attribute=mangaName&mangaId=${manga.id}" method="post" class="row">
                        <div class="form-group input-group col-8">
                            <input type="text" class="form-control" name="mangaName"
                                   required pattern="^[^<>]{1,100}$"
                                   placeholder="<fmt:message key="key.name" bundle="${lang}"/>"
                                   value="${manga.name}">
                        </div>
                        <div class="col-4">
                            <input type="submit" class="btn btn-primary btn-block"
                                   value="<fmt:message key="key.save" bundle="${lang}"/>">
                        </div>
                    </form>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.description" bundle="${lang}"/>
                    </h6>
                    <form action="/controller/editManga?attribute=mangaDescription&mangaId=${manga.id}" method="post" class="row">
                        <div class="form-group input-group col-8">
                            <textarea required pattern="^[^<>]{1,1000}$" name="mangaDescription" class="form-control">${manga.description}</textarea>
                        </div>
                        <div class="col-4">
                            <input type="submit" class="btn btn-primary btn-block"
                                   value="<fmt:message key="key.save" bundle="${lang}"/>">
                        </div>
                    </form>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.image" bundle="${lang}"/>
                    </h6>
                    <form action="/controller/editManga?attribute=mangaImg&mangaId=${manga.id}" method="post" enctype="multipart/form-data" class="row">
                        <div class="form-group input-group col-8">
                            <input type="file" class="custom-file-input d-none" name="mangaImg" id="img" accept="image/*" required>
                            <label for="img" class="custom-file-label form-control">
                                ${manga.imgURL}
                            </label>
                        </div>
                        <div class="col-4">
                            <input type="submit" class="btn btn-primary btn-block"
                                   value="<fmt:message key="key.save" bundle="${lang}"/>">
                        </div>
                    </form>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.genre" bundle="${lang}"/>
                    </h6>
                    <div class="col-12 mb-3">
                        <h6 class="card-title">
                            <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.genre" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${manga.genreList}" var="genre">
                            <a href="/controller/showGenre?genreId=${genre.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/genre.svg"/>
                                    ${genre.name}
                                <a href="/controller/editManga?attribute=mangaGenre&action=delete&mangaId=${manga.id}&genreId=${genre.id}"
                                   class="btn btn-danger mb-1 btn-sm">x</a>
                            </a>
                        </c:forEach>
                        <h6 class="card-title mt-2">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.genre" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${genreList}" var="genre">
                            <a href="/controller/showGenre?genreId=${genre.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/genre.svg"/>
                                    ${genre.name}
                                <a href="/controller/editManga?attribute=mangaGenre&action=add&mangaId=${manga.id}&genreId=${genre.id}"
                                   class="btn btn-success mb-1 btn-sm">+</a>
                            </a>
                        </c:forEach>
                    </div>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.artist" bundle="${lang}"/>
                    </h6>
                    <div class="col-12 mb-3">
                        <h6 class="card-title">
                            <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.artist" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${manga.artistList}" var="artist">
                            <a href="/controller/showArtist?artistId=${artist.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/artist.svg"/>
                                    ${artist.name}
                                <a href="/controller/editManga?attribute=mangaArtist&action=delete&mangaId=${manga.id}&artistId=${artist.id}"
                                   class="btn btn-danger mb-1 btn-sm">x</a>
                            </a>
                        </c:forEach>
                        <h6 class="card-title mt-2">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.artist" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${artistList}" var="artist">
                            <a href="/controller/showArtist?artistId=${artist.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/artist.svg"/>
                                    ${artist.name}
                                <a href="/controller/editManga?attribute=mangaArtist&action=add&mangaId=${manga.id}&artistId=${artist.id}"
                                   class="btn btn-success mb-1 btn-sm">+</a>
                            </a>
                        </c:forEach>
                    </div>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.author" bundle="${lang}"/>
                    </h6>
                    <div class="col-12 mb-3">
                        <h6 class="card-title">
                            <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.author" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${manga.authorList}" var="author">
                            <a href="/controller/showAuthor?authorId=${author.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/author.svg"/>
                                    ${author.name}
                                <a href="/controller/editManga?attribute=mangaAuthor&action=delete&mangaId=${manga.id}&authorId=${author.id}"
                                   class="btn btn-danger mb-1 btn-sm">x</a>
                            </a>
                        </c:forEach>
                        <h6 class="card-title mt-2">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.author" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${authorList}" var="author">
                            <a href="/controller/showAuthor?authorId=${author.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/author.svg"/>
                                    ${author.name}
                                <a href="/controller/editManga?attribute=mangaAuthor&action=add&mangaId=${manga.id}&authorId=${author.id}"
                                   class="btn btn-success mb-1 btn-sm">+</a>
                            </a>
                        </c:forEach>
                    </div>
                    <h6 class="card-title">
                        <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.favorite" bundle="${lang}"/>
                    </h6>
                    <div class="col-12 mb-3">
                        <h6 class="card-title">
                            <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.from" bundle="${lang}"/> <fmt:message key="key.favorite" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${manga.userList}" var="user">
                            <a href="/controller/showUser?userId=${user.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/name.svg"/>
                                    ${user.name}
                                <a href="/controller/editFavorite?action=delete&mangaId=${manga.id}&userId=${user.id}"
                                   class="btn btn-danger mb-1 btn-sm">x</a>
                            </a>
                        </c:forEach>
                        <h6 class="card-title mt-2">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.to" bundle="${lang}"/> <fmt:message key="key.favorite" bundle="${lang}"/>
                        </h6>
                        <c:forEach items="${userList}" var="user">
                            <a href="/controller/showUser?userId=${user.id}" class="btn btn-primary mb-1 btn-sm">
                                <jsp:include page="/img/svg/name.svg"/>
                                    ${user.name}
                                <a href="/controller/editFavorite?action=add&mangaId=${manga.id}&userId=${user.id}"
                                   class="btn btn-success mb-1 btn-sm">+</a>
                            </a>
                        </c:forEach>
                        <div class="mt-4">
                            <a href="/controller/editManga?attribute=manga&action=delete&mangaId=${manga.id}" class="btn btn-danger btn-block">
                                <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.manga" bundle="${lang}"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            </custom:checkAcces>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>



