/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.auth;

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

public abstract class SignInAbstractPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> {

  protected final CookiesManager cookiesManager;
  private String gotoTokenOnCancel;
  protected final I18nUITranslationService i18n;
  protected final LoginRememberManager loginRemember;
  protected final Session session;
  protected final StateManager stateManager;

  public SignInAbstractPresenter(final EventBus eventBus, final View view, final Proxy<?> proxy,
      final Session session, final StateManager stateManager, final I18nUITranslationService i18n,
      final CookiesManager cookiesManager, final LoginRememberManager loginRemember) {
    super(eventBus, view, proxy);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.cookiesManager = cookiesManager;
    this.loginRemember = loginRemember;
  }

  public String getGotoTokenOnCancel() {
    return gotoTokenOnCancel;
  }

  @Override
  public SignInAbstractView getView() {
    return (SignInAbstractView) super.getView();
  }

  public void hide() {
    getView().hide();
    getView().reset();
    getView().hideMessages();
  }

  public void onCancel() {
    hide();
  }

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

  protected void onSignIn(final UserInfoDTO userInfoDTO, final boolean gotoHomePage, final String passwd) {
    final String userHash = userInfoDTO.getUserHash();
    cookiesManager.setAuthCookie(userHash);
    getView().reset();
    session.setUserHash(userHash);
    session.setCurrentUserInfo(userInfoDTO, passwd);
    final I18nLanguageDTO language = userInfoDTO.getLanguage();
    session.setCurrentLanguage(language);
    i18n.changeToLanguageIfNecessary(language.getCode(), language.getEnglishName(), true,
        new I18nLanguageChangeNeeded() {
          @Override
          public void onChangeNeeded() {
          }

          @Override
          public void onChangeNotNeeded() {
            if (gotoHomePage) {
              stateManager.gotoStateToken(new StateToken(userInfoDTO.getHomePage()).clearDocument());
            } else {
              stateManager.redirectOrRestorePreviousToken(false);
            }
          }
        });
  }

  protected void saveAutocompleteLoginData(final String nickOrEmail, final String password) {
    loginRemember.setNickOrEmail(nickOrEmail);
    loginRemember.setPassword(password);
    loginRemember.clickFormLogin();
  }

  public void setGotoTokenOnCancel(final String gotoTokenOnCancel) {
    this.gotoTokenOnCancel = gotoTokenOnCancel;
  }
}
