package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SimpleFilter {
    public static final String POST = "POST";
    public static final String GET = "GET";

    boolean doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException,
	    ServletException;

    void init(FilterConfig config);

    void destroy();

}
