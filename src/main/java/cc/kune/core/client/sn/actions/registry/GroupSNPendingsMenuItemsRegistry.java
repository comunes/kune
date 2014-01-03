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
import cc.kune.core.client.sn.actions.AcceptJoinGroupAction;
import cc.kune.core.client.sn.actions.ChangeToAdminAction;
import cc.kune.core.client.sn.actions.ChangeToCollabAction;
import cc.kune.core.client.sn.actions.DenyJoinGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.sn.actions.GotoPersonAction;
import cc.kune.core.client.sn.actions.RemoveMemberAction;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.shared.SessionConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupSNPendingsMenuItemsRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupSNPendingsMenuItemsRegistry extends AbstractSNMembersActionsRegistry {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new group sn pendings menu items registry.
   * 
   * @param session
   *          the session
   * @param isAdministrableCondition
   *          the is administrable condition
   * @param isPersonCondition
   *          the is person condition
   * @param isGroupCondition
   *          the is group condition
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
   */
  @Inject
  public GroupSNPendingsMenuItemsRegistry(final SessionConstants session,
      final IsCurrentStateAdministrableCondition isAdministrableCondition,
      final IsPersonCondition isPersonCondition, final IsGroupCondition isGroupCondition,
      final ChangeToCollabAction changeToCollabAction, final ChangeToAdminAction changeToAdminAction,
      final RemoveMemberAction removeMemberAction, final AcceptJoinGroupAction acceptJoinGroupAction,
      final DenyJoinGroupAction denyJoinGroupAction, final GotoGroupAction gotoGroupAction,
      final GotoPersonAction gotoPersonAction) {
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(acceptJoinGroupAction);
        item.add(isAdministrableCondition);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(denyJoinGroupAction);
        item.add(isAdministrableCondition);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoPersonAction);
        item.add(isPersonCondition);
        return item;
      }
    });
    add(new Provider<MenuItemDescriptor>() {
      @Override
      public MenuItemDescriptor get() {
        final MenuItemDescriptor item = new MenuItemDescriptor(gotoGroupAction);
        item.add(isGroupCondition);
        return item;
      }
    });
  }

}
