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
package cc.kune.core.server.searcheable;

import cc.kune.core.server.mbean.MBeanConstants;

// TODO: Auto-generated Javadoc
/**
 * MBean interface for JMX management of the {@link SearchEngineServletFilter}.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SearchEngineServletFilterMBean {

  /** The Constant MBEAN_OBJECT_NAME. */
  public static final String MBEAN_OBJECT_NAME = MBeanConstants.PREFIX + "SearchEngineServletFilter";

  /**
   * Clear the htmlunit cache.
   */
  void clearCache();

  /**
   * Client close all windows (htmlunit).
   */
  void closeAllWindows();

  /**
   * Gets the cache max size.
   * 
   * @return the cache max size
   */
  int getCacheMaxSize();

  /**
   * Gets the cache size.
   * 
   * @return the cache size
   */
  int getCacheSize();

  /**
   * Gets the executor thread size.
   * 
   * @return the executor thread size
   */
  int getExecutorThreadSize();

  /**
   * Inits the web client (useful if htmlunit has some memory leak).
   */
  void initWebClient();

  /**
   * Checks if is enabled.
   * 
   * @return true, if is enable
   */
  boolean isEnabled();

  /**
   * Sets the max cache size (maximum number of files in cache).
   * 
   * @param size
   *          the new cache size
   */
  void setCacheMaxSize(int size);

  /**
   * Enable or disable search functionality.
   * 
   * @param enabled
   *          the new enabled
   */
  void setEnabled(boolean enabled);

  /**
   * Sets the executor thread size.
   * 
   * @param size
   *          the new executor thread size
   */
  void setExecutorThreadSize(int size);

}