package by.sir.max.library.command.reciever.admin;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.command.*;
import by.sir.max.library.command.reciever.page.AddBookPage;
import by.sir.max.library.entity.book.Book;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBook implements Command {
    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        Book resultBook = constructBookFromRequest(request);
        int bookQuantity = Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_QUANTITY));
        try {
            bookService.add(resultBook, bookQuantity);
        } catch (LibraryServiceException e) {
            request.setAttribute(JSPAttributeStorage.BOOK_PREVIOUS_DATA, resultBook);
            setErrorMessage(request, e.getMessage());
            return new AddBookPage().execute(request, response);
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        return currentRouter;
    }

    private Book constructBookFromRequest(HttpServletRequest request) {
        String genreUUID = request.getParameter(JSPAttributeStorage.BOOK_GENRE).trim();
        String bookLanguageUUID = request.getParameter(JSPAttributeStorage.BOOK_LANGUAGE).trim();
        String publisherUUID = request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER).trim();
        String authorUUID = request.getParameter(JSPAttributeStorage.BOOK_AUTHOR).trim();
        String title = request.getParameter(JSPAttributeStorage.BOOK_TITLE).trim();
        String description = request.getParameter(JSPAttributeStorage.BOOK_DESCRIPTION).trim();
        int publishYear = Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PUBLISH_YEAR));
        int pagesQuantity = Integer.parseInt(request.getParameter(JSPAttributeStorage.BOOK_PAGES_QUANTITY));

        return new BookBuilder()
                .setGenreUUID(genreUUID)
                .setBookLanguageUUID(bookLanguageUUID)
                .setPublisherUUID(publisherUUID)
                .setAuthorUUID(authorUUID)
                .setTitle(title)
                .setPublishYear(publishYear)
                .setPagesQuantity(pagesQuantity)
                .setDescription(description)
                .build();
    }
}
