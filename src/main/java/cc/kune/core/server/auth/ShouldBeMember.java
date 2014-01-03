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

import com.google.inject.BindingAnnotation;

// TODO: Auto-generated Javadoc
/**
 * Use in *RPC methods to check if user is a translator of the site
 * 
 * The first param in the method must be the userHash.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface ShouldBeMember {

  /**
   * Group shortname key in kune.properties (so the value of this key, will be
   * read from kune.properties and we used to search the db for this group)
   * 
   * @return the groupKuneProperty
   */
  String groupKuneProperty();

  /**
   * Rol required to perform this method.
   * 
   * @return the cc.kune.core.shared.domain. access rol
   */
  cc.kune.core.shared.domain.AccessRol rol() default cc.kune.core.shared.domain.AccessRol.Administrator;

}
