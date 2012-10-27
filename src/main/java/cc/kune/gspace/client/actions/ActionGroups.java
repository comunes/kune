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
package cc.kune.gspace.client.actions;

/**
 * A group of actions that must be grouped and showed in some perspective (on
 * edit, etc).
 */
public final class ActionGroups {

  /**
   * The Constant BOTTOMBAR identifies actions in the bottom bar, below the
   * content.
   */
  public final static String BOTTOMBAR = "bottombar";

  /**
   * The Constant ITEM_MENU identifies actions in the menu of each item of a
   * folder list.
   */
  public final static String ITEM_MENU = "menu-item";

  /** The Constant TOPBAR identifies actions in the top bar, above the content. */
  public final static String TOPBAR = "topbar";

  /**
   * Instantiates a new action groups.
   */
  private ActionGroups() {
  }
}
