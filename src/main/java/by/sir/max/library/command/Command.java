package by.sir.max.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response);

    default void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(JSPAttributeStorage.EXCEPTION_MESSAGE, message);
    }

    default void definePaginationContext(HttpServletRequest request, int fullRecordsQuantity, int currentPage, int recordsPerPage) {
        int pagesQuantity = fullRecordsQuantity / recordsPerPage;

        if (pagesQuantity % recordsPerPage > 0) {
            pagesQuantity++;
        }
        request.setAttribute(JSPAttributeStorage.PAGINATION_PAGES_QUANTITY, pagesQuantity);
        request.setAttribute(JSPAttributeStorage.PAGINATION_CURRENT_PAGE, currentPage);
        request.setAttribute(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE, recordsPerPage);
    }

    default String getRedirectURL(HttpServletRequest request, String page) {
        return request.getContextPath() + request.getServletPath() + "?" + JSPAttributeStorage.COMMAND + "=" + page;
    }
}
