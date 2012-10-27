/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class ServerManagerException extends ServerException {

  private static final long serialVersionUID = -8407996943857184946L;

  public ServerManagerException() {
    super();
  }

  public ServerManagerException(final String text) {
    super(text);
  }

  public ServerManagerException(final String text, final Throwable cause) {
    super(text, cause);
  }

  public ServerManagerException(final Throwable cause) {
    super(cause);
  }

}
