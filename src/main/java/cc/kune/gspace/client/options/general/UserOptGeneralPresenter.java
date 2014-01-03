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
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.i18n.I18nUITranslationService.I18nLanguageChangeNeeded;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.gspace.client.options.UserOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptGeneralPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptGeneralPresenter extends EntityOptGeneralPresenter implements UserOptGeneral {

  /** The ask change language. */
  private boolean askChangeLanguage = true;

  /** The user service. */
  private final Provider<UserServiceAsync> userService;

  /** The user view. */
  private final UserOptGeneralView userView;

  /**
   * Instantiates a new user opt general presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
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
  public UserOptGeneralPresenter(final Session session, final StateManager stateManager,
      final EventBus eventBus, final I18nUITranslationService i18n, final UserOptions entityOptions,
      final Provider<UserServiceAsync> userService, final UserOptGeneralView view) {
    super(session, stateManager, eventBus, i18n, entityOptions);
    this.userService = userService;
    userView = view;
    init(view);
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        setState();
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#applicable
   * ()
   */
  @Override
  protected boolean applicable() {
    return session.isLogged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#init(cc
   * .kune.gspace.client.options.general.EntityOptGeneralView)
   */
  @Override
  public void init(final EntityOptGeneralView view) {
    super.init(view);
    userView.getResendEmailVerif().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        userView.setResendEmailVerifEnabled(false);
        userService.get().askForEmailConfirmation(session.getUserHash(), new AsyncCallback<Void>() {
          @Override
          public void onFailure(final Throwable caught) {
          }

          @Override
          public void onSuccess(final Void result) {
            NotifyUser.info(i18n.t("Sended. Check your email for the verification link."));
          }
        });
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneral#isVisible()
   */
  @Override
  public boolean isVisible() {
    return view.isRendered();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#setState()
   */
  @Override
  protected void setState() {
    final UserSimpleDTO currentUser = session.getCurrentUser();
    userView.setLongName(currentUser.getName());
    userView.setLanguage(I18nLanguageSimpleDTO.create(currentUser.getLanguage()));
    userView.setEmailNotifChecked(currentUser.getEmailNotifFreq());
    userView.setEmailVerified(currentUser.isEmailVerified());
    userView.setResendEmailVerifEnabled(!currentUser.isEmailVerified());
    userView.setEmail(currentUser.getEmail());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptGeneral#update()
   */
  @Override
  public void update() {
    setState();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralPresenter#updateInServer
   * ()
   */
  @Override
  protected void updateInServer() {
    if (view.isValid()) {
      NotifyUser.showProgress();
      final UserSimpleDTO currentUser = session.getCurrentUser();
      final UserDTO user = new UserDTO();
      user.setId(currentUser.getId());
      final String longName = userView.getLongName();
      user.setName(longName);
      user.setEmail(userView.getEmail());
      final I18nLanguageSimpleDTO lang = userView.getLanguage();
      user.setEmailNotifFreq(userView.getEmailNotif());
      userService.get().updateUser(session.getUserHash(), user, userView.getLanguage(),
          new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO result) {
              NotifyUser.hideProgress();
              stateManager.setRetrievedStateAndGo(result);
              sendChangeEntityEvent(currentUser.getShortName(), longName);
              if (askChangeLanguage) {
                i18n.changeToLanguageIfNecessary(lang.getCode(), i18n.t(lang.getEnglishName()), true,
                    new I18nLanguageChangeNeeded() {
                      @Override
                      public void onChangeNeeded() {
                      }

                      @Override
                      public void onChangeNotNeeded() {
                        askChangeLanguage = false;
                      }
                    });
              }
            };
          });
    }
  }
}
