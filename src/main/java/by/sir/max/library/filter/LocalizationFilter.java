package by.sir.max.library.filter;

import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.SupportedLocaleStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "LocalizationFilter")
public class LocalizationFilter implements Filter {
    private static final String DEFAULT_CHARSET_ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        setLocalization(request, response);
        request.setCharacterEncoding(DEFAULT_CHARSET_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setLocalization(HttpServletRequest request, HttpServletResponse response) {
        Object langAttribute = request.getSession().getAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE);
        if (langAttribute == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                setLocaleToCookieAndSession(request, response);
            } else {
                String cookieLang = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE)) {
                        cookieLang = cookie.getValue();
                        request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, cookieLang);
                    }
                }
                if (cookieLang == null) {
                    setLocaleToCookieAndSession(request, response);
                }
            }
        }
    }

    private void setLocaleToCookieAndSession(HttpServletRequest request, HttpServletResponse response) {
        String currentLang = request.getLocale().getLanguage();
        Locale resultLocale = SupportedLocaleStorage.getLocaleFromLanguage(currentLang).getLocale();
        Cookie langCookie = new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLocale.getLanguage());
        response.addCookie(langCookie);
        request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, currentLang);
    }
}
