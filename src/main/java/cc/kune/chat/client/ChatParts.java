package cc.kune.chat.client;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.client.sn.actions.registry.GroupSNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNPendingsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.UserSNMenuItemsRegistry;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChatParts {

    @Inject
    public ChatParts(final Session session, final Provider<ChatSitebarActions> chatActionsProvider,
            final Provider<AddAsBuddieHeaderButton> buddieButton,
            final Provider<GroupSNAdminsMenuItemsRegistry> snAdminsRegistry,
            final Provider<GroupSNCollabsMenuItemsRegistry> snCollabsItemsRegistry,
            final Provider<GroupSNPendingsMenuItemsRegistry> snPendingItemsRegistry,
            final Provider<UserSNMenuItemsRegistry> userItemsRegistry, final IsNotMeCondition isNotMe,
            final IsPersonCondition isPersonCondition,
            final Provider<StartChatWithMemberAction> startChatWithMemberAction, final IsLoggedCondition isLogged,
            final Provider<StartChatWithUserAction> startChatWithUserAction,
            final Provider<StartChatWithThisBuddieAction> startChatWithBuddieAction) {
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
                snAdminsRegistry.get().add(startChatWithMemberItem);
                snCollabsItemsRegistry.get().add(startChatWithMemberItem);
                snPendingItemsRegistry.get().add(startChatWithUserItem);
                userItemsRegistry.get().add(startChatWithBuddieItem);
                buddieButton.get();
            }
        });
    }
}
