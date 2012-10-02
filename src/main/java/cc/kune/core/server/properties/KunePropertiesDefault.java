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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.error.ServerException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KunePropertiesDefault implements KuneProperties {
  public static final Log LOG = LogFactory.getLog(KunePropertiesDefault.class);
  private CompositeConfiguration config;
  private final String fileName;

  @Inject
  public KunePropertiesDefault(final String fileName) {
    this.fileName = fileName;
    // http://stackoverflow.com/questions/40376/handle-signals-in-the-java-virtual-machine
    // Runtime.getRuntime().addShutdownHook() ??
    // Gives some warning in openjdk
    // http://stackoverflow.com/questions/5023520/sending-signals-to-a-running-jvm
    //
    // sun.misc.Signal.handle(new sun.misc.Signal("HUP"), new SignalHandler() {
    // @Override
    // public void handle(final sun.misc.Signal sig) {
    // LOG.warn("Received SIGHUP signal. Will reload kune.properties");
    // loadConfiguration();
    // }
    // });
    loadConfiguration();
  }

  private void checkNull(final String key, final Object value) {
    if (value == null) {
      throw new ServerException(
          String.format("PROPERTY '%s' not defined in file %s. Please updated this configuration file.",
              key, fileName));
    }
  }

  @Override
  public boolean has(final String key) {
    return config.getString(key) != null;
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
  public boolean getBoolean(final String key) {
    final Boolean value = config.getBoolean(key);
    checkNull(key, value);
    return value;
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

  private void loadConfiguration() {
    try {
      config = new CompositeConfiguration();
      config.addConfiguration(new SystemConfiguration());
      config.addConfiguration(new PropertiesConfiguration(fileName));
    } catch (final ConfigurationException e) {
      final String msg = MessageFormat.format("Couldn't open property file {0}", fileName);
      throw new ServerException(msg, e);
    }
  }

}
