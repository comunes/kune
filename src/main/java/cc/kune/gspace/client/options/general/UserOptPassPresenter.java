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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.WrongCurrentPasswordException;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.options.UserOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptPassPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptPassPresenter implements UserOptPass {

  /** The entity options. */
  private final UserOptions entityOptions;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /** The user service. */
  private final Provider<UserServiceAsync> userService;

  /** The view. */
  private UserOptPassView view;

  /**
   * Instantiates a new user opt pass presenter.
   * 
   * @param session
   *          the session
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param entityOptions
   *          the entity options
   * @param userService
   *          the user service
   * @param view
   *          the view
   */
  @Inject
  public UserOptPassPresenter(final Session session, final EventBus eventBus,
      final I18nTranslationService i18n, final UserOptions entityOptions,
      final Provider<UserServiceAsync> userService, final UserOptPassView view) {
    this.session = session;
    this.i18n = i18n;
    this.entityOptions = entityOptions;
    this.userService = userService;
    init(view);
  }

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
  public void init(final UserOptPassView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    view.getChangeBtn().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        updateInServer();
      }
    });
  }

  /**
   * Update in server.
   */
  protected void updateInServer() {
    if (view.isValid()) {
      final String currentPasswd = view.getCurrentPasswd();
      final String newPasswd = view.getNewPasswd();
      final String newPasswdRepeated = view.getNewPasswdRepeated();
      NotifyUser.showProgress();
      if (!newPasswd.equals(newPasswdRepeated)) {
        NotifyUser.error(i18n.t("Passwords don't match"));
      } else {
        if (newPasswd.equals(currentPasswd)) {
          NotifyUser.error(i18n.t("Please provide a different new password"));
        } else {
          view.mask();
          userService.get().changePasswd(session.getUserHash(), currentPasswd, newPasswd,
              new AsyncCallback<Void>() {

                @Override
                public void onFailure(final Throwable caught) {
                  view.unmask();
                  if (caught instanceof WrongCurrentPasswordException) {
                    NotifyUser.error(i18n.t("The current password is incorrect"));
                  } else {
                    NotifyUser.error(i18n.t("Sorry, something was wrong and we cannot change your password"));
                  }
                }

                @Override
                public void onSuccess(final Void result) {
                  NotifyUser.hideProgress();
                  view.unmask();
                  NotifyUser.info(i18n.t("Password changed successfully"));
                  view.reset();
                }

              });
        }

      }
    }
  }
}
