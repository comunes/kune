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
package cc.kune.core.shared.dto;

import java.util.Arrays;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceTheme.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceTheme implements IsSerializable {

  /** The back colors. */
  private String[] backColors;

  /** The colors. */
  private String[] colors;

  /** The name. */
  private String name;

  /**
   * Instantiates a new g space theme.
   */
  public GSpaceTheme() {
  }

  /**
   * Instantiates a new g space theme.
   * 
   * @param name
   *          the name
   */
  public GSpaceTheme(final String name) {
    this.name = name;
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
    final GSpaceTheme other = (GSpaceTheme) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the back colors.
   * 
   * @return the back colors
   */
  public String[] getBackColors() {
    return backColors;
  }

  /**
   * Gets the colors.
   * 
   * @return the colors
   */
  public String[] getColors() {
    return colors;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
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
    return result;
  }

  /**
   * Sets the back colors.
   * 
   * @param backColors
   *          the new back colors
   */
  public void setBackColors(final String[] backColors) {
    this.backColors = backColors;
  }

  /**
   * Sets the colors.
   * 
   * @param colors
   *          the new colors
   */
  public void setColors(final String[] colors) {
    this.colors = colors;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GSpaceTheme [backColors=" + Arrays.toString(backColors) + ", colors="
        + Arrays.toString(colors) + ", name=" + name + "]";
  }

}
