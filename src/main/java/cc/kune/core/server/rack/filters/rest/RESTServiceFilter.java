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
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.rack.filters.AbstractInjectedFilter;
import cc.kune.core.server.rack.utils.RackHelper;

import com.google.inject.Inject;

public class RESTServiceFilter extends AbstractInjectedFilter {
  private static final Log LOG = LogFactory.getLog(RESTServiceFilter.class);

  private final boolean jsonpEnabled = false;
  private final Pattern pattern;
  private final Class<?> serviceClass;

  @Inject
  private TransactionalServiceExecutor transactionalFilter;

  public RESTServiceFilter(final String pattern, final Class<?> serviceClass) {
    this.serviceClass = serviceClass;
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {

    final String methodName = RackHelper.getMethodName(request, pattern);
    final ParametersAdapter parameters = new ParametersAdapter(request);
    LOG.debug("JSON METHOD: '" + methodName + "' on: " + serviceClass.getSimpleName());

    response.setCharacterEncoding("utf-8");

    final String callbackMethod = getCallbackMethod(request);
    final boolean isJsonP = isJSONPRequest(callbackMethod);

    response.setContentType(isJsonP ? "text/javascript" : "text/json");

    final RESTResult result = transactionalFilter.doService(serviceClass, methodName, parameters,
        getInstance(serviceClass));
    if (result != null) {
      if (result.getOutput() != null) {
        final Object output = wrap(result.getOutput(), isJsonP, callbackMethod);
        if (output != null) {
          final PrintWriter writer = response.getWriter();
          writer.print(output);
          writer.flush();
        } else {
          chain.doFilter(request, response);
        }

      } else {
        chain.doFilter(request, response);
      }
    }
  }

  private String getCallbackMethod(final ServletRequest httpRequest) {
    return httpRequest.getParameter("callback");
  }

  @SuppressWarnings("unused")
  private boolean isJSONPRequest(final String callbackMethod) {
    return jsonpEnabled && (callbackMethod != null && callbackMethod.length() > 0);
  }

  private String wrap(final String output, final boolean isJsonP, final String callbackMethod) {
    if (!isJsonP) {
      return output;
    } else {
      final StringBuffer wrapped = new StringBuffer();
      wrapped.append(callbackMethod);
      wrapped.append("(");
      wrapped.append(output);
      wrapped.append(");");
      return wrapped.toString();
    }
  }

}
