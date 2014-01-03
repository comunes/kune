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
package cc.kune.core.server.rack.filters.rest;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import cc.kune.core.server.error.ServerException;

public class JSONLibRESTSerializer implements RESTSerializer {
  private final JsonConfig config;

  public JSONLibRESTSerializer() {
    config = new JsonConfig();
    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
  }

  @Override
  public String serialize(final Object target, final String format) {
    if (format.equals(RESTMethod.FORMAT_JSON)) {
      return JSONSerializer.toJSON(target, config).toString();
    } else {
      throw new ServerException("format not implemented!");
    }
  }

}
