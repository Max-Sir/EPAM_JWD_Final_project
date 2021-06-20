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
        <c:if test="${not empty orderList}">
            <table id="order-list">
                <tr>
                    <th><fmt:message bundle="${locale}" key="order.date.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="order.orderType.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="order.orderStatus.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="book.language"/></th>
                    <th><fmt:message bundle="${locale}" key="book.genre"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publisher"/></th>
                    <th><fmt:message bundle="${locale}" key="book.author"/></th>
                    <th><fmt:message bundle="${locale}" key="book.title"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publishYear"/></th>
                    <th><fmt:message bundle="${locale}" key="book.pagesQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.btn.tableHeader.order"/></th>
                </tr>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td><fmt:formatDate value="${order.date}" pattern="HH:mm:ss dd.MM.yyyy" /></td>
                        <td><fmt:message bundle="${locale}" key="${order.orderType.localizedName}"/></td>
                        <td><fmt:message bundle="${locale}" key="${order.orderStatus.localizedName}"/></td>
                        <td>${order.bookInstance.book.bookLanguage.languageTitle}</td>
                        <td>${order.bookInstance.book.genre.genreTitle}</td>
                        <td>${order.bookInstance.book.publisher.publisherTitle}</td>
                        <td>${order.bookInstance.book.author.authorName}</td>
                        <td>${order.bookInstance.book.title}</td>
                        <td>${order.bookInstance.book.publishYear}</td>
                        <td>${order.bookInstance.book.pagesQuantity}</td>
                        <td>
                            <form class="book_order-form" action="controller" method="post">
                                <input type="hidden" name="orderUUID" value="${order.uuid}">
                                <input type="hidden" name="bookInstanceUUID" value="${order.bookInstance.uuid}">
                                <input type="hidden" name="login" value="${order.user.login}">
                                <input type="hidden" name="email" value="${order.user.email}">
                                <input type="hidden" name="bookTitle" value="${order.bookInstance.book.title}">
                                <input type="hidden" name="author" value="${order.bookInstance.book.author.authorName}">
                                <input type="hidden" name="redirectPageCommand" value="userOrdersPage&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}">
                                <c:choose>
                                    <c:when test = "${order.orderStatus.name().equals('ISSUED_BY')}">
                                        <button name="action" value="returnBookOrder">
                                            <span><fmt:message bundle="${locale}" key="order.btn.returnBook"/></span>
                                        </button>
                                    </c:when>
                                    <c:when test = "${order.orderStatus.name().equals('PENDING')}">
                                        <button name="action" value="cancelBookOrder">
                                            <span><fmt:message bundle="${locale}" key="order.btn.cancelOrder"/></span>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <span><fmt:message bundle="${locale}" key="book.btn.unavailable.order"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <nav>
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item">
                            <a class="page-link" href="controller?action=userOrdersPage&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">
                                <fmt:message bundle="${locale}" key="pagination.prev"/>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${pagesQuantity}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active">
                                    <a class="page-link">
                                            ${i} <span class="sr-only"><fmt:message bundle="${locale}" key="pagination.current"/></span>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link" href="controller?action=userOrdersPage&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt pagesQuantity}">
                        <li class="page-item">
                            <a class="page-link" href="controller?action=userOrdersPage&recordsPerPage=${recordsPerPage}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">
                                <fmt:message bundle="${locale}" key="pagination.next"/>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
        <c:if test="${empty orderList}">
            <p class="error-message">
                <fmt:message bundle="${locale}" key="find.BookOrderList.isEmpty"/>
            </p>
        </c:if>
    </div>
</main>

