/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under 
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public 
 * License version 3, (the "License"); you may not use this file except in 
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.rack.filters.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.server.rack.RackServletFilter;
import cc.kune.core.server.rack.utils.RackHelper;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class CORSServiceFilter extends AbstractCustomCORSFilter {
  private static final Log LOG = LogFactory.getLog(CORSServiceFilter.class);
  protected ServletContext ctx;
  private final Pattern pattern;

  private final Class<?> serviceClass;

  @Inject
  private TransactionalServiceExecutor transactionalFilter;

  public CORSServiceFilter(final String pattern, final Class<?> serviceClass) {
    this.serviceClass = serviceClass;
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  protected void customDoFilter(final HttpServletRequest request, final HttpServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    final boolean cors = (Boolean) request.getAttribute("cors.isCorsRequest");

    // This part is similar to RESTServiceFilter
    final String methodName = RackHelper.getMethodName(request, pattern);
    final ParametersAdapter parameters = new ParametersAdapter(request);
    LOG.debug((cors ? "" : "NO ") + "CORS METHOD: '" + methodName + "' on: "
        + serviceClass.getSimpleName());

    response.setCharacterEncoding("utf-8");

    // See: http://software.dzhuvinov.com/cors-filter-tips.html
    response.setContentType("text/plain");

    final RESTResult result = transactionalFilter.doService(serviceClass, methodName, parameters,
        getInstance(serviceClass));
    if (result != null) {
      final Exception exception = result.getException();
      if (exception != null) {
        if (exception instanceof InvocationTargetException
            && ((InvocationTargetException) exception).getTargetException() instanceof ContentNotFoundException) {
          printMessage(response, HttpServletResponse.SC_NOT_FOUND, result.getException().getMessage());
        } else {
          printMessage(response, HttpServletResponse.SC_BAD_REQUEST, result.getException().getMessage());
        }
      } else {
        final String output = result.getOutput();
        if (output != null) {
          final PrintWriter writer = response.getWriter();
          writer.print(output);
          writer.flush();
        } else {
          // Is not for us!!!
        }
      }
    }
  }

  @Override
  public void destroy() {
  }

  private Injector getInjector() {
    return (Injector) ctx.getAttribute(RackServletFilter.INJECTOR_ATTRIBUTE);
  }

  public <T> T getInstance(final Class<T> type) {
    return getInjector().getInstance(type);
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
    this.ctx = filterConfig.getServletContext();
    getInjector().injectMembers(this);
  }

}
