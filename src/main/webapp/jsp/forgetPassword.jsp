<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${not empty exception_msg}">
<div class="error-message">
    <p>
        <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
    </p>
</div>
</c:if>
<main class="content">
    <form class="form-wrapper" method="post" action="controller" id="login-form">
        <fieldset class="fieldset">
            <input type="hidden" name="action" value="sendForgetPasswordData">
            <legend class="title">
                <fmt:message bundle="${locale}" key="login.forgetPassword"/>
            </legend>
            <label>
                <span><fmt:message bundle="${locale}" key="user.email"/></span>
                <input type="text" name="email" class="input" required>
            </label>
            <label class="inputfield">
                <input class="btn submit" type="submit" value=<fmt:message bundle="${locale}" key="login.enter"/> >
            </label>
        </fieldset>
    </form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>