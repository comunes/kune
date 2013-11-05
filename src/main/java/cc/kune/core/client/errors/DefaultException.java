/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
 \*/
package cc.kune.core.client.errors;

import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.IsSerializable;

public class DefaultException extends InvocationException implements IsSerializable {

  private static final long serialVersionUID = -6111471089427505005L;

  public DefaultException() {
    this(0, "");
  }

  public DefaultException(final int statusCode, final String message) {
    super(statusCode + " " + message);

  }

  public DefaultException(final String message) {
    super(message);
  }

  public DefaultException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DefaultException(final Throwable cause) {
    super("", cause);
  }
}
