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
package cc.kune.core.server.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerException.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ServerException extends RuntimeException {

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(ServerException.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2028618233098694418L;

  /**
   * Instantiates a new server exception.
   */
  public ServerException() {
    super();
  }

  /**
   * Instantiates a new server exception.
   * 
   * @param text
   *          the text
   */
  public ServerException(final String text) {
    super(text);
    LOG.error(text);
  }

  /**
   * Instantiates a new server exception.
   * 
   * @param text
   *          the text
   * @param cause
   *          the cause
   */
  public ServerException(final String text, final Throwable cause) {
    super(text, cause);
    LOG.error(text, cause);
  }

  /**
   * Instantiates a new server exception.
   * 
   * @param cause
   *          the cause
   */
  public ServerException(final Throwable cause) {
    super(cause);
    LOG.error("ServerException", cause);
  }

}
