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

import cc.kune.core.server.persist.KuneTransactional;

import com.google.inject.Inject;

public class TransactionalServiceExecutor {
  private final RESTMethodFinder methodFinder;
  private final RESTSerializer serializer;

  @Inject
  public TransactionalServiceExecutor(final RESTMethodFinder methodFinder,
      final RESTSerializer serializer) {
    this.methodFinder = methodFinder;
    this.serializer = serializer;
  }

  @KuneTransactional
  public RESTResult doService(final Class<?> serviceClass, final String methodName,
      final ParametersAdapter parameters, final Object serviceInstance) {
    String output = null;
    final RESTMethod rest = methodFinder.findMethod(methodName, parameters, serviceClass);
    RESTResult result = null;
    if (rest != null) {
      result = rest.invoke(serviceInstance);
      if (result.isSuccess()) {
        output = serializer.serialize(rest.getResponse(), rest.getFormat());
        result.setOutput(output);
      }
    }
    return result;
  }
}
