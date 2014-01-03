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
package cc.kune.core.server.manager.impl;

import cc.kune.core.server.error.ServerException;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerManagerException.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ServerManagerException extends ServerException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8407996943857184946L;

  /**
   * Instantiates a new server manager exception.
   */
  public ServerManagerException() {
    super();
  }

  /**
   * Instantiates a new server manager exception.
   * 
   * @param text
   *          the text
   */
  public ServerManagerException(final String text) {
    super(text);
  }

  /**
   * Instantiates a new server manager exception.
   * 
   * @param text
   *          the text
   * @param cause
   *          the cause
   */
  public ServerManagerException(final String text, final Throwable cause) {
    super(text, cause);
  }

  /**
   * Instantiates a new server manager exception.
   * 
   * @param cause
   *          the cause
   */
  public ServerManagerException(final Throwable cause) {
    super(cause);
  }

}
