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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.core.shared.domain.UserSNetVisibility;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSNVisibilityMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSNVisibilityMenuItem extends MenuRadioItemDescriptor {

  /** The Constant USER_BUDDIES_VISIBILITY_GROUP. */
  private static final String USER_BUDDIES_VISIBILITY_GROUP = "k-sn-userbuddies-vis";

  /**
   * Instantiates a new user sn visibility menu item.
   * 
   * @param parent
   *          the parent
   * @param action
   *          the action
   */
  @Inject
  public UserSNVisibilityMenuItem(final MenuDescriptor parent, final UserSNVisibilityAction action) {
    super(parent, action, USER_BUDDIES_VISIBILITY_GROUP);
  }

  /**
   * With visibility.
   * 
   * @param visibility
   *          the visibility
   * @return the menu radio item descriptor
   */
  public MenuRadioItemDescriptor withVisibility(final UserSNetVisibility visibility) {
    ((UserSNVisibilityAction) getAction()).setVisibility(visibility);
    return this;
  }

}
