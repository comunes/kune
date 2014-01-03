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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuCheckItemDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MenuCheckItemDescriptor extends MenuItemDescriptor {

  /** The Constant CHECKED. */
  public static final String CHECKED = "checked";

  /**
   * Instantiates a new menu check item descriptor.
   *
   * @param parent the parent
   * @param action the action
   */
  public MenuCheckItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
    super(parent, action);
    setCheckedImpl(false);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor#getType()
   */
  @Override
  public Class<?> getType() {
    return MenuCheckItemDescriptor.class;
  }

  /**
   * Checks if is checked.
   *
   * @return true, if is checked
   */
  public boolean isChecked() {
    return (Boolean) getValue(CHECKED);
  }

  /**
   * Sets the checked.
   *
   * @param checked the new checked
   */
  public void setChecked(final boolean checked) {
    setCheckedImpl(checked);
  }

  /**
   * Sets the checked impl.
   *
   * @param checked the new checked impl
   */
  private void setCheckedImpl(final boolean checked) {
    putValue(CHECKED, checked);
  }
}
