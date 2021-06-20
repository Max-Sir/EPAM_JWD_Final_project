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
            <table id="book-list">
                <tr>
                    <th><fmt:message bundle="${locale}" key="book.language"/></th>
                    <th><fmt:message bundle="${locale}" key="book.genre"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publisher"/></th>
                    <th><fmt:message bundle="${locale}" key="book.author"/></th>
                    <th><fmt:message bundle="${locale}" key="book.title"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publishYear"/></th>
                    <th><fmt:message bundle="${locale}" key="book.pagesQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.totalQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.availableQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.description"/></th>
                    <c:if test = "${userRole.equals('ADMIN') || userRole.equals('USER')}">
                        <th><fmt:message bundle="${locale}" key="book.btn.tableHeader.order"/></th>
                    </c:if>
                </tr>
                <c:forEach var="bookDTO" items="${bookDTO}">
                    <tr>
                        <td>${bookDTO.book.bookLanguage.languageTitle}</td>
                        <td>${bookDTO.book.genre.genreTitle}</td>
                        <td>${bookDTO.book.publisher.publisherTitle}</td>
                        <td>${bookDTO.book.author.authorName}</td>
                        <td>${bookDTO.book.title}</td>
                        <td>${bookDTO.book.publishYear}</td>
                        <td>${bookDTO.book.pagesQuantity}</td>
                        <td>${bookDTO.totalBooksQuantity}</td>
                        <td>${bookDTO.totalAvailableBooksQuantity}</td>
                        <td>${bookDTO.book.description}</td>
                        <c:if test = "${userRole.equals('ADMIN') || userRole.equals('USER')}">
                            <td>
                                <form class="book_order-form" action="controller" method="post">
                                    <input type="hidden" name="action" value="addBookOrder">
                                    <input type="hidden" name="bookUUID" value="${bookDTO.book.uuid}">
                                    <c:if test = "${bookDTO.totalAvailableBooksQuantity > 0}">
                                    <fieldset>
                                        <label class="book-select_wrapper">
                                            <button name="orderTypeResult" value="${orderTypeSubscription.name()}">
                                                <span><fmt:message bundle="${locale}" key="${orderTypeSubscription.localizedName}"/></span>
                                            </button>
                                        </label>
                                    </fieldset>
                                    <fieldset>
                                        <label class="book-select_wrapper">
                                            <button name="orderTypeResult" value="${orderTypeReadingHole.name()}">
                                                <span><fmt:message bundle="${locale}" key="${orderTypeReadingHole.localizedName}"/></span>
                                            </button>
                                        </label>
                                    </fieldset>
                                    </c:if>
                                    <c:if test = "${bookDTO.totalAvailableBooksQuantity == 0}">
                                        <span><fmt:message bundle="${locale}" key="book.btn.unavailable.order"/></span>
                                    </c:if>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</main>

