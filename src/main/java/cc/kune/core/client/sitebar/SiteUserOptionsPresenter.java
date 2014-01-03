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
 \*/
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class SiteUserOptionsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SiteUserOptionsPresenter implements SiteUserOptions {

  /** The Constant LOGGED_USER_MENU. */
  public static final MenuDescriptor LOGGED_USER_MENU = new MenuDescriptor();

  /** The Constant LOGGED_USER_MENU_ID. */
  public static final String LOGGED_USER_MENU_ID = "kune-sump-lum";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The icons. */
  private final IconicResources icons;

  /** The res. */
  private final CoreResources res;

  /** The separator. */
  private ToolbarSeparatorDescriptor separator;

  /** The session. */
  private final Session session;

  /** The short cut register. */
  private final GlobalShortcutRegister shortCutRegister;

  /** The site options. */
  private final SitebarActions siteOptions;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new site user options presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param img
   *          the img
   * @param icons
   *          the icons
   * @param siteOptions
   *          the site options
   * @param shortCutRegister
   *          the short cut register
   */
  @Inject
  public SiteUserOptionsPresenter(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final CoreResources img, final IconicResources icons,
      final SitebarActions siteOptions, final GlobalShortcutRegister shortCutRegister) {
    super();
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.res = img;
    this.icons = icons;
    this.siteOptions = siteOptions;
    this.shortCutRegister = shortCutRegister;
    createActions();
    separator.setVisible(false);
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        SiteUserOptionsPresenter.this.onUserSignIn(event.getUserInfo());
        separator.setVisible(true);
      }
    });
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        LOGGED_USER_MENU.setVisible(false);
        separator.setVisible(false);
        SiteUserOptionsPresenter.this.setLoggedUserName("");
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.SiteOptions#addAction(cc.kune.common.client
   * .actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    addActionImpl(descriptor);
  }

  /**
   * Adds the action impl.
   * 
   * @param descriptor
   *          the descriptor
   */
  private void addActionImpl(final GuiActionDescrip descriptor) {
    descriptor.setParent(LOGGED_USER_MENU, true);
  }

  /**
   * Creates the actions.
   */
  private void createActions() {
    LOGGED_USER_MENU.setId(LOGGED_USER_MENU_ID);
    LOGGED_USER_MENU.setParent(SitebarActions.RIGHT_TOOLBAR);
    LOGGED_USER_MENU.setStyles("k-no-backimage, k-btn-sitebar");
    LOGGED_USER_MENU.withIcon(res.arrowdownsitebarSmall());
    separator = new ToolbarSeparatorDescriptor(Type.separator, SitebarActions.RIGHT_TOOLBAR);

    final AbstractExtendedAction userHomeAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        goUserHome();
      }
    };
    userHomeAction.putValue(Action.NAME, i18n.t(CoreMessages.YOUR_HOMEPAGE));
    userHomeAction.putValue(Action.SMALL_ICON, icons.home());
    final MenuItemDescriptor item = new MenuItemDescriptor(userHomeAction);
    item.withShortcut(Shortcut.getShortcut("Alt+U"), shortCutRegister);
    item.setPosition(0);
    addActionImpl(item);
  }

  /**
   * Go user home.
   */
  private void goUserHome() {
    stateManager.gotoHistoryToken(session.getCurrentUserInfo().getShortName());
  }

  /**
   * On user sign in.
   * 
   * @param userInfoDTO
   *          the user info dto
   */
  private void onUserSignIn(final UserInfoDTO userInfoDTO) {
    LOGGED_USER_MENU.setVisible(true);
    LOGGED_USER_MENU.setEnabled(true);
    setLoggedUserName(userInfoDTO.getShortName());
    siteOptions.refreshActions();
  }

  /**
   * Sets the logged user name.
   * 
   * @param shortName
   *          the new logged user name
   */
  private void setLoggedUserName(final String shortName) {
    LOGGED_USER_MENU.putValue(Action.NAME, shortName);
  }

}
