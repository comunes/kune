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
 * The Class MenuRadioItemDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MenuRadioItemDescriptor extends MenuCheckItemDescriptor {

  /** The Constant CHECKED_ITEM. */
  public static final String CHECKED_ITEM = "-checkeditem";
  
  /** The group. */
  private final String group;

  /**
   * Instantiates a new menu radio item descriptor.
   *
   * @param parent the parent
   * @param action the action
   * @param group the group
   */
  public MenuRadioItemDescriptor(final MenuDescriptor parent, final AbstractAction action,
      final String group) {
    super(parent, action);
    this.group = group;
  }

  /**
   * Gets the group.
   *
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor#getType()
   */
  @Override
  public Class<?> getType() {
    return MenuRadioItemDescriptor.class;
  }

  /**
   * Previous checked item id.
   *
   * @return the string
   */
  private String previousCheckedItemId() {
    return group + CHECKED_ITEM;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor#setChecked(boolean)
   */
  @Override
  public void setChecked(final boolean checked) {
    if (checked) {
      final MenuRadioItemDescriptor previous = (MenuRadioItemDescriptor) parent.getValue(previousCheckedItemId());
      if (previous != null) {
        previous.setChecked(false);
      }
      parent.putValue(previousCheckedItemId(), this);
    }
    super.setChecked(checked);
  }

}
