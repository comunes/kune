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
package cc.kune.core.server;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.google.inject.matcher.AbstractMatcher;

// TODO: Auto-generated Javadoc
/**
 * The Class NotInObject.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NotInObject extends AbstractMatcher<Method> {

  /** The excluded. */
  private final List<String> excluded;

  /**
   * Instantiates a new not in object.
   */
  public NotInObject() {
    super();
    // FIXME exclude password
    excluded = Arrays.asList(new String[] { "finalize", "toString", "hashCode", "getClass", "wait",
        "equals" });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.matcher.Matcher#matches(java.lang.Object)
   */
  @Override
  public boolean matches(final Method method) {
    final String name = method.getName();
    // http://code.google.com/p/google-guice/issues/detail?id=640
    final boolean isSynth = method.isSynthetic();
    final boolean isGetter = name.startsWith("set");
    final boolean isExcluded = excluded.contains(name);
    return !isSynth && (!isGetter || !isExcluded);
  }
}
