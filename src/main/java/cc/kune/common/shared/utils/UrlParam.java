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
 \*/
package cc.kune.common.shared.utils;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class UrlParam.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UrlParam {
  
  /**
   * No cache string suffix.
   *
   * @return the string
   */
  public static String noCacheStringSuffix() {
    final String noCache = "&nocache=" + new Date().getTime();
    return noCache;
  }
  
  /** The name. */
  private final String name;

  /** The value. */
  private final String value;

  /**
   * Instantiates a new url param.
   *
   * @param name the name
   * @param value the value
   */
  public UrlParam(final String name, final boolean value) {
    this.name = name;
    this.value = value ? "true" : "false";
  }

  /**
   * Instantiates a new url param.
   *
   * @param name the name
   * @param value the value
   */
  public UrlParam(final String name, final String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return name + "=" + value;
  }

}
