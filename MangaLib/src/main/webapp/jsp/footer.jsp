<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2019
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom"%>

</main>

<footer class="bg-white my-footer" id="footer">
    <div class="container d-flex justify-content-between">
        <div class="dropup">
            <a data-toggle="dropdown" class="btn btn-link dropdown-toggle">
                <span>
                    <jsp:include page="/img/svg/language.svg"/>
                </span>
                ${currentLanguage.name}
            </a>
            <div class="dropdown-menu">
                <c:forEach items="${languageList}" var="language">
                    <a href="/controller/changeLanguage?languageId=${language.id}" class="dropdown-item">
                    <span>
                        <jsp:include page="/img/flags/${language.locale}.svg"/>
                    </span>
                        ${language.name}
                    </a>
                </c:forEach>
            </div>
        </div>
        <a href="/controller/showMain" class="btn btn-link">
            <img src="/img/favicon.gif" width="30px" height="30px">
        </a>
        <div>
            <a href="https://twitter.com/?lang=ru" class="btn btn-link">
                <jsp:include page="/img/social/twitter.svg"/>
            </a>
            <a href="https://www.instagram.com/?hl=ru" class="btn btn-link">
                <jsp:include page="/img/social/instagram.svg"/>
            </a>
        </div>
    </div>
</footer>

</div>
<script src="/js/calcMainHeight.js"></script>
<script src="/js/jQuery.js"></script>
<script src="/js/popper.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>
