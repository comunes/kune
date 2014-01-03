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
package cc.kune.core.shared.domain.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessRights.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AccessRights implements IsSerializable {

  /** The administrable. */
  boolean administrable;

  /** The editable. */
  boolean editable;

  /** The visible. */
  boolean visible;

  /**
   * Instantiates a new access rights.
   */
  public AccessRights() {
    this(false, false, false);
  }

  /**
   * Instantiates a new access rights.
   * 
   * @param administrable
   *          the administrable
   * @param editable
   *          the editable
   * @param visible
   *          the visible
   */
  public AccessRights(final boolean administrable, final boolean editable, final boolean visible) {
    this.administrable = administrable;
    this.editable = editable;
    this.visible = visible;
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
    final AccessRights other = (AccessRights) obj;
    if (administrable != other.administrable) {
      return false;
    }
    if (editable != other.editable) {
      return false;
    }
    if (visible != other.visible) {
      return false;
    }
    return true;
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
    result = prime * result + (administrable ? 1231 : 1237);
    result = prime * result + (editable ? 1231 : 1237);
    result = prime * result + (visible ? 1231 : 1237);
    return result;
  }

  /**
   * Checks if is administrable.
   * 
   * @return true, if is administrable
   */
  public boolean isAdministrable() {
    return administrable;
  }

  /**
   * Checks if is editable.
   * 
   * @return true, if is editable
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   * Checks if is visible.
   * 
   * @return true, if is visible
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Sets the administrable.
   * 
   * @param isAdministrable
   *          the new administrable
   */
  public void setAdministrable(final boolean isAdministrable) {
    this.administrable = isAdministrable;
  }

  /**
   * Sets the editable.
   * 
   * @param isEditable
   *          the new editable
   */
  public void setEditable(final boolean isEditable) {
    this.editable = isEditable;
  }

  /**
   * Sets the visible.
   * 
   * @param isVisible
   *          the new visible
   */
  public void setVisible(final boolean isVisible) {
    this.visible = isVisible;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AccessRights[a: " + administrable + ", e: " + editable + ", v: " + visible + "]";
  }
}
