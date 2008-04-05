package org.ourproject.kune.rack;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RackHelper {
    private RackHelper() {
    }

    public static String extractParameters(final ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        int index = uri.indexOf('?');
        if (index > 0) {
            return uri.substring(index);
        } else {
            return "";
        }
    }

    public static String getRelativeURL(final ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        return uri.substring(contextPath.length());
    }

    public static String getURI(final ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getRequestURI();
    }

    public static String buildForwardString(final ServletRequest request, final String forward) {
        String parameters = RackHelper.extractParameters(request);
        return new StringBuilder(forward).append(parameters).toString();
    }

}
