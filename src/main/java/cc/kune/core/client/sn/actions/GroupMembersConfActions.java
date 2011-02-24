package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.registry.GroupMembersActionsRegistry;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupMembersConfActions {

    public static final SubMenuDescriptor MODERATION_SUBMENU = new SubMenuDescriptor();
    public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();
    public static final SubMenuDescriptor VISIBILITY_SUBMENU = new SubMenuDescriptor();

    @Inject
    public GroupMembersConfActions(final I18nTranslationService i18n, final GroupMembersActionsRegistry registry,
            final Provider<MembersVisibilityMenuItem> membersVisibility,
            final Provider<MembersModerationMenuItem> membersModeration, final CoreResources res,
            final JoinGroupAction joinGroupAction, final IsGroupCondition isGroupCondition,
            final UnJoinGroupAction unJoinGroupAction) {
        OPTIONS_MENU.withText(i18n.t("More")).withIcon(res.arrowDownSitebar()).withStyles("k-sn-options-menu");
        registry.add(OPTIONS_MENU);
        registry.add(VISIBILITY_SUBMENU.withText(i18n.t("Those who can view this member list")).withParent(OPTIONS_MENU));
        registry.add(MODERATION_SUBMENU.withText(i18n.t("New members policy")).withParent(OPTIONS_MENU));
        registry.add(membersVisibility.get().withVisibility(SocialNetworkVisibility.anyone).withParent(
                VISIBILITY_SUBMENU).withText(i18n.t("anyone")));
        registry.add(membersVisibility.get().withVisibility(SocialNetworkVisibility.onlymembers).withParent(
                VISIBILITY_SUBMENU).withText(i18n.t("only members")));
        registry.add(membersVisibility.get().withVisibility(SocialNetworkVisibility.onlyadmins).withParent(
                VISIBILITY_SUBMENU).withText(i18n.t("only admins")));
        registry.add(membersModeration.get().withModeration(AdmissionType.Moderated).withParent(MODERATION_SUBMENU).withText(
                i18n.t("moderate request to join")));
        registry.add(membersModeration.get().withModeration(AdmissionType.Open).withParent(MODERATION_SUBMENU).withText(
                i18n.t("auto accept request to join")));
        registry.add(membersModeration.get().withModeration(AdmissionType.Closed).withParent(MODERATION_SUBMENU).withText(
                i18n.t("closed for new members")));

        registry.add(new ButtonDescriptor(joinGroupAction).withStyles("k-no-backimage"));
        registry.add(new ButtonDescriptor(unJoinGroupAction).withStyles("k-no-backimage"));
    }
}
