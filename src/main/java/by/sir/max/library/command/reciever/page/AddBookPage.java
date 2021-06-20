package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookService;
import by.sir.max.library.service.impl.CommonBookComponentsCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookPage implements Command {
    private static final BookService bookService = ServiceFactory.getInstance().getBookService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();

        CommonBookComponentsCache bookComponentsCache = bookService.getBookComponentsCache();
        request.setAttribute(JSPAttributeStorage.BOOK_AUTHORS, bookComponentsCache.getAuthors().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_GENRES, bookComponentsCache.getGenres().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_LANGUAGES, bookComponentsCache.getBookLanguages().getAllValuesList());
        request.setAttribute(JSPAttributeStorage.BOOK_PUBLISHERS, bookComponentsCache.getPublishers().getAllValuesList());

        currentRouter.setPagePath(PageStorage.ADD_BOOK);
        currentRouter.setRouteType(Router.RouteType.FORWARD);

        return currentRouter;
    }
}