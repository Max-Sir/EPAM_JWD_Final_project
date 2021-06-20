package by.sir.max.library.filter;

import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.Cache;
import by.sir.max.library.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UserBanFilter")
public class UserBanFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(UserBanFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute(JSPAttributeStorage.USER_LOGIN);
        if (userLogin != null) {
            UserService userService = ServiceFactory.getInstance().getUserService();
            Cache<String , User> onlineUsersCache = userService.getUsersOnlineCache();
            try {
                User currentUser = onlineUsersCache.get(userLogin);
                if (currentUser == null) {
                    LOGGER.warn(String.format("User with login: %s is not in cache", userLogin));
                    int userId =  Integer.parseInt((String) session.getAttribute(JSPAttributeStorage.USER_ID));
                    currentUser = userService.findUserById(userId);
                }
                if (currentUser != null && currentUser.getBanned()){
                    session.invalidate();
                }
            } catch (LibraryServiceException e) {
                LOGGER.warn(e);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
