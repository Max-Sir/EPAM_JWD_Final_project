package by.sir.max.library.command.reciever.user;

import by.sir.max.library.builder.UserBuilder;
import by.sir.max.library.command.*;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUserCommand implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        User newUser = constructUser(request);
        Router currentRouter = new Router();
        try {
            userService.registerUser(newUser, request.getRequestURL().toString());
            String redirectURL = getRedirectURL(request, CommandStorage.HOME_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            setUserInfoToRequest(request, newUser);
            currentRouter.setPagePath(PageStorage.REGISTER_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }

    private User constructUser(HttpServletRequest request) {
        String login = request.getParameter(JSPAttributeStorage.USER_LOGIN).trim();
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String firstName = request.getParameter(JSPAttributeStorage.USER_FIRST_NAME).trim();
        String lastName = request.getParameter(JSPAttributeStorage.USER_LAST_NAME).trim();
        String passportSerialNumber = request.getParameter(JSPAttributeStorage.USER_PASSPORT_SERIAL_NUMBER);
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL).trim().toLowerCase();
        String phoneNumber = request.getParameter(JSPAttributeStorage.USER_PHONE).trim();
        String address = request.getParameter(JSPAttributeStorage.USER_ADDRESS).trim();

        return new UserBuilder()
                .setLogin(login)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassportSerialNumber(passportSerialNumber)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAddress(address)
                .build();
    }

    private void setUserInfoToRequest(HttpServletRequest request, User user) {
        request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
    }
}
