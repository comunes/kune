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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class IconLabelDescriptor extends AbstractGuiActionDescrip {

  public IconLabelDescriptor(final AbstractAction action) {
    super(action);
  }

  public IconLabelDescriptor(final String text) {
    this(text, "");
  }

  public IconLabelDescriptor(final String text, final ImageResource icon) {
    this(text, null, icon);
  }

  public IconLabelDescriptor(final String text, final String tooltip) {
    this(text, tooltip, "");
  }

  public IconLabelDescriptor(final String text, final String tooltip, final ImageResource icon) {
    this(new BaseAction(text, tooltip, icon));
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
    putValue(Action.SMALL_ICON, icon);
  }

  public IconLabelDescriptor(final String text, final String tooltip, final String icon) {
    this(new BaseAction(text, tooltip, icon));
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
    putValue(Action.SMALL_ICON, icon);
  }

  @Override
  public Class<?> getType() {
    return IconLabelDescriptor.class;
  }

}
