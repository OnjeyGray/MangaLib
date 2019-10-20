<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 p-2">
                <div class="card">
                    <div class="card-body">
                        <a href="/controller/editManga?attribute=manga&action=add">
                            <div class="my-image" style="background-image: url('/controller/loadFile?fileName=defaultMangaImg.jpg');
                                    background-size: cover; background-position: center;">
                            </div>
                        </a>
                        <h6 class="text-truncate mb-0 mt-2">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.manga" bundle="${lang}"/>
                        </h6>
                    </div>
                </div>
            </div>
        </custom:checkAcces>
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