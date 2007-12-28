/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.ourproject.kune.platf.server.access.AccessType;

import com.google.inject.BindingAnnotation;

/**
 * Use in RPC methods, for instance: <code>
 * Authorizated(authLevelRequired = AuthLevelRequired.COLLAB, checkContent = true) </code>
 * 
 * The first parameter in the method must be the userHash, the second the group
 * shortName, and if you want to check also the content access, the 3rd
 * parameter must be a content id (string)
 * 
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorizated {

    AccessType accessTypeRequired() default AccessType.READ;

    boolean checkContent() default false;

}
