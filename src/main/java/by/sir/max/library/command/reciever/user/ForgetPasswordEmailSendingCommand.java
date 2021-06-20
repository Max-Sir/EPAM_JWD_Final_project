package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.*;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgetPasswordEmailSendingCommand implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL);
        try {
            userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            currentRouter.setPagePath(PageStorage.LOG_IN);
        }
        return currentRouter;
    }
}
