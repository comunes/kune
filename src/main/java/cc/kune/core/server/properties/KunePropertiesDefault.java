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
package cc.kune.core.server.properties;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import cc.kune.core.server.ServerException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KunePropertiesDefault implements KuneProperties {
  private final String fileName;
  private Properties properties;

  @Inject
  public KunePropertiesDefault(@PropertiesFileName final String fileName) {
    this.fileName = fileName;
    try {
      properties = new Properties();
      final InputStream input = getInputStream(fileName);
      properties.load(input);
    } catch (final IOException e) {
      final String msg = MessageFormat.format("Couldn't open property file {0}", fileName);
      throw new ServerException(msg, e);
    }

  }

  @Override
  public String get(final String key) {
    final String value = properties.getProperty(key);
    if (value == null) {
      throw new ServerException("PROPERTY: " + key + " not defined in " + fileName);
    }
    return value;
  }

  @Override
  public String get(final String key, final String defaultValue) {
    final String value = properties.getProperty(key);
    return value != null ? value : defaultValue;
  }

  private InputStream getInputStream(final String fileName) {
    final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        fileName);
    if (input == null) {
      final String msg = MessageFormat.format("Properties file: ''{0}'' not found", fileName);
      throw new ServerException(msg);
    }
    return input;
  }

}
