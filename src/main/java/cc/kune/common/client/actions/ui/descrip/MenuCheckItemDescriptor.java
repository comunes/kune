/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class MenuCheckItemDescriptor extends MenuItemDescriptor {

  public static final String CHECKED = "checked";

  public MenuCheckItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
    super(parent, action);
    setCheckedImpl(false);
  }

  @Override
  public Class<?> getType() {
    return MenuCheckItemDescriptor.class;
  }

  public boolean isChecked() {
    return (Boolean) getValue(CHECKED);
  }

  public void setChecked(final boolean checked) {
    setCheckedImpl(checked);
  }

  private void setCheckedImpl(final boolean checked) {
    putValue(CHECKED, checked);
  }
}
