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
package cc.kune.core.server.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cc.kune.core.shared.domain.AccessRol;

import com.google.inject.BindingAnnotation;

// TODO: Auto-generated Javadoc
/**
 * Use in RPC methods, for instance: <code>
 * Authorizated({@link #accessRolRequired} = {@link AccessRol#Administrator}, {@link #mustCheckMembership} = true) </code>
 * 
 * The first parameter in the method must be the userHash and if you want to
 * check also the second parameter should be the token of the Content (use.
 * 
 * {@link ActionLevel#content}) or Container ({@link ActionLevel#container})
 * etc.
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorizated {
  // Why full package?
  // http://code.google.com/p/projectlombok/issues/detail?id=121

  /**
   * Access rol required.
   * 
   * @return the access rol
   */
  AccessRol accessRolRequired() default cc.kune.core.shared.domain.AccessRol.Viewer;

  /**
   * If the action is over a "group", "tool", "container" or over the content.
   * 
   * @return the action level
   */
  ActionLevel actionLevel() default cc.kune.core.server.auth.ActionLevel.content;

  /**
   * Must check membership.
   * 
   * @return true, if successful
   */
  boolean mustCheckMembership() default true;

}
