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
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultRESTMethodFinder implements RESTMethodFinder {
  private static final Log LOG = LogFactory.getLog(DefaultRESTMethodFinder.class);

  private final HashMap<Class<?>, RESTServiceDefinition> definitionCache;

  public DefaultRESTMethodFinder() {
    this.definitionCache = new HashMap<Class<?>, RESTServiceDefinition>();
  }

  private boolean checkParams(final REST methodAnnotation, final Parameters parameters) {
    for (final String name : methodAnnotation.params()) {
      if (parameters.get(name) == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public RESTMethod findMethod(final String methodName, final Parameters parameters,
      final Class<?> serviceType) {
    final RESTServiceDefinition serviceDefinition = getServiceDefinition(serviceType);
    final Method[] serviceMethods = serviceDefinition.getMethods();
    LOG.debug("SERVICE METHODS: " + Arrays.toString(serviceMethods));
    for (final Method method : serviceMethods) {
      LOG.debug("CHECKING: " + method.toString());
      if (method.getName().equals(methodName)) {
        final REST methodAnnotation = method.getAnnotation(REST.class);
        if (checkParams(methodAnnotation, parameters)) {
          return new RESTMethod(method, methodAnnotation.params(), parameters, methodAnnotation.format());
        }
      }
    }
    return null;
  }

  private RESTServiceDefinition getServiceDefinition(final Class<?> serviceType) {
    RESTServiceDefinition serviceDefinition = definitionCache.get(serviceType);
    if (serviceDefinition == null) {
      serviceDefinition = new RESTServiceDefinition(serviceType);
      definitionCache.put(serviceType, serviceDefinition);
    }
    return serviceDefinition;
  }
}
