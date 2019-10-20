<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>

<div class="container">
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

<jsp:include page="footer.jsp"/>



