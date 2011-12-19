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
package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;

import com.google.inject.Inject;

public class StartAssemblyWithMembers extends MenuItemDescriptor {

  @Inject
  public StartAssemblyWithMembers(final StartAssemblyWithMembersAction action,
      final I18nTranslationService i18n) {
    super(action);
    action.setInviteMembers(true);
    withText(i18n.t("Start a public assembly with members")).withToolTip(
        i18n.t("Enter to this group public chat room and invite members"));
    setParent(GroupSNConfActions.OPTIONS_MENU);
    setPosition(0);
  }
}
