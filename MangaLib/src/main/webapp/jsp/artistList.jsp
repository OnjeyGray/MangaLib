<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.09.2019
  Time: 12:12
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
            <div class="col-6 col-sm-4 col-md-3 col-lg-3 p-2">
                <div class="card">
                    <div class="card-body">
                        <a href="/controller/editArtist?attribute=artist&action=add">
                            <h6 class="text-truncate mb-0 mt-2">
                                <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.artist" bundle="${lang}"/>
                            </h6>
                        </a>
                        <div>
                            <fmt:message key="key.artist" bundle="${lang}"/> <fmt:message key="key.description" bundle="${lang}"/>
                        </div>
                    </div>
                </div>
            </div>
        </custom:checkAcces>
        <c:forEach items="${artistList}" var="artist">
            <div class="col-6 col-sm-4 col-md-3 col-lg-3 p-2">
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

<jsp:include page="footer.jsp"/>



