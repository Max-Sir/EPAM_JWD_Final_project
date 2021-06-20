<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="crt" uri="customtags"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=${lang}>
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/ico" href="${pageContext.request.contextPath}/img/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <title><fmt:message bundle="${locale}" key="header.home"/></title>
</head>
<body>
<header>
    <ul>
        <li><a href="${pageContext.request.contextPath}/jsp/index.jsp"><h1>
            <fmt:message bundle="${locale}" key="header.home"/>
        </h1></a></li>

        <c:if test="${userRole.equals('GUEST')}">
            <li><a class="active" href="${pageContext.request.contextPath}/jsp/login.jsp">
                <fmt:message bundle="${locale}" key="header.login"/>
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/jsp/registration.jsp">
                <fmt:message bundle="${locale}" key="header.registration"/>
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('ADMIN') || userRole.equals('USER')}">
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=profilePage">
                <fmt:message bundle="${locale}" key="header.profile"/>
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=logOut">
                <fmt:message bundle="${locale}" key="header.logout"/>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/controller?action=userOrdersPage&recordsPerPage=10&currentPage=1">
                <fmt:message bundle="${locale}" key="header.userOrdersPage"/>
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('USER') || userRole.equals('GUEST')}">
            <li>
                <div class="dropdown">
                    <form action="controller" method="post">
                        <input type="hidden" name="action" value="switchLang">
                        <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
                        <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
                        <div class="dropbtn"><fmt:message bundle="${locale}" key="header.lang"/></div>
                        <div class="dropdown-content">
                            <button type="submit" name="language" value="ru">
                                <fmt:message bundle="${locale}" key="header.lang.rus"/>
                            </button >
                            <button type="submit" name="language" value="en">
                                <fmt:message bundle="${locale}" key="header.lang.eng"/>
                            </button>
                        </div>
                    </form>
                </div>
            </li>
        </c:if>
    </ul>
    <c:if test="${userRole.equals('ADMIN')}">
        <div class="admin-bar">
            <div>
                <ul>
                    <li>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <input type="hidden" name="action" value="switchLang">
                                <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
                                <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
                                <div class="dropbtn"><fmt:message bundle="${locale}" key="header.lang"/></div>
                                <div class="dropdown-content">
                                    <button type="submit" name="language" value="ru">
                                        <fmt:message bundle="${locale}" key="header.lang.rus"/>
                                    </button >
                                    <button type="submit" name="language" value="en">
                                        <fmt:message bundle="${locale}" key="header.lang.eng"/>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/controller?action=adminPage">
                        <fmt:message bundle="${locale}" key="admin.onlineUserList" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?action=allUsersList&recordsPerPage=10&currentPage=1">
                        <fmt:message bundle="${locale}" key="admin.usersList" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?action=addBookPage">
                        <fmt:message bundle="${locale}" key="admin.addBook" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?action=addBookComponentPage">
                        <fmt:message bundle="${locale}" key="admin.addBookComponent" />
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?action=openOrdersPage&recordsPerPage=10&currentPage=1">
                        <fmt:message bundle="${locale}" key="admin.orders" />
                    </a></li>
                </ul>
            </div>
        </div>
    </c:if>
</header>