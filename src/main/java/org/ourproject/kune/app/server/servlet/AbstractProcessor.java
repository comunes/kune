/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Injector;

public abstract class AbstractProcessor implements SimpleFilter {
    public static final Log log = LogFactory.getLog(AbstractProcessor.class);
    protected ServletContext servletContext;
    private final String[] validMethods;
    protected Injector injector;

    public AbstractProcessor(final String... validMethods) {
	this.validMethods = validMethods;
    }

    public void init(final ServletContext servletContext, final Injector injector) {
	this.servletContext = servletContext;
	this.injector = injector;
    }

    public void destroy() {
    }

    public ServletContext getServletContext() {
	return servletContext;
    }

    public Injector getInjector() {
	return injector;
    }

    public boolean doFilter(final HttpServletRequest req, final HttpServletResponse response) throws IOException,
	    ServletException {

	if (isValidMethod(req)) {
	    String contextPath = req.getContextPath();
	    String uri = req.getRequestURI();
	    String relativeUrl = uri.substring(contextPath.length());
	    return process(relativeUrl, req, response);
	}
	return false;
    }

    private boolean isValidMethod(final HttpServletRequest req) {
	String method = req.getMethod();
	for (String valid : validMethods) {
	    if (valid.equals(method)) {
		return true;
	    }
	}
	return false;
    }

    protected abstract boolean process(final String relativeUrl, final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException, ServletException;

}
