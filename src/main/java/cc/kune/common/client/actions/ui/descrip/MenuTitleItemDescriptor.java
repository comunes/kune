/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

public class MenuTitleItemDescriptor extends MenuItemDescriptor {

  /**
   * This GUI element only show a title (or header) in the menu, in the position
   * you add it to the menu. Useful before a radio group, etc This is the
   * preferred and more common used constructor.
   * 
   * @param parent
   *          menu
   * @param title
   *          the menu title
   */
  public MenuTitleItemDescriptor(final MenuDescriptor parent, final String title) {
    super(AbstractAction.NO_ACTION);
    putValue(Action.NAME, title);
  }

  /**
   * This GUI element only show a title (or header) in the menu, in the position
   * you add it to the menu. Useful before a radio group, etc You must define a
   * menu item with its parent menu. Only use this constructor if you'll set the
   * parent menu in the future (before render)
   * 
   * @param title
   *          the menu title
   */
  public MenuTitleItemDescriptor(final String title) {
    super(AbstractAction.NO_ACTION);
    putValue(Action.NAME, title);
  }

  @Override
  public Class<?> getType() {
    return MenuTitleItemDescriptor.class;
  }
}
