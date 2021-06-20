package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.*;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInByTokenLink implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String token = request.getParameter(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN);
        try {
            User user = userService.logInByToken(token);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, user.getLogin());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getUserRole().name());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
            userService.deleteRememberUserToken(user.getId());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            currentRouter.setPagePath(PageStorage.HOME);
        }
        return currentRouter;
    }
}