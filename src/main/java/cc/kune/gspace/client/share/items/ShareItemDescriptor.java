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
package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;

public class ShareItemDescriptor {
  public static final String GROUP = "share-item-group";
  public static final String ITEM = "share-item";
  private boolean isMe;
  private Object itemIcon;
  private String itemText;
  private final MenuItemDescriptor[] menuItems;
  private final String menuText;

  public ShareItemDescriptor(final Object itemIcon, final String itemText, final String menuText,
      final MenuItemDescriptor... menuItems) {
    this.setItemIcon(itemIcon);
    this.setItemText(itemText);
    this.menuText = menuText;
    this.menuItems = menuItems;
  }

  public ShareItemDescriptor(final String menuText, final MenuItemDescriptor... menuItems) {
    this.menuText = menuText;
    this.menuItems = menuItems;
  }

  public Object getItemIcon() {
    return itemIcon;
  }

  public String getItemText() {
    return itemText;
  }

  public MenuItemDescriptor[] getMenuItems() {
    return menuItems;
  }

  public String getMenuText() {
    return menuText;
  }

  public boolean isMe() {
    return isMe;
  }

  public void setGroup(final String group) {
    for (final MenuItemDescriptor item : menuItems) {
      item.getAction().putValue(GROUP, group);
    }
  }

  public void setIsMe(final boolean isMe) {
    this.isMe = isMe;
  }

  public void setItemIcon(final Object itemIcon) {
    this.itemIcon = itemIcon;
  }

  public void setItemText(final String itemText) {
    this.itemText = itemText;
  }

  public void setTarget(final Object target) {
    for (final MenuItemDescriptor item : menuItems) {
      item.getAction().putValue(ITEM, target);
    }
  }

}
