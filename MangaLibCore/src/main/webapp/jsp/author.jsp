<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.09.2019
  Time: 10:16
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
                <div class="card-header">
                    <jsp:include page="/img/svg/author.svg"/>
                    ${author.name}
                </div>
                <div class="card-body">
                    ${author.description}
                </div>
            </div>
        </div>
        <div class="col-12 col-sm-6 col-md-6 col-lg-8 col-xl-9">
            <div class="d-flex auto-x">
                <c:forEach items="${author.mangaList}" var="manga">
                    <div class="col-6 col-lg-3 p-1">
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
            <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                <div class="card mt-4">
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
                        <form action="/controller/editAuthor?attribute=authorName&authorId=${author.id}" method="post" class="row">
                            <div class="form-group input-group col-8">
                                <input type="text" class="form-control" name="authorName"
                                       required pattern="^[^<>]{1,100}$"
                                       placeholder="<fmt:message key="key.name" bundle="${lang}"/>"
                                       value="${author.name}">
                            </div>
                            <div class="col-4">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <h6 class="card-title">
                            <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.description" bundle="${lang}"/>
                        </h6>
                        <form action="/controller/editAuthor?attribute=authorDescription&authorId=${author.id}" method="post" class="row">
                            <div class="form-group input-group col-8">
                                <textarea required pattern="^[^<>]{1,1000}$" name="authorDescription"
                                          placeholder="<fmt:message key="key.description" bundle="${lang}"/>"
                                          class="form-control">${author.description}</textarea>
                            </div>
                            <div class="col-4">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <div class="mt-4">
                            <a href="/controller/editAuthor?attribute=author&action=delete&authorId=${author.id}" class="btn btn-danger btn-block">
                                <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.author" bundle="${lang}"/>
                            </a>
                        </div>
                    </div>
                </div>
            </custom:checkAcces>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>



