<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>

    <div class="inherit d-flex justify-content-center align-items-center">
        <div class="d-flex">
            <div class="card card-default">
                <div class="card-header text-center">
                    <span class="my-color">
                        <jsp:include page="/img/svg/login.svg"/>
                    </span>
                    <h5>
                        <fmt:message key="key.login" bundle="${lang}"/>
                    </h5>
                    <h6>
                        <fmt:message key="key.loginToYourAccount" bundle="${lang}"/>
                    </h6>
                </div>
                <div class="card-body">
                    <form action="/controller/login" method="post">
                        <c:if test="${param.error != null}">
                            <div role="alert" class="alert alert-danger">
                                <fmt:message key="${param.error}" bundle="${lang}"/>
                            </div>
                        </c:if>
                        <c:if test="${loginError != null}">
                            <div role="alert" class="alert alert-danger">
                                <fmt:message key="${loginError}" bundle="${lang}"/>
                            </div>
                        </c:if>
                        <div class="form-group input-group ">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/name.svg"/>
                                </span>
                                <input type="text" class="form-control my-form" name="userName"
                                       required pattern="^[^<>]{1,100}$"
                                       placeholder="<fmt:message key="key.name" bundle="${lang}"/>">
                            </div>
                        </div>
                        <div class="form-group input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <jsp:include page="/img/svg/password.svg"/>
                                </span>
                                <input type="password" class="form-control my-form" name="userPassword"
                                       required pattern="^[0-9A-Za-zА-Яа-я-_]{1,100}$"
                                       placeholder="<fmt:message key="key.password" bundle="${lang}"/>">
                            </div>
                        </div>
                        <div>
                            <input type="submit" class="btn btn-primary btn-block"
                                   value="<fmt:message key="key.login" bundle="${lang}"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="footer.jsp"/>