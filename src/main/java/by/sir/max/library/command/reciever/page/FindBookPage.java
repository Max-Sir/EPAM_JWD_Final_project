package by.sir.max.library.command.reciever.page;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.dto.BookDTO;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.entity.order.OrderType;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookService;
import by.sir.max.library.service.impl.CommonBookComponentsCache;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindBookPage implements Command {
    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        CommonBookComponentsCache bookComponentsCache = bookService.getBookComponentsCache();
        request.setAttribute(JSPAttributeStorage.BOOK_AUTHORS, bookComponentsCache.getAuthors().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_GENRES, bookComponentsCache.getGenres().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_LANGUAGES, bookComponentsCache.getBookLanguages().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_PUBLISHERS, bookComponentsCache.getPublishers().getAllValuesList());

        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_SUBSCRIPTION, OrderType.SUBSCRIPTION);
        request.setAttribute(JSPAttributeStorage.ORDER_TYPE_READING_HOLE, OrderType.READING_HOLE);
        int currentPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_CURRENT_PAGE));
        int recordsPerPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE));
        try {
            Book resultBook = constructBookFromRequest(request);
            request.setAttribute(JSPAttributeStorage.BOOK_PREVIOUS_DATA, resultBook);
            List<BookDTO> bookDTOList = bookService.findByFields(resultBook, currentPage, recordsPerPage);
            definePaginationContext(request, bookService.findBookQuantityByFields(resultBook), currentPage, recordsPerPage);
            request.setAttribute(JSPAttributeStorage.BOOK_DATA_TRANSFER_OBJECT, bookDTOList);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }

        currentRouter.setPagePath(PageStorage.FIND_BOOK);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        return currentRouter;

    }

    private Book constructBookFromRequest(HttpServletRequest request) {
        return new BookBuilder().setGenreUUID(request.getParameter(JSPAttributeStorage.BOOK_GENRE))
                .setBookLanguageUUID(request.getParameter(JSPAttributeStorage.BOOK_LANGUAGE))
                .setPublisherUUID(request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER))
                .setAuthorUUID(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR))
                .setTitle(request.getParameter(JSPAttributeStorage.BOOK_TITLE))
                .setPublishYear(StringUtils.isBlank(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR))
                        ? 0 : Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR)))
                .setPagesQuantity(StringUtils.isBlank(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY))
                        ? 0 : Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY)))
                .build();
    }
}
