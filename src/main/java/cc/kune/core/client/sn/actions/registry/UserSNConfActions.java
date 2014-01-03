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
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.sn.actions.AddNewBuddiesAction;
import cc.kune.core.client.sn.actions.UserSNVisibilityMenuItem;
import cc.kune.core.client.sn.actions.conditions.IsGroupCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.actions.SNActionStyles;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * You must call {@link UserSNPresenter#refreshActions()} when adding some
 * action externally with.
 * 
 * {@link #add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)}
 */

public class UserSNConfActions extends AbstractSNActionsRegistry {

  /** The Constant ADD_BUDDIE_BTN. */
  public static final String ADD_BUDDIE_BTN = "user-sn-add-buddie-btn";

  /** The Constant OPTIONS_MENU. */
  public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant VISIBILITY_SUBMENU. */
  public static final SubMenuDescriptor VISIBILITY_SUBMENU = new SubMenuDescriptor();

  /**
   * Instantiates a new user sn conf actions.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param userBuddiesVisibility
   *          the user buddies visibility
   * @param res
   *          the res
   * @param isGroupCondition
   *          the is group condition
   * @param addNewBuddiesAction
   *          the add new buddies action
   */
  @Inject
  public UserSNConfActions(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<UserSNVisibilityMenuItem> userBuddiesVisibility,
      final CoreResources res, final IsGroupCondition isGroupCondition,
      final AddNewBuddiesAction addNewBuddiesAction) {
    final boolean isNewbie = session.isNewbie();
    final ImageResource icon = isNewbie ? res.prefGrey() : res.arrowdownsitebar();
    final String menuText = isNewbie ? i18n.t("Options") : "";
    final String menuTooltip = isNewbie ? "" : i18n.t("Options");
    final String menuStyle = isNewbie ? SNActionStyles.SN_OPTIONS_STYLES_NEWBIE
        : SNActionStyles.SN_OPTIONS_STYLES;
    OPTIONS_MENU.withText(menuText).withToolTip(menuTooltip).withIcon(icon).withStyles(menuStyle);
    final MenuRadioItemDescriptor anyoneItem = userBuddiesVisibility.get().withVisibility(
        UserSNetVisibility.anyone);
    final MenuRadioItemDescriptor onlyYourBuddiesItem = userBuddiesVisibility.get().withVisibility(
        UserSNetVisibility.yourbuddies);
    final MenuRadioItemDescriptor onlyYou = userBuddiesVisibility.get().withVisibility(
        UserSNetVisibility.onlyyou);
    assert anyoneItem.getAction() != onlyYourBuddiesItem.getAction();
    assert anyoneItem.getAction() != onlyYou.getAction();
    addImpl(OPTIONS_MENU);
    VISIBILITY_SUBMENU.withText(i18n.t("Users who can view your network")).withParent(OPTIONS_MENU);
    anyoneItem.withParent(VISIBILITY_SUBMENU).withText(i18n.t("anyone"));
    onlyYourBuddiesItem.withParent(VISIBILITY_SUBMENU).withText(i18n.t("only your buddies"));
    onlyYou.withParent(VISIBILITY_SUBMENU).withText(i18n.t("only you"));

    final ButtonDescriptor addBuddieBtn = new ButtonDescriptor(addNewBuddiesAction);

    addImpl(addBuddieBtn.withStyles("k-no-backimage").withId(ADD_BUDDIE_BTN));

    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        final boolean administrable = state.getGroupRights().isAdministrable();
        OPTIONS_MENU.setVisible(administrable);
        OPTIONS_MENU.setEnabled(administrable);
        if (state.getGroup().isPersonal()) {
          final UserSNetVisibility visibility = state.getSocialNetworkData().getUserBuddiesVisibility();
          switch (visibility) {
          case anyone:
            anyoneItem.setChecked(true);
            break;
          case yourbuddies:
            onlyYourBuddiesItem.setChecked(true);
            break;
          case onlyyou:
            onlyYou.setChecked(true);
            break;
          }
          // NotifyUser.info(i18n.t("Visibility of your network is " +
          // visibility.toString()));
        }
        addBuddieBtn.setVisible(session.isLogged() && state.getGroup().isPersonal()
            && session.getCurrentUser().getShortName().equals(state.getGroup().getShortName()));
      }
    });
  }

  /**
   * You must call {@link UserSNPresenter#refreshActions()} when adding some
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
