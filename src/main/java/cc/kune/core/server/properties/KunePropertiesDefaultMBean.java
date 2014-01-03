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

import cc.kune.core.server.mbean.MBeanConstants;

/**
 * MBean interface for JMX management of server properties.
 * 
 * @author pablojan <pablojan@gmail.com>
 */
public interface KunePropertiesDefaultMBean {

  /** The Constant MBEAN_OBJECT_NAME. */
  public static final String MBEAN_OBJECT_NAME = MBeanConstants.PREFIX + "KuneProperties";

  /**
   * Return a property value for the provided key.
   * 
   * @param key
   *          the key
   * @return To string value
   */
  String getProperty(String key);

  /**
   * Reloads configuration object, reading again property files.
   */
  void reload();

  /**
   * Set a property value for the provided key.
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  void setProperty(String key, String value);

}
