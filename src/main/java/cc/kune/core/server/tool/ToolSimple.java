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
package cc.kune.core.server.tool;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSimple.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSimple {

  /** The name. */
  private final String name;

  /** The root name. */
  private final String rootName;

  /**
   * Instantiates a new tool simple.
   */
  public ToolSimple() {
    this(null, null);
  }

  /**
   * Instantiates a new tool simple.
   * 
   * @param name
   *          the name
   * @param rootName
   *          the root name
   */
  public ToolSimple(final String name, final String rootName) {
    this.name = name;
    this.rootName = rootName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ToolSimple other = (ToolSimple) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (rootName == null) {
      if (other.rootName != null) {
        return false;
      }
    } else if (!rootName.equals(other.rootName)) {
      return false;
    }
    return true;
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
   * Gets the root name.
   * 
   * @return the root name
   */
  public String getRootName() {
    return rootName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((rootName == null) ? 0 : rootName.hashCode());
    return result;
  }
}
