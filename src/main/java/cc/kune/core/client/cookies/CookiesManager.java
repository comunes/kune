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
package cc.kune.core.client.cookies;

// TODO: Auto-generated Javadoc
/**
 * The Interface CookiesManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface CookiesManager {

  /**
   * Gets the anon cookie.
   * 
   * @return the anon cookie
   */
  String getAnonCookie();

  /**
   * Gets the auth cookie.
   * 
   * @return the auth cookie
   */
  String getAuthCookie();

  /**
   * Removes the anon cookie.
   */
  void removeAnonCookie();

  /**
   * Removes the auth cookie.
   */
  void removeAuthCookie();

  /**
   * Sets the anon cookie.
   * 
   * @param userRegister
   *          the new anon cookie
   */
  void setAnonCookie(Boolean userRegister);

  /**
   * Sets the auth cookie.
   * 
   * @param userHash
   *          the new auth cookie
   */
  void setAuthCookie(String userHash);

}
