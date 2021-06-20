package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBookComponentPage implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        currentRouter.setPagePath(PageStorage.ADD_BOOK_COMPONENT);
        currentRouter.setRouteType(Router.RouteType.FORWARD);
        return currentRouter;
    }
}
