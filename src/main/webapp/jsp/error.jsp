<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
    <div class="error-message">
        <c:if test="${pageContext.errorData.statusCode != null}">
            <h1><fmt:message bundle="${exc_msg}" key="errorPage.title"/></h1>
            <br/>
            <c:if test="${pageContext.errorData.statusCode != 0}">
                <span><fmt:message bundle="${exc_msg}" key="errorPage.code"/>${pageContext.errorData.statusCode}</span>
            </c:if>
        </c:if>
        <h6><a href="mailto:javaeetemp@gmail.com"><fmt:message bundle="${exc_msg}" key="errorPage.contactUs"/></a></h6>
        <h6><a href="${pageContext.request.contextPath}"><fmt:message bundle="${exc_msg}" key="errorPage.toHome"/></a></h6>
    </div>
</main>
