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

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

public class ToolbarDescriptor extends AbstractParentGuiActionDescrip {

  public static final String TOOLBAR_CLEAR = "menuclear";

  public static ToolbarDescriptor build() {
    return new ToolbarDescriptor();
  }

  public ToolbarDescriptor() {
    super(new BaseAction(null, null));
    super.getAction().putValue(TOOLBAR_CLEAR, false);
  }

  public void clear() {
    // Action detects changes in values, then we fire a change (whatever) to
    // fire this method in the UI
    putValue(TOOLBAR_CLEAR, !((Boolean) getValue(TOOLBAR_CLEAR)));
  }

  @Override
  public Class<?> getType() {
    return ToolbarDescriptor.class;
  }

  public void setText(final String text) {
    putValue(Action.NAME, text);
  }

}
