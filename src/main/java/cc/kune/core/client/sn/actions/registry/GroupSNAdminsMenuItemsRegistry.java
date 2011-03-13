package cc.kune.core.client.sn.actions.registry;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.sn.actions.AcceptJoinGroupAction;
import cc.kune.core.client.sn.actions.ChangeToAdminAction;
import cc.kune.core.client.sn.actions.ChangeToCollabAction;
import cc.kune.core.client.sn.actions.DenyJoinGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.sn.actions.GotoMemberAction;
import cc.kune.core.client.sn.actions.GotoYourHomePageAction;
import cc.kune.core.client.sn.actions.RemoveMemberAction;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotMeCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupSNAdminsMenuItemsRegistry extends AbstractSNMembersActionsRegistry {

    @Inject
    public GroupSNAdminsMenuItemsRegistry(final Session session, final IsLoggedCondition isLogged,
            final IsCurrentStateAdministrableCondition isAdministrableCondition, final IsPersonCondition isPersonCondition,
            final IsGroupCondition isGroupCondition, final IsMeCondition isMe, final IsNotMeCondition isNotMe,
            final ChangeToCollabAction changeToCollabAction, final ChangeToAdminAction changeToAdminAction,
            final RemoveMemberAction removeMemberAction, final AcceptJoinGroupAction acceptJoinGroupAction,
            final DenyJoinGroupAction denyJoinGroupAction, final GotoGroupAction gotoGroupAction,
            final GotoMemberAction gotoMemberAction, final GotoYourHomePageAction gotoYourHomePageAction) {
        add(new Provider<MenuItemDescriptor>() {
            @Override
            public MenuItemDescriptor get() {
                final MenuItemDescriptor item = new MenuItemDescriptor(gotoMemberAction);
                item.add(isPersonCondition);
                item.add(isNotMe);
                return item;
            }
        });
        add(new Provider<MenuItemDescriptor>() {
            @Override
            public MenuItemDescriptor get() {
                final MenuItemDescriptor item = new MenuItemDescriptor(gotoYourHomePageAction);
                item.add(isPersonCondition);
                item.add(isMe);
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
        add(new Provider<MenuItemDescriptor>() {
            @Override
            public MenuItemDescriptor get() {
                final MenuItemDescriptor item = new MenuItemDescriptor(changeToCollabAction);
                item.add(isAdministrableCondition);
                item.add(isNotMe);
                item.add(isLogged);
                return item;
            }
        });
        add(new Provider<MenuItemDescriptor>() {
            @Override
            public MenuItemDescriptor get() {
                final MenuItemDescriptor item = new MenuItemDescriptor(removeMemberAction);
                item.add(isAdministrableCondition);
                item.add(isNotMe);
                item.add(isLogged);
                return item;
            }
        });
    }
}
