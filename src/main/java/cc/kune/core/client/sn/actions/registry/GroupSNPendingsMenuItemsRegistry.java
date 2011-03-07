package cc.kune.core.client.sn.actions.registry;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.sn.actions.AcceptJoinGroupAction;
import cc.kune.core.client.sn.actions.ChangeToAdminAction;
import cc.kune.core.client.sn.actions.ChangeToCollabAction;
import cc.kune.core.client.sn.actions.DenyJoinGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.sn.actions.GotoPersonAction;
import cc.kune.core.client.sn.actions.RemoveMemberAction;
import cc.kune.core.client.sn.actions.conditions.IsAdministrableCondition;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsPersonCondition;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupSNPendingsMenuItemsRegistry extends AbstractSNMembersActionsRegistry {

    @Inject
    public GroupSNPendingsMenuItemsRegistry(final Session session, final IsAdministrableCondition isAdministrableCondition,
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
