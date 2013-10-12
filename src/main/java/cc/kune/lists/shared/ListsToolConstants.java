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
package cc.kune.lists.shared;

import cc.kune.common.shared.res.KuneIcon;

public final class ListsToolConstants {

  public static final KuneIcon ICON_TYPE_LIST = new KuneIcon('o');
  public static final KuneIcon ICON_TYPE_POST = new KuneIcon('k');
  public static final KuneIcon ICON_TYPE_ROOT = new KuneIcon('o');
  public static final String ROOT_NAME = "lists";
  public static final String TOOL_NAME = "lists";
  public static final String TYPE_LIST = TOOL_NAME + "." + "list";
  public static final String TYPE_POST = TOOL_NAME + "." + "post";
  public static final String TYPE_ROOT = TOOL_NAME + "." + "root";

  private ListsToolConstants() {
  }
}
