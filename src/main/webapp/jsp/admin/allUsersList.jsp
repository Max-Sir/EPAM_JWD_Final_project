<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
    <div class="table_content-wrapper">
        <c:if test="${not empty exception_msg}">
            <div class="error-message">
                <p>
                    <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
                </p>
            </div>
        </c:if>
        <c:if test="${empty exception_msg}">
            <table id="users-list">
                <tr>
                    <th><fmt:message bundle="${locale}" key="user.id"/></th>
                    <th><fmt:message bundle="${locale}" key="user.login"/></th>
                    <th><fmt:message bundle="${locale}" key="user.email"/></th>
                    <th><fmt:message bundle="${locale}" key="user.phone"/></th>
                    <th><fmt:message bundle="${locale}" key="user.firstname"/></th>
                    <th><fmt:message bundle="${locale}" key="user.lastname"/></th>
                    <th><fmt:message bundle="${locale}" key="user.isBanned"/></th>
                </tr>
                <c:forEach var="user" items="${allUsersList}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>
                            <c:if test = "${user.userRole.name().equals('USER')}">
                                <form class="ban-form" action="controller" method="post">
                                    <input type="hidden" name="action" value="banUser">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="hidden" name="redirectPageCommand" value="allUsersList">
                                    <c:if test = "${user.getBanned()}">
                                        <input class="btn" type="submit" value=<fmt:message bundle="${locale}" key="admin.btn.unbanUser"/>>
                                    </c:if>
                                    <c:if test = "${not user.getBanned()}">
                                        <input class="btn" type="submit" value=<fmt:message bundle="${locale}" key="admin.btn.banUser"/>>
                                    </c:if>

                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</main>

