/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerException extends RuntimeException {

  private static final Log LOG = LogFactory.getLog(ServerException.class);

  private static final long serialVersionUID = 2028618233098694418L;

  public ServerException() {
    super();
  }

  public ServerException(final String text) {
    super(text);
    LOG.error(text);
  }

  public ServerException(final String text, final Throwable cause) {
    super(text, cause);
    LOG.error(text, cause);
  }

  public ServerException(final Throwable cause) {
    super(cause);
    LOG.error("ServerException", cause);
  }

}
