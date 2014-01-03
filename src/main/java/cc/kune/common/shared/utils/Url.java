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

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Url.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class Url {
  
  /** The base. */
  private final String base;
  
  /** The params. */
  private final ArrayList<UrlParam> params;

  /**
   * Instantiates a new url.
   *
   * @param base the base
   */
  public Url(final String base) {
    this.base = base;
    params = new ArrayList<UrlParam>();
  }

  /**
   * Instantiates a new url.
   *
   * @param base the base
   * @param iniParams the ini params
   */
  public Url(final String base, final UrlParam... iniParams) {
    this(base);
    for (final UrlParam param : iniParams) {
      addImpl(param);
    }
  }

  /**
   * Adds the.
   *
   * @param param the param
   */
  public void add(final UrlParam param) {
    addImpl(param);
  }

  /**
   * Adds the impl.
   *
   * @param param the param
   */
  private void addImpl(final UrlParam param) {
    params.add(param);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String paramPart = "";
    boolean first = true;
    for (final UrlParam param : params) {
      if (first) {
        paramPart = "?" + param;
        first = false;
      } else {
        paramPart += "&" + param;
      }
    }
    return base + paramPart;
  }

}
