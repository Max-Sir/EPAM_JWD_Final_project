package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.*;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllUsersPage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        try {
            List<User> userList = userService.findAllUsers();
            request.setAttribute(JSPAttributeStorage.ALL_USERS_LIST, userList);
            currentRouter.setPagePath(PageStorage.ALL_USERS_LIST);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.ALL_USERS_LIST);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }
}
