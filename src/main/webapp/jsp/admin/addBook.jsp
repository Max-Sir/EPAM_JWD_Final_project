<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    <form class="form-wrapper" method="post" action="controller"  id="book-add-form">
        <fieldset class="fieldset">
            <input type="hidden" name="action" value="addBook">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="admin.addBook"/>
            </legend>
            <label class="book-select_wrapper">
                <select class="book-select" name="genre" required>
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
                <select class="book-select" name="bookLanguage" required>
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
            </label>
            <label class="book-select_wrapper">
                <select class="book-select" name="publisher" required>
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
                <select class="book-select" name="author" required>
                    <c:if test="${empty bookPreviousData}">
                        <option selected disabled value=""><fmt:message bundle="${locale}" key="book.author" /></option>
                    </c:if>
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
                <input type="text"  name="bookTitle" class="input" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,45}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.title" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.title" />' : '');"
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.title} </c:if>
                >
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.publishYear"/></span>
                <input type="number"  name="bookPublishYear" class="input" required min="1000"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.publishYear" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.publishYear" />' : '');"
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.publishYear} </c:if>
                >
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.pagesQuantity"/></span>
                <input type="number"  name="bookPagesQuantity" class="input" required min="10" max="32767"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.pagesQuantity" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.pagesQuantity" />' : '');"
                <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.pagesQuantity} </c:if>
                >
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.quantity"/></span>
                <input type="number"  name="bookQuantity" class="input" required min="1"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.bookQuantity" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.bookQuantity" />' : '');"
                >
            </label>
            <label>
                <textarea name="bookDescription" rows="10" cols="45" class="input" maxlength="500" required
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.description" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.description" />' : '');"
                >
                <c:if test="${empty bookPreviousData}"> <fmt:message  bundle="${locale}" key="book.description"/> </c:if>
                <c:if test="${not empty bookPreviousData}"> ${bookPreviousData.description} </c:if>
                </textarea>
            </label>
            <label class="inputfield">
                <input class="btn submit" type="submit" value=<fmt:message  bundle="${locale}" key="book.btn.addBook"/>>
            </label>
        </fieldset>
    </form>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
