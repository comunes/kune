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
package cc.kune.core.server.rack.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public final class RackHelper {
  public static String buildForwardString(final ServletRequest request, final String forward) {
    final String parameters = RackHelper.extractParameters(request);
    return new StringBuilder(forward).append(parameters).toString();
  }

  public static String extractParameters(final ServletRequest request) {
    final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    final String uri = httpServletRequest.getRequestURI();
    final int index = uri.indexOf('?');
    if (index > 0) {
      return uri.substring(index);
    } else {
      return "";
    }
  }

  public static String getMethodName(final ServletRequest request, final Pattern pattern) {
    final String relativeURL = RackHelper.getRelativeURL(request);
    final Matcher matcher = pattern.matcher(relativeURL);
    matcher.find();
    final String methodName = matcher.group(1);
    return methodName;
  }

  public static String getRelativeURL(final ServletRequest request) {
    final HttpServletRequest req = (HttpServletRequest) request;
    final String contextPath = req.getContextPath();
    final String uri = req.getRequestURI();
    return uri.substring(contextPath.length());
  }

  public static String getURI(final ServletRequest request) {
    final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    return httpServletRequest.getRequestURI();
  }

  private RackHelper() {
  }

}
