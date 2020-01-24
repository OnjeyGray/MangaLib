<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <label class="my-image"
                           style="background-image: url('/controller/loadFile?fileName=${user.avatarURL}');
                                   background-position: center; background-size: cover;">
                    </label>
                    <div class="py-1 px-2">
                        <h5 class="mb-0">
                            ${user.name}
                        </h5>
                        <p class="mb-0">
                            ${user.registrationDate}
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="card mb-4">
                <div class="card-header text-center">
                    <h5 class="mb-0">
                        <fmt:message key="key.favorite" bundle="${lang}"/>
                    </h5>
                </div>
                <div class="card-body">
                    <div class="d-flex auto-x">
                        <c:forEach items="${user.mangaList}" var="manga">
                            <div class="col-3 p-1">
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
            <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN"
                               currentId="${authorizedUser.id}" accessId="${user.id}">
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
                        <form action="/controller/editUser?attribute=userName" method="post" class="row">
                            <div class="col-12">
                                <c:if test="${userNameError != null}">
                                    <div role="alert" class="alert alert-danger">
                                        <fmt:message key="${userNameError}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                                <c:if test="${userNameWarning != null}">
                                    <div role="alert" class="alert alert-warning">
                                        <fmt:message key="${userNameWarning}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                                <c:if test="${userNameSuccess != null}">
                                    <div role="alert" class="alert alert-success">
                                        <fmt:message key="${userNameSuccess}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group input-group col-8">
                                <div class="input-group-prepend w-100">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/name.svg"/>
                                </span>
                                    <input type="text" class="form-control my-form" name="userName"
                                           required pattern="^[^<>]{1,100}$"
                                           placeholder="<fmt:message key="key.name" bundle="${lang}"/>"
                                           value="${user.name}">
                                </div>
                            </div>
                            <div class="col-4">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <h6 class="card-title">
                            <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.email" bundle="${lang}"/>
                        </h6>
                        <form action="/controller/editUser?attribute=userEmail" method="post" class="row">
                            <div class="col-12">
                                <c:if test="${userEmailError != null}">
                                    <div role="alert" class="alert alert-danger">
                                        <fmt:message key="${userEmailError}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                                <c:if test="${userEmailWarning != null}">
                                    <div role="alert" class="alert alert-warning">
                                        <fmt:message key="${userEmailWarning}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                                <c:if test="${userEmailSuccess != null}">
                                    <div role="alert" class="alert alert-success">
                                        <fmt:message key="${userEmailSuccess}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group input-group col-8">
                                <div class="input-group-prepend w-100">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/email.svg"/>
                                </span>
                                    <input type="email" class="form-control w-100 my-form" name="userEmail"
                                           required pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$"
                                           placeholder="<fmt:message key="key.email" bundle="${lang}"/>"
                                           value="${user.email}">
                                </div>
                            </div>
                            <div class="col-4">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <h6 class="card-title">
                            <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.password" bundle="${lang}"/>
                        </h6>
                        <form action="/controller/editUser?attribute=userPassword" method="post" class="row">
                            <div class="col-12">
                                <c:if test="${userPasswordWarning != null}">
                                    <div role="alert" class="alert alert-warning">
                                        <fmt:message key="${userPasswordWarning}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                                <c:if test="${userPasswordSuccess != null}">
                                    <div role="alert" class="alert alert-success">
                                        <fmt:message key="${userPasswordSuccess}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group input-group col-8">
                                <div class="input-group-prepend w-100">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/password.svg"/>
                                </span>
                                    <input type="password" class="form-control my-form" name="userPassword"
                                           required pattern="^[0-9A-Za-zА-Яа-я-_]{1,100}$"
                                           placeholder="<fmt:message key="key.password" bundle="${lang}"/>">
                                </div>
                            </div>
                            <div class="col-4">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <h6 class="card-title">
                            <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.avatar" bundle="${lang}"/>
                        </h6>
                        <form action="/controller/editUser?attribute=userAvatar" method="post" enctype="multipart/form-data" class="row">
                            <div class="col-12">
                                <c:if test="${userAvatarSuccess != null}">
                                    <div role="alert" class="alert alert-success">
                                        <fmt:message key="${userAvatarSuccess}" bundle="${lang}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group input-group col-8">
                                <input type="file" class="custom-file-input d-none" name="userAvatar" id="avatar" accept="image/*" required>
                                <label for="avatar" class="custom-file-label form-control ">
                                        ${user.avatarURL}
                                </label>
                            </div>
                            <div class="col-4">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <form action="/controller/editUser?attribute=user&action=delete" method="post" class="row">
                            <div class="col-12">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-danger btn-block"
                                       value="<fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.user" bundle="${lang}"/>">
                            </div>
                        </form>
                    </div>
                </div>
            </custom:checkAcces>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>