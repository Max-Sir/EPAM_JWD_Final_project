<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    <div class="search-wrapper">
        <aside class="search-form">
            <form class="form-wrapper-search" method="get" action="controller"  id="book-search-form">
                <fieldset class="fieldset">
                    <input type="hidden" name="action" value="bookCatalogPage">
                    <input type="hidden" name="recordsPerPage" value="10">
                    <input type="hidden" name="currentPage" value="1">
                    <legend class="title">
                        <fmt:message  bundle="${locale}" key="book.btn.findBook"/>
                    </legend>
                    <label class="book-select_wrapper">
                        <select class="book-select" name="genre">
                            <option value=""><fmt:message bundle="${locale}" key="book.genre.any"/></option>
                            <c:if test="${empty bookPreviousData}">
                                <option selected disabled value=""><fmt:message bundle="${locale}" key="book.genre" /></option>
                            </c:if>
                            <c:forEach var="genre" items="${genres}">
                                <c:choose>
                                    <c:when test="${not empty bookPreviousData && bookPreviousData.genre.uuid.equals(genre.uuid)}">
                                        <option selected value="${genre.uuid}">${genre.genreTitle}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${genre.uuid}">${genre.genreTitle}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="bookLanguage">
                            <option value=""><fmt:message bundle="${locale}" key="book.language.any"/></option>
                            <c:if test="${empty bookPreviousData}">
                                <option selected disabled value=""><fmt:message bundle="${locale}" key="book.language" /></option>
                            </c:if>
                            <c:forEach var="bookLanguage" items="${bookLanguages}">
                                <c:choose>
                                    <c:when test="${not empty bookPreviousData && bookPreviousData.bookLanguage.uuid.equals(bookLanguage.uuid)}">
                                        <option selected value="${bookLanguage.uuid}">${bookLanguage.languageTitle}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${bookLanguage.uuid}">${bookLanguage.languageTitle}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="publisher">
                            <option value=""><fmt:message bundle="${locale}" key="book.publisher.any"/></option>
                            <c:if test="${empty bookPreviousData}">
                                <option selected disabled value=""><fmt:message bundle="${locale}" key="book.publisher" /></option>
                            </c:if>
                            <c:forEach var="publisher" items="${publishers}">
                                <c:choose>
                                    <c:when test="${not empty bookPreviousData && bookPreviousData.publisher.uuid.equals(publisher.uuid)}">
                                        <option selected value="${publisher.uuid}">${publisher.publisherTitle}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${publisher.uuid}">${publisher.publisherTitle}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="author">
                            <option value=""><fmt:message bundle="${locale}" key="book.author.any"/></option>
                            <c:forEach var="author" items="${authors}">
                                <c:choose>
                                    <c:when test="${not empty bookPreviousData && bookPreviousData.author.uuid.equals(author.uuid)}">
                                        <option selected value="${author.uuid}">${author.authorName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${author.uuid}">${author.authorName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </label>
                    <label>
                        <span><fmt:message  bundle="${locale}" key="book.title"/></span>
                        <input type="text"  name="bookTitle" class="input"
                           <c:if test="${not empty bookPreviousData}">value="${bookPreviousData.title}"</c:if>
                        >
                        <span><fmt:message  bundle="${locale}" key="book.publishYear"/></span>
                        <input type="number"  name="bookPublishYear" class="input"
                            <c:if test="${not empty bookPreviousData  && bookPreviousData.publishYear != 0}">
                               value="${bookPreviousData.publishYear}"
                            </c:if>
                        >
                        <span><fmt:message  bundle="${locale}" key="book.pagesQuantity"/></span>
                        <input type="number"  name="bookPagesQuantity" class="input"
                            <c:if test="${not empty bookPreviousData && bookPreviousData.pagesQuantity != 0}">
                               value="${bookPreviousData.pagesQuantity}"
                            </c:if>
                        >
                    </label>
                    <label class="inputfield">
                        <input class="btn submit" type="submit" value=<fmt:message  bundle="${locale}" key="book.btn.findBook"/>/>
                    </label>
                </fieldset>
            </form>
        </aside>
        <c:if test="${not empty bookDTO}">
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
                                    <input type="hidden" name="redirectPageCommand" value="bookCatalogPage&recordsPerPage=${recordsPerPage}&currentPage=${currentPage}">

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
        <nav>
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=bookCatalogPage&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">
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
                                <a class="page-link" href="controller?action=bookCatalogPage&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt pagesQuantity}">
                    <li class="page-item">
                        <a class="page-link" href="controller?action=bookCatalogPage&recordsPerPage=${recordsPerPage}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">
                            <fmt:message bundle="${locale}" key="pagination.next"/>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
        <c:if test="${empty bookDTO}">
            <p class="error-message">
                <fmt:message bundle="${locale}" key="find.bookDTO.isEmpty"/>
            </p>
        </c:if>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>

