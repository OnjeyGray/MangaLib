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
        <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <a href="/controller/editGenre?attribute=genre&action=add">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.genre" bundle="${lang}"/>
                        </a>
                    </div>
                </div>
            </div>
        </custom:checkAcces>
        <c:forEach items="${genreList}" var="genre">
            <div class="col-12 mb-4">
                <div class="card">
                    <div class="card-header">
                        <a href="/controller/showGenre?genreId=${genre.id}" class="btn-link">
                            <jsp:include page="/img/svg/genre.svg"/>
                            ${genre.name}
                        </a>
                    </div>
                    <div class="card-body">
                        <div class="d-flex auto-x">
                            <c:forEach items="${genre.mangaList}" var="manga">
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
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="footer.jsp"/>



