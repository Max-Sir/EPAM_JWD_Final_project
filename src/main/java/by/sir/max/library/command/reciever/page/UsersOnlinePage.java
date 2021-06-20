package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersOnlinePage implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        request.setAttribute(JSPAttributeStorage.ONLINE_USERS_LIST, userService.findUsersOnline());
        currentRouter.setPagePath(PageStorage.ONLINE_USERS_LIST);
        currentRouter.setRouteType(Router.RouteType.FORWARD);
        return currentRouter;
    }
}
