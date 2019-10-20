<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

<jsp:include page="/jsp/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    ${chapter.name}
                </div>
                <div class="card-body">
                    <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                        <h6 class="card-title">
                            <fmt:message key="key.change" bundle="${lang}"/> <fmt:message key="key.name" bundle="${lang}"/>
                        </h6>
                        <form action="/controller/editChapter?attribute=chapterName&chapterId=${chapter.id}" method="post" class="row">
                            <div class="form-group input-group col-9">
                                <input type="text" class="form-control" name="chapterName"
                                       required pattern="^[^<>]{1,100}$"
                                       placeholder="<fmt:message key="key.name" bundle="${lang}"/>"
                                       value="${chapter.name}">
                            </div>
                            <div class="col-3">
                                <input type="submit" class="btn btn-primary btn-block"
                                       value="<fmt:message key="key.save" bundle="${lang}"/>">
                            </div>
                        </form>
                        <a href="/controller/editChapter?attribute=chapter&action=delete&chapterId=${chapter.id}"
                           class="btn btn-block btn-danger mb-4">
                            <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.chapter" bundle="${lang}"/>
                        </a>
                    </custom:checkAcces>
                    <c:forEach items="${chapter.imageList}" var="image">
                        <img src="/controller/loadFile?fileName=${image.URL}" width="100%" class="mb-4">
                        <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                            <form action="/controller/editChapter?attribute=imageURL&chapterId=${chapter.id}&imageId=${image.id}"
                                  method="post" enctype="multipart/form-data" class="row">
                                <div class="form-group input-group col-9">
                                    <input type="file" class="custom-file-input d-none" name="imageURL" id="img${image.id}" accept="image/*" required>
                                    <label for="img${image.id}" class="custom-file-label form-control">
                                            ${image.URL}
                                    </label>
                                </div>
                                <div class="col-3">
                                    <input type="submit" class="btn btn-primary btn-block"
                                           value="<fmt:message key="key.save" bundle="${lang}"/>">
                                </div>
                            </form>
                            <a href="/controller/editChapter?attribute=chapterImage&action=delete&chapterId=${chapter.id}&imageId=${image.id}"
                               class="btn btn-block btn-danger mb-4">
                                <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.image" bundle="${lang}"/>
                            </a>
                        </custom:checkAcces>
                    </c:forEach>
                    <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN">
                        <a href="/controller/editChapter?attribute=chapterImage&action=add&chapterId=${chapter.id}&"
                        class="btn btn-block btn-success">
                            <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.image" bundle="${lang}"/>
                        </a>
                    </custom:checkAcces>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-header">
                    <fmt:message key="key.comments" bundle="${lang}"/>
                </div>
                <div class="card-body">
                    <c:forEach items="${chapter.commentList}" var="comment">
                        <div class="media mb-3">
                            <a
                               <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="USER">
                                   href="/controller/showUser?userId=${comment.user.id}"
                               </custom:checkAcces>
                               style="background-image: url('/controller/loadFile?fileName=${comment.user.avatarURL}');
                                       background-size: cover; background-position: center;"
                               class="my-image-media mr-4">
                            </a>
                            <div class="media-body">
                                <h5 class="mt-0">${comment.user.name}</h5>
                                    ${comment.content}
                                <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="ADMIN"
                                                   currentId="${authorizedUser.id}" accessId="${comment.user.id}">
                                    <a href="/controller/editComment?action=delete&commentId=${comment.id}"
                                    class="btn btn-danger btn-block">
                                        <fmt:message key="key.delete" bundle="${lang}"/> <fmt:message key="key.comment" bundle="${lang}"/>
                                    </a>
                                </custom:checkAcces>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <custom:checkAcces currentRole="${authorizedUser.role}" accessRole="USER">
                <div class="card mt-4">
                    <div class="card-header">
                        <fmt:message key="key.add" bundle="${lang}"/> <fmt:message key="key.comment" bundle="${lang}"/>
                    </div>
                    <div class="card-body">
                        <div class="media">
                            <div style="background-image: url('/controller/loadFile?fileName=${authorizedUser.avatarURL}');
                                    background-size: cover; background-position: center;"
                                 class="my-image-media mr-4">

                            </div>
                            <div class="media-body">
                                <h5 class="mt-0">${authorizedUser.name}</h5>
                                <form action="/controller/editComment?action=add&chapterId=${chapter.id}&userId=${authorizedUser.id}"
                                      method="post" class="row">
                                    <div class="form-group input-group col-9">
                                        <textarea required pattern="^[^<>]{1,1000}$" name="commentContent" class="form-control"></textarea>
                                    </div>
                                    <div class="col-3">
                                        <input type="submit" class="btn btn-primary btn-block"
                                               value="<fmt:message key="key.save" bundle="${lang}"/>">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </custom:checkAcces>
        </div>
    </div>
</div>


<jsp:include page="/jsp/footer.jsp"/>
