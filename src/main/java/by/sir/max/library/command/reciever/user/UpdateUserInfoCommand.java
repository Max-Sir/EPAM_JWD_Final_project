package by.sir.max.library.command.reciever.user;

import by.sir.max.library.command.*;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.LibraryServiceException;
import by.sir.max.library.factory.ServiceFactory;
import by.sir.max.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserInfoCommand implements Command {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        User updatedUser;
        Router currentRouter = new Router();
        try {
            updatedUser = userService.findUserByLogin(login);
        } catch (LibraryServiceException e) {
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
            return currentRouter;
        }

        try {
            updateUserInfo(request, updatedUser);
            userService.updateUserProfileData(updatedUser);
            String redirectURL = getRedirectURL(request, CommandStorage.PROFILE_PAGE.getCommandName());
            currentRouter.setPagePath(redirectURL);
            currentRouter.setRouteType(Router.RouteType.REDIRECT);
        } catch (LibraryServiceException e) {
            setUserInfoToRequest(request, updatedUser);
            setErrorMessage(request, e.getMessage());
            currentRouter.setPagePath(PageStorage.PROFILE_USER);
            currentRouter.setRouteType(Router.RouteType.FORWARD);
        }
        return currentRouter;
    }

    private void updateUserInfo(HttpServletRequest request, User currentUser) {
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String email = request.getParameter(JSPAttributeStorage.USER_EMAIL).trim().toLowerCase();
        String phoneNumber = request.getParameter(JSPAttributeStorage.USER_PHONE).trim();
        String address = request.getParameter(JSPAttributeStorage.USER_ADDRESS).trim();
        currentUser.setPassword(password);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setAddress(address);
    }

    private void setUserInfoToRequest(HttpServletRequest request, User user) {
        request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
    }
}
