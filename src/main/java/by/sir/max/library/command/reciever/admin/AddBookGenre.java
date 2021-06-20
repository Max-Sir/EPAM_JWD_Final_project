package by.sir.max.library.command.reciever.admin;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.entity.book.bookcomponent.Genre;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookGenre implements Command {
    private static final BookComponentService<Genre> genreService = ServiceFactory.getInstance().getBookGenreService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        Genre genre = new Genre();
        genre.setGenreTitle(request.getParameter(JSPAttributeStorage.BOOK_GENRE).trim());

        try {
            genreService.add(genre);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        String redirectURL = getRedirectURL(request, CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        return currentRouter;
    }
}
