<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content">
    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    <div class="form-wrapper" id="component-form">
        <form class="fieldset"  method="post" action="controller">
            <legend class="title">
                <fmt:message bundle="${locale}" key="book.btn.addBook.genre"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="genre" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,40}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.genre" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.genre" />' : '');"
                >
                <button class="submit" name="action" value="addBookGenre">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>
        </form>
        <form class="fieldset" method="post" action="controller">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.language"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="bookLanguage" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,15}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.language" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.language" />' : '');"
                >
                <button class="submit" name="action" value="addBookLanguage">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>

        </form>
        <form class="fieldset" method="post" action="controller">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.publisher"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="publisher" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,45}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.publisher" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.publisher" />' : '');"
                >
                <button class="submit" name="action" value="addBookPublisher">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>

        </form>
        <form class="fieldset" method="post" action="controller">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.author"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="author" required
                    pattern="^[^!@#$%^&*()_|+~\d]{3,40}$"
                    oninvalid="this.setCustomValidity('<fmt:message bundle="${exc_msg}" key="validation.book.add.author" />')"
                    onchange="this.setAttribute('value', this.value);
                    this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message bundle="${exc_msg}" key="validation.book.add.author" />' : '');"
                >
                <button class="submit" name="action" value="addBookAuthor">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>
        </form>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xxsProtectionScript.js"></script>
