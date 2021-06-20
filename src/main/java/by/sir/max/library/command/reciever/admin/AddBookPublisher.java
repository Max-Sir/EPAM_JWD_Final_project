package by.sir.max.library.command.reciever.admin;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.entity.book.bookcomponent.Publisher;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookComponentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookPublisher  implements Command {
    private static final BookComponentService<Publisher> publisherService = ServiceFactory.getInstance().getBookPublisherService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        Publisher publisher = new Publisher();
        publisher.setPublisherTitle(request.getParameter(JSPAttributeStorage.BOOK_PUBLISHER).trim());

        try {
            publisherService.add(publisher);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
        }
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        String redirectURL = getRedirectURL(request, CommandStorage.ADD_BOOK_COMPONENT_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        return currentRouter;
    }
}
