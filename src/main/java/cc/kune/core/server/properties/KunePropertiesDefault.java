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

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import cc.kune.core.server.ServerException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KunePropertiesDefault implements KuneProperties {
  private CompositeConfiguration config;
  private final String fileName;

  @Inject
  public KunePropertiesDefault(@PropertiesFileName final String fileName) {
    this.fileName = fileName;
    try {
      config = new CompositeConfiguration();
      config.addConfiguration(new SystemConfiguration());
      config.addConfiguration(new PropertiesConfiguration(fileName));
    } catch (final ConfigurationException e) {
      final String msg = MessageFormat.format("Couldn't open property file {0}", fileName);
      throw new ServerException(msg, e);
    }
  }

  private void checkNull(final String key, final Object value) {
    if (value == null) {
      throw new ServerException("PROPERTY: " + key + " not defined in " + fileName);
    }
  }

  @Override
  public String get(final String key) {
    final String value = config.getString(key);
    checkNull(key, value);
    return value;
  }

  @Override
  public String get(final String key, final String defaultValue) {
    final String value = config.getString(key);
    return value != null ? value : defaultValue;
  }

  @Override
  public Integer getInteger(final String key) {
    final Integer value = config.getInt(key);
    checkNull(key, value);
    return value;
  }

  @Override
  public List<String> getList(final String key) {
    @SuppressWarnings("unchecked")
    final List<String> value = config.getList(key);
    checkNull(key, value);
    return value;
  }

  @Override
  public Long getLong(final String key) {
    final Long value = config.getLong(key);
    checkNull(key, value);
    return value;
  }

}
