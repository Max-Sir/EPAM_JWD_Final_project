package by.sir.max.library.controller;

import by.sir.max.library.command.Command;
import by.sir.max.library.command.CommandStorage;
import by.sir.max.library.command.JSPAttributeStorage;
import by.sir.max.library.command.Router;
import by.sir.max.library.exception.ConnectionPoolException;
import by.sir.max.library.factory.UtilFactory;
import by.sir.max.library.pool.ConnectionPool;
import by.sir.max.library.service.impl.CommonBookComponentsCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controller", urlPatterns = {"/controller", "/jsp/controller"}, loadOnStartup = 0)
public class ServletController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ServletController.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().init();
        } catch (ConnectionPoolException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }
        CommonBookComponentsCache.getInstance().initBookComponentsCache();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
        UtilFactory.getInstance().getEmailDistributorUtil().sendEmailsIfExist();
        super.destroy();
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(JSPAttributeStorage.COMMAND);
        Command command = CommandStorage.getCommandByName(commandName);
        Router router = command.execute(request, response);
        if (router.getRouteType().equals(Router.RouteType.FORWARD)) {
            request.getRequestDispatcher(router.getPagePath()).forward(request,response);
        } else {
            response.sendRedirect(router.getPagePath());
        }
    }
}
