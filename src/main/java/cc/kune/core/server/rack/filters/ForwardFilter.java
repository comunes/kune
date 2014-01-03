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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cc.kune.core.server.rack.utils.RackHelper;

public class ForwardFilter extends AbstractInjectedFilter {
  // private static final Log log = LogFactory.getLog(ForwardFilter.class);

  private final String forward;
  private final Pattern pattern;

  public ForwardFilter(final String forward) {
    this(".*", forward);
  }

  public ForwardFilter(final String pattern, final String forward) {
    this.forward = forward;
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {

    final String relativeURL = RackHelper.getRelativeURL(request);
    final Matcher matcher = pattern.matcher(relativeURL);
    matcher.matches();

    final int groupCount = matcher.groupCount();
    // log.debug("GROUP COUNT: " + groupCount);
    String replaced = forward;
    for (int index = 0; index < groupCount; index++) {
      final String tag = "{" + index + "}";
      final String group = matcher.group(index + 1);
      // log.debug("REPLACING " + tag + " WITH + " + group);
      replaced = replaced.replace(tag, group);
    }

    final String forwardString = RackHelper.buildForwardString(request, replaced);
    forward(request, response, forwardString);
  }

  private void forward(final ServletRequest request, final ServletResponse response,
      final String forwardString) throws ServletException, IOException {
    // log.debug("FORWADING TO: " + forwardString);
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    httpRequest.getRequestDispatcher(forwardString).forward(request, response);
  }

}
