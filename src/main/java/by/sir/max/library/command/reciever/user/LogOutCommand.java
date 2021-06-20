package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.*;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class);

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        int userId = (Integer) request.getSession().getAttribute(JSPAttributeStorage.USER_ID);
        try {
            userService.logOut(login);
        } catch (LibraryServiceException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN)) {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    cookie.setValue("");
                    response.addCookie(cookie);
                    try {
                        userService.deleteRememberUserToken(userId);
                    } catch (LibraryServiceException e) {
                        LOGGER.warn(String.format("RememberToken is not deleted for user %s", login), e);
                    }
                }
            }
        }

        request.getSession().invalidate();
        String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
        currentRouter.setPagePath(redirectURL);
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        return currentRouter;
    }
}
