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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.core.shared.domain.UserSNetVisibility;

import com.google.inject.Inject;

public class UserSNVisibilityMenuItem extends MenuRadioItemDescriptor {

  private static final String USER_BUDDIES_VISIBILITY_GROUP = "k-sn-userbuddies-vis";

  @Inject
  public UserSNVisibilityMenuItem(final MenuDescriptor parent, final UserSNVisibilityAction action) {
    super(parent, action, USER_BUDDIES_VISIBILITY_GROUP);
  }

  public MenuRadioItemDescriptor withVisibility(final UserSNetVisibility visibility) {
    ((UserSNVisibilityAction) getAction()).setVisibility(visibility);
    return this;
  }

}