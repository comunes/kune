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
package cc.kune.core.client.sn.actions.registry;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.sn.actions.AcceptJoinGroupAction;
import cc.kune.core.client.sn.actions.ChangeToAdminAction;
import cc.kune.core.client.sn.actions.ChangeToCollabAction;
import cc.kune.core.client.sn.actions.DenyJoinGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.sn.actions.GotoPersonAction;
import cc.kune.core.client.sn.actions.GotoYourHomePageAction;
import cc.kune.core.client.sn.actions.RemoveMemberAction;
import cc.kune.core.client.sn.actions.UnJoinFromThisGroupAction;
import cc.kune.core.client.sn.actions.WriteToAction;
import cc.kune.core.client.sn.actions.conditions.ImPartOfGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsBuddieCondition;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.shared.SessionConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSNMenuItemsRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SuppressWarnings("serial")
public class UserSNMenuItemsRegistry extends AbstractSNMembersActionsRegistry {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new user sn menu items registry.
   * 
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param isAdministrableCondition
   *          the is administrable condition
   * @param isPerson
   *          the is person
   * @param isGroup
   *          the is group
   * @param imPartOfGroup
   *          the im part of group
   * @param isLogged
   *          the is logged
   * @param isBuddie
   *          the is buddie
   * @param isMe
   *          the is me
   * @param isNotMe
   *          the is not me
   * @param changeToCollabAction
   *          the change to collab action
   * @param changeToAdminAction
   *          the change to admin action
   * @param removeMemberAction
   *          the remove member action
   * @param acceptJoinGroupAction
   *          the accept join group action
   * @param denyJoinGroupAction
   *          the deny join group action
   * @param gotoGroupAction
   *          the goto group action
   * @param gotoPersonAction
   *          the goto person action
   * @param unjoinAction
   *          the unjoin action
   * @param writeToAction
   *          the write to action
   * @param writeToActionOnlyAdmins
   *          the write to action only admins
   * @param gotoYourHomePageAction
   *          the goto your home page action
   */
  @Inject
  public UserSNMenuItemsRegistry(final SessionConstants session, final I18nTranslationService i18n,
      final IsCurrentStateAdministrableCondition isAdministrableCondition,
      final IsPersonCondition isPerson, final IsGroupCondition isGroup,
      final ImPartOfGroupCondition imPartOfGroup, final IsLoggedCondition isLogged,
      final IsBuddieCondition isBuddie, final IsMeCondition isMe, final IsNotMeCondition isNotMe,
      final ChangeToCollabAction changeToCollabAction, final ChangeToAdminAction changeToAdminAction,
      final RemoveMemberAction removeMemberAction, final AcceptJoinGroupAction acceptJoinGroupAction,
      final DenyJoinGroupAction denyJoinGroupAction, final GotoGroupAction gotoGroupAction,
      final GotoPersonAction gotoPersonAction, final UnJoinFromThisGroupAction unjoinAction,
      final WriteToAction writeToAction, final WriteToAction writeToActionOnlyAdmins,
      final GotoYourHomePageAction gotoYourHomePageAction) {
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoPersonAction);
        item.add(isPerson);
        item.add(isNotMe);
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
        final MenuItemDescriptor item = new MenuItemDescriptor(writeToAction);
        item.add(isPerson);
        item.withText(i18n.t("Write to your buddy"));
        item.add(isBuddie);
        item.add(isNotMe);
        item.add(isLogged);
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
        final MenuItemDescriptor item = new MenuItemDescriptor(unjoinAction);
        item.add(isLogged);
        item.add(isGroup);
        item.add(imPartOfGroup);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        writeToActionOnlyAdmins.setOnlyToAdmin(true);
        final MenuItemDescriptor item = new MenuItemDescriptor(writeToActionOnlyAdmins);
        item.withText(i18n.t("Write to the administrators of this group"));
        item.add(isLogged);
        item.add(isGroup);
        item.add(imPartOfGroup);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(writeToAction);
        item.withText(i18n.t("Write to the members of this group"));
        item.add(isLogged);
        item.add(isGroup);
        item.add(imPartOfGroup);
        return item;
      }
    });
  }
}
