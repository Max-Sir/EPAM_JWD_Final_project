package by.sir.max.library.command.reciever.user;

import by.sir.max.library.builder.BookBuilder;
import by.sir.max.library.builder.BookOrderBuilder;
import by.sir.max.library.builder.UserBuilder;
import by.sir.max.library.command.Command;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.command.reciever.page.FindBookPage;
import by.sir.max.library.entity.book.BookInstance;
import by.sir.max.library.entity.order.BookOrder;
import by.sir.max.library.entity.order.OrderStatus;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.BookOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelBookOrder implements Command {
    private static final BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router currentRouter = new Router();
        try {
            String orderUUID = request.getParameter(JSPAttributeStorage.ORDER_UUID);
            String orderUserLogin = request.getParameter(JSPAttributeStorage.USER_LOGIN);
            String orderUserEmail = request.getParameter(JSPAttributeStorage.USER_EMAIL);
            BookInstance bookInstance = new BookInstance();
            bookInstance.setUuid(request.getParameter(JSPAttributeStorage.BOOK_INSTANCE_UUID));
            bookInstance.setBook(new BookBuilder()
                    .setTitle(request.getParameter(JSPAttributeStorage.BOOK_TITLE))
                    .setAuthorName(request.getParameter(JSPAttributeStorage.BOOK_AUTHOR))
                    .build());
            BookOrder bookOrder = new BookOrderBuilder().setUuid(orderUUID)
                    .setOrderStatus(OrderStatus.CLOSE)
                    .setBookInstance(bookInstance)
                    .setUser(new UserBuilder()
                            .setLogin(orderUserLogin)
                            .setEmail(orderUserEmail)
                            .build())
                    .build();
            bookOrderService.updateBookOrderStatus(bookOrder);
            String redirectCommand = request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
            String redirectURL = getRedirectURL(request, redirectCommand);
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
            return currentRouter;
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            return new FindBookPage().execute(request, response);
        }
    }
}

