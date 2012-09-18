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

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.actions.AddEntityToThisGroupAction;
import cc.kune.core.client.sn.actions.GroupSNModerationSubMenu;
import cc.kune.core.client.sn.actions.GroupSNOptionsMenu;
import cc.kune.core.client.sn.actions.GroupSNVisibilitySubMenu;
import cc.kune.core.client.sn.actions.JoinGroupAction;
import cc.kune.core.client.sn.actions.MembersModerationMenuItem;
import cc.kune.core.client.sn.actions.MembersVisibilityMenuItem;
import cc.kune.core.client.sn.actions.UnJoinFromCurrentGroupAction;
import cc.kune.core.client.sn.actions.WriteToAdmins;
import cc.kune.core.client.sn.actions.WriteToMembers;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.actions.ActionStyles;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 * You must call {@link GroupSNPresenter#refreshActions()} when adding some
 * action externally with
 * {@link #add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)}
 *
 */
@SuppressWarnings("serial")
public class GroupSNConfActions extends AbstractSNActionsRegistry {

  @Inject
  public GroupSNConfActions(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<MembersVisibilityMenuItem> membersVisibility,
      final Provider<MembersModerationMenuItem> membersModeration, final CoreResources res,
      final IsLoggedCondition isLoggedCondition, final JoinGroupAction joinGroupAction,
      final WriteToMembers writeToMembers, final WriteToAdmins writeToAdmins,
      final IsGroupCondition isGroupCondition, final UnJoinFromCurrentGroupAction unJoinGroupAction,
      final AddEntityToThisGroupAction addEntityToThisGroupAction, final GroupSNOptionsMenu optionsMenu,
      final GroupSNModerationSubMenu moderationSubMenu, final GroupSNVisibilitySubMenu visibilitySubMenu) {
    boolean isNewbie = session.isNewbie();
    ImageResource icon = isNewbie? res.prefGrey():  res.arrowdownsitebar();
    String menuText = isNewbie? i18n.t("Options"): "";
    String menuTooltip = isNewbie? "" : i18n.t("Options");
    String menuStyle = isNewbie? ActionStyles.SN_OPTIONS_STYLES_NEWBIE : ActionStyles.SN_OPTIONS_STYLES;
    optionsMenu.withText(menuText).withToolTip(menuTooltip).withIcon(icon).withStyles(menuStyle);
    final MenuRadioItemDescriptor anyoneItem = membersVisibility.get().withVisibility(
        SocialNetworkVisibility.anyone);
    final MenuRadioItemDescriptor onlyMembersItem = membersVisibility.get().withVisibility(
        SocialNetworkVisibility.onlymembers);
    final MenuRadioItemDescriptor onlyAdminsItem = membersVisibility.get().withVisibility(
        SocialNetworkVisibility.onlyadmins);
    final MenuRadioItemDescriptor closedItem = membersModeration.get().withModeration(
        AdmissionType.Closed);
    final MenuRadioItemDescriptor moderatedItem = membersModeration.get().withModeration(
        AdmissionType.Moderated);
    final MenuRadioItemDescriptor openItem = membersModeration.get().withModeration(AdmissionType.Open);

    addImpl(optionsMenu);
    new MenuSeparatorDescriptor(optionsMenu);
    new MenuTitleItemDescriptor(i18n.t("Options")).withParent(optionsMenu);
    new MenuItemDescriptor(addEntityToThisGroupAction).withParent(optionsMenu).setPosition(0);
    new MenuItemDescriptor(unJoinGroupAction).withParent(optionsMenu).setPosition(1);
    // new MenuSeparatorDescriptor(optionsMenu).setPosition(2);
    visibilitySubMenu.withText(i18n.t("Users who can view this member list")).withParent(optionsMenu);
    moderationSubMenu.withText(i18n.t("New members policy")).withParent(optionsMenu);
    anyoneItem.withParent(visibilitySubMenu).withText(i18n.t("anyone"));
    onlyMembersItem.withParent(visibilitySubMenu).withText(i18n.t("only members"));
    onlyAdminsItem.withParent(visibilitySubMenu).withText(i18n.t("only administrators"));
    moderatedItem.withParent(moderationSubMenu).withText(i18n.t("moderate request to join"));
    openItem.withParent(moderationSubMenu).withText(i18n.t("auto accept request to join"));
    closedItem.withParent(moderationSubMenu).withText(i18n.t("closed for new members"));

    final ButtonDescriptor joinBtn = new ButtonDescriptor(joinGroupAction);
    // final ButtonDescriptor unJoinBtn = new
    // ButtonDescriptor(unJoinGroupAction);
    // final ButtonDescriptor addMemberBtn = new
    // ButtonDescriptor(addEntityToThisGroupAction);
    // unJoinBtn.add(isLoggedCondition);
    addImpl(joinBtn); // .withStyles("k-no-backimage, k-noborder, k-nobackcolor"));
    // addImpl(unJoinBtn); //
    // .withStyles("k-no-backimage, k-noborder, k-nobackcolor"));
    // addImpl(addMemberBtn);

    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final boolean administrable = event.getState().getGroupRights().isAdministrable();
        optionsMenu.setVisible(administrable);
        optionsMenu.setEnabled(administrable);
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
      }
    });
  }

  /**
   *
   * You must call {@link GroupSNPresenter#refreshActions()} when adding some
   * action externally with
   * {@link #add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)}
   *
   */
  @Override
  public boolean add(final GuiActionDescrip action) {
    return addImpl(action);
  }

  private boolean addImpl(final GuiActionDescrip action) {
    return super.add(action);
  }

}
