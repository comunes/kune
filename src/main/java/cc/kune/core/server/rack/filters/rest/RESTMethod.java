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

import java.lang.reflect.Method;

import cc.kune.core.server.error.ServerException;

public class RESTMethod {
  public static final String FORMAT_JSON = "json";
  public static final String FORMAT_XML = "xml";

  private final String format;
  private final Method method;
  private final String[] names;
  private final Parameters parameters;
  private Object response;

  public RESTMethod(final Method method, final String[] names, final Parameters parameters,
      final String format) {
    this.method = method;
    this.names = names;
    this.parameters = parameters;
    this.format = format;
  }

  private Object convert(final Class<?> type, final String stringValue) {
    if (type.equals(String.class)) {
      return stringValue;
    } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
      return Integer.parseInt(stringValue);
    } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
      return Long.parseLong(stringValue);
    } else {
      throw new ServerException("unable to convert parameter in JSON method to type: " + type);
    }
  }

  private Object[] convertParameters() {
    final int total = names.length;
    final Object[] values = new Object[total];
    final Class<?>[] types = method.getParameterTypes();

    for (int index = 0; index < total; index++) {
      values[index] = convert(types[index], parameters.get(names[index]));
    }

    return values;
  }

  public String getFormat() {
    return format;
  }

  public Object getResponse() {
    return response;
  }

  public RESTResult invoke(final Object service) {
    final Object[] values = convertParameters();
    try {
      response = method.invoke(service, values);
      return RESTResult.build(true);
    } catch (final Exception e) {
      return RESTResult.build(false, e);
    }
  }

}
