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
package cc.kune.chat.client;

import cc.kune.chat.client.actions.AddAsBuddyAction;
import cc.kune.chat.client.actions.AddAsBuddieHeaderButton;
import cc.kune.chat.client.actions.ChatClientActions;
import cc.kune.chat.client.actions.ChatSitebarActions;
import cc.kune.chat.client.actions.OpenGroupPublicChatRoomAction;
import cc.kune.chat.client.actions.StartAssemblyWithMembers;
import cc.kune.chat.client.actions.StartChatWithMemberAction;
import cc.kune.chat.client.actions.StartChatWithThisBuddyAction;
import cc.kune.chat.client.actions.StartChatWithThisPersonAction;
import cc.kune.chat.client.actions.conditions.IsBuddieCondition;
import cc.kune.chat.client.actions.conditions.IsNotBuddieCondition;
import cc.kune.chat.shared.ChatConstants;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateEditableCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.client.sn.actions.registry.GroupSNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.GroupSNPendingsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.UserSNMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatParts {

  @Inject
  public ChatParts(final Session session, final I18nTranslationService i18n,
      final Provider<ChatClientTool> clientTool, final Provider<ChatSitebarActions> chatActionsProvider,
      final Provider<AddAsBuddieHeaderButton> buddieButton,
      final Provider<GroupSNAdminsMenuItemsRegistry> snAdminsRegistry,
      final Provider<GroupSNCollabsMenuItemsRegistry> snCollabsItemsRegistry,
      final Provider<GroupSNPendingsMenuItemsRegistry> snPendingItemsRegistry,
      final Provider<GroupSNConfActions> groupConfActions, final Provider<GroupSNPresenter> groupSN,
      final Provider<UserSNMenuItemsRegistry> userItemsRegistry, final IsNotMeCondition isNotMe,
      final IsCurrentStateAdministrableCondition isAdministrableCondition,
      final IsCurrentStateEditableCondition isEditableCondition,
      final IsBuddieCondition isBuddieCondition, final IsNotBuddieCondition isNotBuddieCondition,
      final IsCurrentStateAGroupCondition isGroupCondition, final IsPersonCondition isPersonCondition,
      final Provider<AddAsBuddyAction> addAsBuddie,
      final Provider<StartChatWithMemberAction> startChatWithMemberAction,
      final IsLoggedCondition isLogged,
      final Provider<StartChatWithThisPersonAction> startChatWithPersonAction,
      final Provider<StartChatWithThisBuddyAction> startChatWithBuddieAction,
      final ChatClientActions chatActions, final Provider<StartAssemblyWithMembers> startAssembly,
      final Provider<OpenGroupPublicChatRoomAction> openGroupRoomAction,
      // final Provider<OpenGroupPublicChatRoomButton> openGroupRoom,
      final ContentViewerSelector viewerSelector, final FolderViewerPresenter folderViewer) {
    viewerSelector.register(folderViewer, true, ChatConstants.TYPE_ROOT);
    viewerSelector.register(folderViewer, true, ChatConstants.TYPE_ROOM);
    clientTool.get();
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        chatActionsProvider.get();
        final Provider<MenuItemDescriptor> startChatWithMemberItem = new Provider<MenuItemDescriptor>() {
          @Override
          public MenuItemDescriptor get() {
            final MenuItemDescriptor item = new MenuItemDescriptor(startChatWithMemberAction.get());
            item.add(isNotMe);
            item.add(isNotBuddieCondition);
            item.add(isPersonCondition);
            item.add(isLogged);
            return item;
          }
        };
        final Provider<MenuItemDescriptor> startChatWithBuddieItem = new Provider<MenuItemDescriptor>() {
          @Override
          public MenuItemDescriptor get() {
            final MenuItemDescriptor item = new MenuItemDescriptor(startChatWithBuddieAction.get());
            item.add(isLogged);
            item.add(isBuddieCondition);
            item.add(isPersonCondition);
            return item;
          }
        };
        final Provider<MenuItemDescriptor> addAsBuddieItem = new Provider<MenuItemDescriptor>() {
          @Override
          public MenuItemDescriptor get() {
            final AddAsBuddyAction action = addAsBuddie.get();
            final MenuItemDescriptor item = new MenuItemDescriptor(action);
            item.add(isNotMe);
            item.add(isNotBuddieCondition);
            item.add(isPersonCondition);
            item.add(isLogged);
            /**
             * FIXME Buggy & duplicate code with {@link AddAsBuddieHeaderButton}
             */
            action.addPropertyChangeListener(new PropertyChangeListener() {
              @Override
              public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(AbstractAction.ENABLED)) {
                  item.setVisible((Boolean) event.getNewValue());
                }
              }
            });
            return item;
          }
        };
        final Provider<MenuItemDescriptor> startChatWithPersonItem = new Provider<MenuItemDescriptor>() {
          @Override
          public MenuItemDescriptor get() {
            final MenuItemDescriptor item = new MenuItemDescriptor(startChatWithPersonAction.get());
            item.add(isNotMe);
            item.add(isLogged);
            item.add(isPersonCondition);
            item.add(isNotBuddieCondition);
            return item;
          }
        };

        snAdminsRegistry.get().add(startChatWithMemberItem);
        snCollabsItemsRegistry.get().add(startChatWithMemberItem);
        snPendingItemsRegistry.get().add(startChatWithPersonItem);
        snAdminsRegistry.get().add(startChatWithBuddieItem);
        snCollabsItemsRegistry.get().add(startChatWithBuddieItem);
        snPendingItemsRegistry.get().add(startChatWithBuddieItem);
        snAdminsRegistry.get().add(addAsBuddieItem);
        snCollabsItemsRegistry.get().add(addAsBuddieItem);
        snPendingItemsRegistry.get().add(addAsBuddieItem);

        userItemsRegistry.get().add(startChatWithBuddieItem);
        userItemsRegistry.get().add(startChatWithPersonItem);
        userItemsRegistry.get().add(addAsBuddieItem);
        startAssembly.get();
        buddieButton.get();
        groupSN.get().refreshActions();
        // openGroupRoom.get();
      }
    });
  }
}
