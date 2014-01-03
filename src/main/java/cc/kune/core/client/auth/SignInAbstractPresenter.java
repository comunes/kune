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
package cc.kune.core.client.auth;

import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.i18n.I18nUITranslationService.I18nLanguageChangeNeeded;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

// TODO: Auto-generated Javadoc
/**
 * The Class SignInAbstractPresenter.
 * 
 * @param <V>
 *          the value type
 * @param <Proxy_>
 *          the generic type
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class SignInAbstractPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> {

  private boolean askForLanguageChange;

  /** The cookies manager. */
  protected final CookiesManager cookiesManager;

  /** The goto token on cancel. */
  private String gotoTokenOnCancel;

  /** The goto token on success. */
  private String gotoTokenOnSuccess;

  /** The i18n. */
  protected final I18nUITranslationService i18n;

  /** The login remember. */
  protected final LoginRememberManager loginRemember;

  /** The session. */
  protected final Session session;

  /** The state manager. */
  protected final StateManager stateManager;

  /**
   * Instantiates a new sign in abstract presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param cookiesManager
   *          the cookies manager
   * @param loginRemember
   *          the login remember
   */
  public SignInAbstractPresenter(final EventBus eventBus, final View view, final Proxy<?> proxy,
      final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
      final CookiesManager cookiesManager, final LoginRememberManager loginRemember) {
    super(eventBus, view, proxy);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.cookiesManager = cookiesManager;
    this.loginRemember = loginRemember;
    this.askForLanguageChange = true;
  }

  /**
   * Gets the goto token on cancel.
   * 
   * @return the goto token on cancel
   */
  public String getGotoTokenOnCancel() {
    return gotoTokenOnCancel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#getView()
   */
  @Override
  public SignInAbstractView getView() {
    return (SignInAbstractView) super.getView();
  }

  /**
   * Hide.
   */
  public void hide() {
    getView().hide();
    getView().reset();
    getView().hideMessages();
  }

  /**
   * On cancel.
   */
  public void onCancel() {
    hide();
  }

  /**
   * On close.
   */
  public void onClose() {
    getView().reset();
    getView().hideMessages();
    if (!session.isLogged()) {
      if (gotoTokenOnCancel != null) {
        if (!gotoTokenOnCancel.equals(SiteTokens.NEW_GROUP)) {
          stateManager.gotoHistoryToken(gotoTokenOnCancel);
        }
        gotoTokenOnCancel = null;
      } else {
        stateManager.redirectOrRestorePreviousToken(false);
      }
    }
  }

  /**
   * On sign in.
   * 
   * @param userInfoDTO
   *          the user info dto
   * @param gotoHomePage
   *          the goto home page
   * @param passwd
   *          the passwd
   */
  protected void onSignIn(final UserInfoDTO userInfoDTO, final boolean gotoHomePage, final String passwd) {
    final String userHash = userInfoDTO.getUserHash();
    cookiesManager.setAuthCookie(userHash);
    getView().reset();
    session.setUserHash(userHash);
    session.setCurrentUserInfo(userInfoDTO, passwd);
    final I18nLanguageDTO language = userInfoDTO.getLanguage();
    session.setCurrentLanguage(language);
    i18n.changeToLanguageIfNecessary(language.getCode(), language.getEnglishName(),
        askForLanguageChange, new I18nLanguageChangeNeeded() {
          @Override
          public void onChangeNeeded() {
          }

          @Override
          public void onChangeNotNeeded() {
            if (gotoHomePage) {
              stateManager.gotoStateToken(new StateToken(userInfoDTO.getHomePage()).clearDocument());
            } else {
              if (gotoTokenOnSuccess != null) {
                if (gotoTokenOnSuccess.matches(TextUtils.URL_REGEXP)) {
                  // Redirect to other website
                  KuneWindowUtils.open(gotoTokenOnSuccess);
                } else {
                  // We know were to go after sign-in
                  stateManager.gotoHistoryToken(gotoTokenOnSuccess);
                }
              } else {
                // In other case, try to restore previous state
                stateManager.redirectOrRestorePreviousToken(false);
              }
            }
          }
        });
  }

  /**
   * Save autocomplete login data.
   * 
   * @param nickOrEmail
   *          the nick or email
   * @param password
   *          the password
   */
  protected void saveAutocompleteLoginData(final String nickOrEmail, final String password) {
    loginRemember.setNickOrEmail(nickOrEmail);
    loginRemember.setPassword(password);
    loginRemember.clickFormLogin();
  }

  public void setAskForLanguageChange(final boolean askForLanguageChange) {
    this.askForLanguageChange = askForLanguageChange;
  }

  /**
   * Sets the goto token on cancel.
   * 
   * @param gotoTokenOnCancel
   *          the new goto token on cancel
   */
  public void setGotoTokenOnCancel(final String gotoTokenOnCancel) {
    this.gotoTokenOnCancel = gotoTokenOnCancel;
  }

  /**
   * Sets the goto token on success.
   * 
   * @param gotoTokenOnSuccess
   *          the new goto token on success
   */
  public void setGotoTokenOnSuccess(final String gotoTokenOnSuccess) {
    this.gotoTokenOnSuccess = gotoTokenOnSuccess;
  }
}
