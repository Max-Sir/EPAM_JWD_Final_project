package by.sir.max.library.command.reciever.page;

import by.sir.max.library.command.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchLanguageCommand implements Command {
    private static final int COOKIE_MAX_AGE_21_DAY = 60*60*24*21;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String chosenLang = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
        String resultLang = SupportedLocaleStorage.getLocaleFromLanguage(chosenLang).getLanguage();

        Cookie langCookie = new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);
        langCookie.setMaxAge(COOKIE_MAX_AGE_21_DAY);
        langCookie.setPath(request.getContextPath());
        response.addCookie(langCookie);

        request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLang);

        Router currentRouter = new Router();
        currentRouter.setPagePath(getRedirectPage(request));
        currentRouter.setRouteType(Router.RouteType.REDIRECT);
        return currentRouter;
    }

    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(JSPAttributeStorage.LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS);
        if (!StringUtils.isBlank(redirectPage)) {
            return request.getContextPath() + request.getServletPath() + "?" + redirectPage;
        }
        return request.getParameter(JSPAttributeStorage.LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL);
    }
}
