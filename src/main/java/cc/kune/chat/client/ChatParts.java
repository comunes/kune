package cc.kune.chat.client;

import cc.kune.chat.client.actions.AddAsBuddieHeaderButton;
import cc.kune.chat.client.actions.ChatSitebarActions;
import cc.kune.chat.client.actions.OpenGroupPublicChatRoomAction;
import cc.kune.chat.client.actions.OpenGroupPublicChatRoomButton;
import cc.kune.chat.client.actions.StartChatWithMemberAction;
import cc.kune.chat.client.actions.StartChatWithThisBuddieAction;
import cc.kune.chat.client.actions.StartChatWithUserAction;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
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

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatParts {

    @Inject
    public ChatParts(final Session session, final I18nTranslationService i18n,
            final Provider<ChatSitebarActions> chatActionsProvider,
            final Provider<AddAsBuddieHeaderButton> buddieButton,
            final Provider<GroupSNAdminsMenuItemsRegistry> snAdminsRegistry,
            final Provider<GroupSNCollabsMenuItemsRegistry> snCollabsItemsRegistry,
            final Provider<GroupSNPendingsMenuItemsRegistry> snPendingItemsRegistry,
            final Provider<GroupSNConfActions> groupConfActions,
            final Provider<UserSNMenuItemsRegistry> userItemsRegistry, final IsNotMeCondition isNotMe,
            final IsCurrentStateAdministrableCondition isAdministrableCondition,
            final IsCurrentStateEditableCondition isEditableCondition,
            final IsCurrentStateAGroupCondition isGroupCondition, final IsPersonCondition isPersonCondition,
            final Provider<StartChatWithMemberAction> startChatWithMemberAction, final IsLoggedCondition isLogged,
            final Provider<StartChatWithUserAction> startChatWithUserAction,
            final Provider<StartChatWithThisBuddieAction> startChatWithBuddieAction,
            final Provider<OpenGroupPublicChatRoomAction> openGroupRoomAction,
            final Provider<OpenGroupPublicChatRoomButton> openGroupRoom) {
        session.onInitDataReceived(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                chatActionsProvider.get();
                final Provider<MenuItemDescriptor> startChatWithMemberItem = new Provider<MenuItemDescriptor>() {
                    @Override
                    public MenuItemDescriptor get() {
                        final MenuItemDescriptor item = new MenuItemDescriptor(startChatWithMemberAction.get());
                        item.add(isNotMe);
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
                        item.add(isPersonCondition);
                        return item;
                    }
                };
                final Provider<MenuItemDescriptor> startChatWithUserItem = new Provider<MenuItemDescriptor>() {
                    @Override
                    public MenuItemDescriptor get() {
                        final MenuItemDescriptor item = new MenuItemDescriptor(startChatWithUserAction.get());
                        item.add(isNotMe);
                        item.add(isLogged);
                        item.add(isPersonCondition);
                        return item;
                    }
                };
                final Provider<MenuItemDescriptor> openChatAndInvite = new Provider<MenuItemDescriptor>() {
                    @Override
                    public MenuItemDescriptor get() {
                        final OpenGroupPublicChatRoomAction action = openGroupRoomAction.get();
                        action.setInviteMembers(true);
                        final MenuItemDescriptor item = new MenuItemDescriptor(action);
                        item.withText(i18n.t("Open group's room with members")).withToolTip(
                                i18n.t("Enter to this group public chat room and invite members"));
                        item.setParent(GroupSNConfActions.OPTIONS_MENU);
                        item.setPosition(0);
                        return item;
                    }
                };
                snAdminsRegistry.get().add(startChatWithMemberItem);
                snCollabsItemsRegistry.get().add(startChatWithMemberItem);
                snPendingItemsRegistry.get().add(startChatWithUserItem);
                userItemsRegistry.get().add(startChatWithBuddieItem);
                groupConfActions.get().add(openChatAndInvite.get());
                buddieButton.get();
                openGroupRoom.get();
            }
        });
    }
}
