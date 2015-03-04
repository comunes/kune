/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.constants.IconType;

public abstract class AbstractCheckListItem extends ComplexAnchorListItem {

  private final IconType checkedIcon;
  private final IconType uncheckedIcon;

  public AbstractCheckListItem(final IconType checked, final IconType unchecked) {
    this.checkedIcon = checked;
    this.uncheckedIcon = unchecked;
    super.setIcon(unchecked);
  }

  public AbstractCheckListItem(final IconType checked, final IconType unchecked, final String text) {
    this(checked, unchecked);
    setText(text);
  }

  public boolean isChecked() {
    return getIcon().equals(checkedIcon);
  }

  public void setChecked(final boolean checked) {
    super.setIcon(checked ? checkedIcon : uncheckedIcon);
  }

  @Override
  public void setIcon(final IconType iconType) {
    // We override this to avoid the setting of another icons
  }
}
