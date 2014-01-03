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

package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sn.actions.GroupSNOptionsMenu;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupInvitationMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class GroupInvitationMenuItem extends MenuItemDescriptor {

  /**
   * Instantiates a new group invitation menu item.
   * 
   * @param action
   *          the action
   * @param icons
   *          the icons
   * @param optionsMenu
   *          the options menu
   */
  @Inject
  GroupInvitationMenuItem(final GroupInvitationAction action, final IconicResources icons,
      final GroupSNOptionsMenu optionsMenu) {
    super(action);
    withText(I18n.t("Invite others to this group via email")).withIcon(icons.listsPostGrey());
    setParent(optionsMenu);
    setPosition(0);
  }
}
