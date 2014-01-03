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
package cc.kune.gspace.client.options;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.options.GroupOptionsPresenter.GroupOptionsView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptionsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptionsPresenter extends
    AbstractTabbedDialogPresenter<GroupOptionsView, GroupOptionsPresenter.GroupOptionsProxy> implements
    GroupOptions {

  /**
   * The Interface GroupOptionsProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface GroupOptionsProxy extends Proxy<GroupOptionsPresenter> {
  }

  /**
   * The Interface GroupOptionsView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface GroupOptionsView extends EntityOptionsView {

    /**
     * Adds the action.
     * 
     * @param descriptor
     *          the descriptor
     */
    void addAction(GuiActionDescrip descriptor);
  }

  /** The Constant GROUP_OPTIONS_ICON. */
  public static final String GROUP_OPTIONS_ICON = "k-eop-icon";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The img. */
  private final CoreResources img;

  /** The prefs item. */
  private ButtonDescriptor prefsItem;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new group options presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param proxy
   *          the proxy
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param img
   *          the img
   * @param view
   *          the view
   * @param tokenListener
   *          the token listener
   */
  @Inject
  public GroupOptionsPresenter(final EventBus eventBus, final GroupOptionsProxy proxy,
      final StateManager stateManager, final Session session, final I18nTranslationService i18n,
      final CoreResources img, final GroupOptionsView view, final SiteTokenListeners tokenListener) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
    this.session = session;
    this.i18n = i18n;
    this.img = img;
  }

  /**
   * Check state.
   * 
   * @param state
   *          the state
   */
  private void checkState(final StateAbstractDTO state) {
    if (!state.getGroup().isPersonal() && state.getGroupRights().isAdministrable()) {
      prefsItem.setVisible(true);
    } else {
      getView().hide();
      prefsItem.setVisible(false);
    }
  }

  /**
   * Creates the actions.
   */
  private void createActions() {
    final AbstractExtendedAction groupPrefsAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        show();
      }
    };
    if (session.isNewbie()) {
      groupPrefsAction.putValue(Action.NAME, i18n.t("Group options"));
    }
    groupPrefsAction.putValue(Action.SMALL_ICON, img.prefGrey());
    prefsItem = new ButtonDescriptor(groupPrefsAction);
    prefsItem.withStyles(ActionStyles.BTN_NO_BACK_NO_BORDER);
    // k-noborder,
    // k-nobackcolor,
    // k-btn-min");
    prefsItem.setId(GROUP_OPTIONS_ICON);
    prefsItem.withToolTip(i18n.t("Set your group preferences here"));
    prefsItem.setVisible(false);
    getView().addAction(prefsItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter#getView
   * ()
   */
  @Override
  public GroupOptionsView getView() {
    return (GroupOptionsView) super.getView();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();
    createActions();
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        checkState(state);
      }
    });
    session.onUserSignOut(false, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().hide();
      }
    });

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.GroupOptions#show(java.lang.String)
   */
  @Override
  public void show(final String token) {
    stateManager.gotoHistoryToken(token);
    super.show();
    checkState(session.getCurrentState());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.GroupOptions#showTooltip()
   */
  @Override
  public void showTooltip() {
    prefsItem.toggleTooltipVisible();
  }
}
