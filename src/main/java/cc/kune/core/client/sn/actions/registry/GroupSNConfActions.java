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

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.invitation.GroupInvitationMenuItem;
import cc.kune.core.client.invitation.GroupInviteUserMenuItem;
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
import cc.kune.gspace.client.actions.SNActionStyles;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * You must call {@link GroupSNPresenter#refreshActions()} when adding some
 * action externally with.
 *
 * {@link #add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)}
 */

public class GroupSNConfActions extends AbstractSNActionsRegistry {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new group sn conf actions.
   *
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param membersVisibility
   *          the members visibility
   * @param membersModeration
   *          the members moderation
   * @param res
   *          the res
   * @param isLoggedCondition
   *          the is logged condition
   * @param joinGroupAction
   *          the join group action
   * @param groupInvitation
   *          the group invitation
   * @param writeToMembers
   *          the write to members
   * @param writeToAdmins
   *          the write to admins
   * @param isGroupCondition
   *          the is group condition
   * @param unJoinGroupAction
   *          the un join group action
   * @param addEntityToThisGroupAction
   *          the add entity to this group action
   * @param optionsMenu
   *          the options menu
   * @param moderationSubMenu
   *          the moderation sub menu
   * @param visibilitySubMenu
   *          the visibility sub menu
   */
  @Inject
  public GroupSNConfActions(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<MembersVisibilityMenuItem> membersVisibility,
      final Provider<MembersModerationMenuItem> membersModeration, final CoreResources res,
      final IsLoggedCondition isLoggedCondition, final JoinGroupAction joinGroupAction,
      final GroupInvitationMenuItem groupInvitation, final GroupInviteUserMenuItem groupUserInvitation,
      final WriteToMembers writeToMembers, final WriteToAdmins writeToAdmins,
      final IsGroupCondition isGroupCondition, final UnJoinFromCurrentGroupAction unJoinGroupAction,
      final AddEntityToThisGroupAction addEntityToThisGroupAction, final GroupSNOptionsMenu optionsMenu,
      final GroupSNModerationSubMenu moderationSubMenu, final GroupSNVisibilitySubMenu visibilitySubMenu) {
    final boolean isNewbie = session.isNewbie();
    final ImageResource icon = isNewbie ? res.prefGrey() : res.arrowdownsitebar();
    final String menuText = isNewbie ? i18n.t("Options") : "";
    final String menuTooltip = isNewbie ? "" : i18n.t("Options");
    final String menuStyle = isNewbie ? SNActionStyles.SN_OPTIONS_STYLES_NEWBIE
        : SNActionStyles.SN_OPTIONS_STYLES;
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

    // final MenuSeparatorDescriptor sep =
    new MenuSeparatorDescriptor(optionsMenu);
    final GuiActionDescrip memberOptions = new MenuTitleItemDescriptor(i18n.t("Member options")).withParent(optionsMenu);
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
    // Disabled, so we have more space
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
        final boolean isAdmin = event.getState().getGroupRights().isAdministrable();
        // sep.setVisible(isAdmin);
        memberOptions.setEnabled(isAdmin);
        // setVisible(false) shows the arrow...
        // moderationSubMenu.setVisible(isAdmin);
        moderationSubMenu.setEnabled(isAdmin);
        // visibilitySubMenu.setVisible(isAdmin);
        visibilitySubMenu.setEnabled(isAdmin);
        final boolean isEditor = event.getState().getGroupRights().isEditable();
        optionsMenu.setVisible(isEditor);
        optionsMenu.setEnabled(isEditor);
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
   * You must call {@link GroupSNPresenter#refreshActions()} when adding some
   * action externally with.
   *
   * @param action
   *          the action
   * @return true, if successful
   *         {@link #add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)}
   */
  @Override
  public boolean add(final GuiActionDescrip action) {
    return addImpl(action);
  }

  /**
   * Adds the impl.
   *
   * @param action
   *          the action
   * @return true, if successful
   */
  private boolean addImpl(final GuiActionDescrip action) {
    return super.add(action);
  }

}
