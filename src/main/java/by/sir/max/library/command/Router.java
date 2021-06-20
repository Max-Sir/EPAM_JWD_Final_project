package by.sir.max.library.command;

public class Router {
    public enum RouteType {
        FORWARD,
        REDIRECT
    }

    private RouteType routeType;
    private String pagePath;

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        if (routeType == null) {
            routeType = RouteType.REDIRECT;
        }
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}
