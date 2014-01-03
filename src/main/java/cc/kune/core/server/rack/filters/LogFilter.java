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
package cc.kune.core.server.rack.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.rack.utils.RackHelper;

public class LogFilter implements Filter {
  public static final Log LOG = LogFactory.getLog(LogFilter.class);
  private final boolean logduration = false;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    long start;
    if (logduration) {
      start = System.currentTimeMillis();
    }
    final String uri = RackHelper.getURI(request);
    LOG.debug("REQUEST: " + uri);
    chain.doFilter(request, response);
    if (logduration) {
      final long finish = System.currentTimeMillis();
      LOG.debug("Total time '" + uri + "': " + (finish - start) + " miliseconds");
    }
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
  }

}
