package by.sir.max.library.filter;

import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.entity.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static by.sir.max.library.command.CommandStorage.*;

@WebFilter(filterName = "UserRolePermittedCommandFilter")
public class UserRolePermittedCommandFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(UserRolePermittedCommandFilter.class);

    private static final Set<CommandStorage> guestCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            REGISTER_PAGE,
            SWITCH_LANG,
            REGISTER_USER,
            LOG_IN,
            BOOK_CATALOG_PAGE,
            FORGET_PASSWORD_GENERATE_EMAIL
    );

    private static final Set<CommandStorage> userCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            LOG_OUT,
            SWITCH_LANG,
            PROFILE_PAGE,
            UPDATE_PROFILE_USER,
            BOOK_CATALOG_PAGE,
            USER_ORDERS_PAGE,
            ADD_BOOK_ORDER,
            CANCEL_BOOK_ORDER,
            RETURN_BOOK_ORDER
    );

    private static final Set<CommandStorage> adminCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            LOG_OUT,
            SWITCH_LANG,
            PROFILE_PAGE,
            UPDATE_PROFILE_USER,
            ADMIN_PAGE,
            USERS_LIST_PAGE,
            TOGGLE_USER_BAN,
            ADD_BOOK_PAGE,
            ADD_BOOK,
            ADD_BOOK_COMPONENT_PAGE,
            ADD_BOOK_AUTHOR,
            ADD_BOOK_PUBLISHER,
            ADD_BOOK_LANGUAGE,
            ADD_BOOK_GENRE,
            BOOK_CATALOG_PAGE,
            USER_ORDERS_PAGE,
            ADD_BOOK_ORDER,
            APPROVE_BOOK_ORDER,
            CANCEL_BOOK_ORDER,
            RETURN_BOOK_ORDER,
            OPEN_ORDERS_PAGE
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        UserRole userRole = UserRole.valueOf(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).toString().toUpperCase());
        Set<CommandStorage> permittedCommands;
        switch (userRole) {
            case ADMIN:
                permittedCommands = adminCommands;
                break;
            case USER:
                permittedCommands = userCommands;
                break;
            case GUEST:
                permittedCommands = guestCommands;
                break;
            default:
                permittedCommands = Collections.emptySet();
        }

        String commandName = request.getParameter(JSPAttributeStorage.COMMAND);
        CommandStorage command;
        if (commandName != null) {
            command = getCommandEnumByName(commandName);
        } else {
            command = HOME_PAGE;
        }

        if (permittedCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOGGER.info(String.format("Command is not in %s's Role scope: %s", userRole.name(), commandName));
            response.sendRedirect(request.getContextPath() + PageStorage.HOME);
        }
    }
}
