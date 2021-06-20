package by.sir.max.library.command.reciever.admin;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.entity.book.bookcomponent.BookLanguage;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookLanguage implements Command {
    private static final BookComponentService<BookLanguage> bookLanguageService = ServiceFactory.getInstance().getBookLanguageService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        BookLanguage bookLanguage = new BookLanguage();
        bookLanguage.setLanguageTitle(request.getParameter(JSPAttributeStorage.BOOK_LANGUAGE).trim());

        try {
            bookLanguageService.add(bookLanguage);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        String redirectURL = getRedirectURL(request, CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        return currentRouter;
    }
}
