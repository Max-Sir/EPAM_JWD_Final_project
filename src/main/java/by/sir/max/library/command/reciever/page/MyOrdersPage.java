package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.PageStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyOrdersPage implements Command {
    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        int currentPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_CURRENT_PAGE));
        int recordsPerPage = Integer.parseInt(request.getParameter(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE));
        try {
            int arrayOrdersShift = (currentPage - 1) * recordsPerPage;
            List<BookOrder> bookOrders = bookOrderService.getBookOrdersCache()
                    .get((String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN))
                    .getAllValuesList();
            definePaginationContext(request, bookOrders.size(), currentPage, recordsPerPage);
            bookOrders = bookOrders.subList(arrayOrdersShift, Math.min(arrayOrdersShift + recordsPerPage, bookOrders.size()));
            request.setAttribute(JSPAttributeStorage.ORDER_LIST, bookOrders);
            router.setPagePath(PageStorage.USER_ORDERS_PAGE);
            router.setRouteType(Router.RouteType.FORWARD);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            router.setPagePath(PageStorage.HOME);
            router.setRouteType(Router.RouteType.FORWARD);
        }
        return router;
    }
}
