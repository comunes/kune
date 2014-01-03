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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RESTServiceDefinition {
  private final Method[] methods;
  private final Class<?> serviceType;

  public RESTServiceDefinition(final Class<?> serviceType) {
    this.serviceType = serviceType;
    final List<Method> sorted = sortByParamsLength(getAnnotatedMethods());
    this.methods = sorted.toArray(new Method[sorted.size()]);
  }

  private List<Method> getAnnotatedMethods() {
    final Method[] allMethods = serviceType.getMethods();
    final List<Method> annotatedMethods = new ArrayList<Method>();
    for (final Method m : allMethods) {
      if (m.getAnnotation(REST.class) != null) {
        annotatedMethods.add(m);
      }
    }
    return annotatedMethods;
  }

  public Method[] getMethods() {
    return methods;
  }

  private List<Method> sortByParamsLength(final List<Method> annotatedMethods) {
    Collections.sort(annotatedMethods, new Comparator<Method>() {
      @Override
      public int compare(final Method o1, final Method o2) {
        final REST a1 = o1.getAnnotation(REST.class);
        final REST a2 = o2.getAnnotation(REST.class);
        final Integer length1 = a1.params().length;
        final Integer length2 = a2.params().length;
        return -1 * length1.compareTo(length2);
      }
    });
    return annotatedMethods;
  }

}
