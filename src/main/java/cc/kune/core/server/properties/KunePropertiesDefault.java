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

// TODO: Auto-generated Javadoc
/**
 * The Class KunePropertiesDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class KunePropertiesDefault implements KuneProperties, KunePropertiesDefaultMBean {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(KunePropertiesDefault.class);

  /** The config. */
  private CompositeConfiguration config;

  /** The file name. */
  private final String fileName;

  /**
   * Instantiates a new kune properties default.
   * 
   * @param fileName
   *          the file name
   */
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

  /**
   * Check null.
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  private void checkNull(final String key, final Object value) {
    if (value == null) {
      throw new ServerException(
          String.format("PROPERTY '%s' not defined in file %s. Please updated this configuration file.",
              key, fileName));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.properties.KuneProperties#get(java.lang.String)
   */
  @Override
  public String get(final String key) {
    final String value = config.getString(key);
    checkNull(key, value);
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.properties.KuneProperties#get(java.lang.String,
   * java.lang.String)
   */
  @Override
  public String get(final String key, final String defaultValue) {
    final String value = config.getString(key);
    return value != null ? value : defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KuneProperties#getBoolean(java.lang.String)
   */
  @Override
  public boolean getBoolean(final String key) {
    final Boolean value = config.getBoolean(key);
    checkNull(key, value);
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KuneProperties#getInteger(java.lang.String)
   */
  @Override
  public Integer getInteger(final String key) {
    final Integer value = config.getInt(key);
    checkNull(key, value);
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KuneProperties#getList(java.lang.String)
   */
  @Override
  public List<String> getList(final String key) {
    @SuppressWarnings("unchecked")
    final List<String> value = config.getList(key);
    checkNull(key, value);
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KuneProperties#getLong(java.lang.String)
   */
  @Override
  public Long getLong(final String key) {
    final Long value = config.getLong(key);
    checkNull(key, value);
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KunePropertiesDefaultMBean#getProperty(java
   * .lang.String)
   */
  @Override
  public String getProperty(final String key) {
    return this.get(key, "Value doesn't exist");
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.properties.KuneProperties#has(java.lang.String)
   */
  @Override
  public boolean has(final String key) {
    return config.getString(key) != null;
  }

  /**
   * Load configuration.
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.properties.KunePropertiesDefaultMBean#reload()
   */
  @Override
  public void reload() {
    /* Don't catch any exception */
    this.loadConfiguration();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.properties.KunePropertiesDefaultMBean#setProperty(java
   * .lang.String, java.lang.String)
   */
  @Override
  public void setProperty(final String key, final String value) {
    this.config.setProperty(key, value);
  }

}
