package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.*;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final int COOKIE_MAX_AGE_21_DAY = 60*60*24*21;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN);
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String rememberToken = request.getParameter(JSPAttributeStorage.GENERATE_REMEMBER_USER_TOKEN);

        Router currentRouter = new Router();
        try {
            User user = userService.logInByPassword(login, password);
            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, login);
            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getUserRole().name());
            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());

            if (rememberToken != null) {
                Cookie rememberTokenCookie = new Cookie(
                        JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN, userService.getUpdatedRememberUserToken(user.getId())
                );
                rememberTokenCookie.setPath(request.getContextPath());
                rememberTokenCookie.setMaxAge(COOKIE_MAX_AGE_21_DAY);
                response.addCookie(rememberTokenCookie);
            }
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.LOG_IN);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
