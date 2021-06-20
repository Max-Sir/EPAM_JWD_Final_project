package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostRegistrationAccountApproval implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String token = request.getParameter(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN);
        try {
            userService.postRegistrationApprovalByToken(token);
            return new LogInByTokenLink().execute(request, response);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.LOG_IN);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}