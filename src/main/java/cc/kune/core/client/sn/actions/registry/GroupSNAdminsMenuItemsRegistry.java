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
package cc.kune.core.client.sn.actions.registry;

import cc.kune.chat.client.actions.conditions.IsBuddieCondition;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.sn.actions.AcceptJoinGroupAction;
import cc.kune.core.client.sn.actions.ChangeToAdminAction;
import cc.kune.core.client.sn.actions.ChangeToCollabAction;
import cc.kune.core.client.sn.actions.DenyJoinGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.sn.actions.GotoMemberAction;
import cc.kune.core.client.sn.actions.GotoYourHomePageAction;
import cc.kune.core.client.sn.actions.RemoveMemberAction;
import cc.kune.core.client.sn.actions.WriteToAction;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupSNAdminsMenuItemsRegistry extends AbstractSNMembersActionsRegistry {

  @Inject
  public GroupSNAdminsMenuItemsRegistry(final Session session, final I18nTranslationService i18n,
      final IsLoggedCondition isLogged, final IsCurrentStateAdministrableCondition isAdministrable,
      final IsPersonCondition isPerson, final IsGroupCondition isGroup,
      final IsBuddieCondition isBuddie, final IsMeCondition isMe, final IsNotMeCondition isNotMe,
      final ChangeToCollabAction changeToCollabAction, final ChangeToAdminAction changeToAdminAction,
      final RemoveMemberAction removeMemberAction, final AcceptJoinGroupAction acceptJoinGroupAction,
      final DenyJoinGroupAction denyJoinGroupAction, final GotoGroupAction gotoGroupAction,
      final GotoMemberAction gotoMemberAction, final GotoYourHomePageAction gotoYourHomePageAction,
      final WriteToAction writeToAction) {
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoMemberAction);
        item.add(isPerson);
        item.add(isNotMe);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoYourHomePageAction);
        item.add(isPerson);
        item.add(isMe);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(writeToAction);
        item.add(isPerson);
        item.withText(i18n.t("Write to your buddy"));
        item.add(isNotMe);
        item.add(isBuddie);
        item.add(isLogged);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoGroupAction);
        item.add(isGroup);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(changeToCollabAction);
        item.add(isAdministrable);
        item.add(isNotMe);
        item.add(isLogged);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(removeMemberAction);
        item.add(isAdministrable);
        item.add(isNotMe);
        item.add(isLogged);
        return item;
      }
    });
  }
}
