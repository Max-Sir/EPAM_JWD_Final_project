package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilePage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        Router currentRouter = new Router();
        try {
            User user = userService.getUsersOnlineCache().get(login);
            request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
