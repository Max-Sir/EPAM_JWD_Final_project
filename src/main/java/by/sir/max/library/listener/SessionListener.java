package by.sir.max.library.listener;

import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.entity.user.UserRole;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener  {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, UserRole.GUEST.name());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String userLogin = (String) se.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        if (userLogin != null) {
            try {
                userService.logOut(userLogin);
            } catch (LibraryServiceException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
}
