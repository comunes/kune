package cc.kune.core.client.sn.actions.registry;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sn.actions.JoinGroupAction;
import cc.kune.core.client.sn.actions.MembersModerationMenuItem;
import cc.kune.core.client.sn.actions.MembersVisibilityMenuItem;
import cc.kune.core.client.sn.actions.UnJoinGroupAction;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("serial")
public class GroupSNConfActions extends AbstractSNActionsRegistry {

    public static final SubMenuDescriptor MODERATION_SUBMENU = new SubMenuDescriptor();
    public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();
    public static final SubMenuDescriptor VISIBILITY_SUBMENU = new SubMenuDescriptor();

    @Inject
    public GroupSNConfActions(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final Provider<MembersVisibilityMenuItem> membersVisibility,
            final Provider<MembersModerationMenuItem> membersModeration, final CoreResources res,
            final IsLoggedCondition isLoggedCondition, final JoinGroupAction joinGroupAction,
            final IsGroupCondition isGroupCondition, final UnJoinGroupAction unJoinGroupAction) {
        OPTIONS_MENU.withToolTip(i18n.t("Options")).withIcon(res.arrowDownSitebar()).withStyles("k-sn-options-menu");
        final GuiActionDescrip optionsTitle = new MenuTitleItemDescriptor(i18n.t("Options")).withParent(OPTIONS_MENU);
        final MenuRadioItemDescriptor anyoneItem = membersVisibility.get().withVisibility(
                SocialNetworkVisibility.anyone);
        final MenuRadioItemDescriptor onlyMembersItem = membersVisibility.get().withVisibility(
                SocialNetworkVisibility.onlymembers);
        final MenuRadioItemDescriptor onlyAdminsItem = membersVisibility.get().withVisibility(
                SocialNetworkVisibility.onlyadmins);
        final MenuRadioItemDescriptor closedItem = membersModeration.get().withModeration(AdmissionType.Closed);
        final MenuRadioItemDescriptor moderatedItem = membersModeration.get().withModeration(AdmissionType.Moderated);
        final MenuRadioItemDescriptor openItem = membersModeration.get().withModeration(AdmissionType.Open);
        add(OPTIONS_MENU);
        add(optionsTitle);
        add(VISIBILITY_SUBMENU.withText(i18n.t("Those who can view this member list")).withParent(OPTIONS_MENU));
        add(MODERATION_SUBMENU.withText(i18n.t("New members policy")).withParent(OPTIONS_MENU));
        add(anyoneItem.withParent(VISIBILITY_SUBMENU).withText(i18n.t("anyone")));
        add(onlyMembersItem.withParent(VISIBILITY_SUBMENU).withText(i18n.t("only members")));
        add(onlyAdminsItem.withParent(VISIBILITY_SUBMENU).withText(i18n.t("only admins")));
        add(moderatedItem.withParent(MODERATION_SUBMENU).withText(i18n.t("moderate request to join")));
        add(openItem.withParent(MODERATION_SUBMENU).withText(i18n.t("auto accept request to join")));
        // add(closedItem.withParent(MODERATION_SUBMENU).withText(
        // i18n.t("closed for new members")));

        final ButtonDescriptor joinBtn = new ButtonDescriptor(joinGroupAction);
        final ButtonDescriptor unJoinBtn = new ButtonDescriptor(unJoinGroupAction);
        unJoinBtn.add(isLoggedCondition);
        add(joinBtn.withStyles("k-no-backimage"));
        add(unJoinBtn.withStyles("k-no-backimage"));

        stateManager.onStateChanged(true, new StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                final boolean administrable = event.getState().getGroupRights().isAdministrable();
                OPTIONS_MENU.setVisible(administrable);
                OPTIONS_MENU.setEnabled(administrable);
                final StateAbstractDTO state = event.getState();
                final GroupDTO currentGroup = state.getGroup();
                if (currentGroup.isNotPersonal()) {
                    switch (state.getSocialNetworkData().getSocialNetworkVisibility()) {
                    case anyone:
                        anyoneItem.setChecked(true);
                        break;
                    case onlyadmins:
                        onlyAdminsItem.setChecked(true);
                        break;
                    case onlymembers:
                        onlyMembersItem.setChecked(true);
                        break;
                    }
                }
                switch (currentGroup.getAdmissionType()) {
                case Moderated:
                    moderatedItem.setChecked(true);
                    break;
                case Open:
                    openItem.setChecked(true);
                    break;
                case Closed:
                    closedItem.setChecked(true);
                    break;
                }
                unJoinBtn.setVisible(session.isLogged());
            }
        });
    }
}
