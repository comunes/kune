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
package cc.kune.core.client.state.impl;

import java.util.Collection;
import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.I18nCountryDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SessionDefault implements Session {
  private final CookiesManager cookieManager;
  private Object[][] countriesArray;
  private I18nLanguageDTO currentLanguage;
  private StateAbstractDTO currentState;
  private UserInfoDTO currentUserInfo;
  private final EventBus eventBus;
  private InitDataDTO initData;
  private Object[][] languagesArray;
  private Object[][] timezonesArray;
  private String userHash;
  private final Provider<UserServiceAsync> userServiceProvider;

  @Inject
  public SessionDefault(final CookiesManager cookieManager,
      final Provider<UserServiceAsync> userServiceProvider, final EventBus eventBus) {
    this.cookieManager = cookieManager;
    this.eventBus = eventBus;
    this.userHash = cookieManager.getAuthCookie();
    this.userHash = userHash == null || userHash.equals("null") ? null : userHash;
    this.userServiceProvider = userServiceProvider;
    languagesArray = null;
    check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
      }
    });
  }

  @Override
  public void check(final AsyncCallbackSimple<Void> callback) {
    Log.debug("Checking session (userhash: " + getUserHash() + ")");
    userServiceProvider.get().onlyCheckSession(getUserHash(), callback);
  }

  @Override
  public StateContainerDTO getContainerState() {
    return (StateContainerDTO) currentState;
  }

  @Override
  public StateContentDTO getContentState() {
    return (StateContentDTO) currentState;
  }

  @Override
  public List<I18nCountryDTO> getCountries() {
    return initData.getCountries();
  }

  @Override
  public Object[][] getCountriesArray() {
    if (countriesArray == null) {
      countriesArray = mapCountries();
    }
    return countriesArray;
  }

  @Override
  public String getCurrentCCversion() {
    return initData.getCurrentCCversion();
  }

  @Override
  public String getCurrentGroupShortName() {
    return currentState == null ? null : currentState.getStateToken().getGroup();
  }

  @Override
  public I18nLanguageDTO getCurrentLanguage() {
    return currentLanguage;
  }

  @Override
  public StateAbstractDTO getCurrentState() {
    return currentState;
  }

  @Override
  public StateToken getCurrentStateToken() {
    return currentState == null ? null : currentState.getStateToken();
  }

  @Override
  public UserSimpleDTO getCurrentUser() {
    return currentUserInfo == null ? null : currentUserInfo.getUser();
  }

  @Override
  public UserInfoDTO getCurrentUserInfo() {
    return currentUserInfo;
  }

  @Override
  public LicenseDTO getDefLicense() {
    return initData.getDefaultLicense();
  }

  @Override
  public String getGalleryPermittedExtensions() {
    return initData.getGalleryPermittedExtensions();
  }

  @Override
  public Collection<ToolSimpleDTO> getGroupTools() {
    return initData.getGroupTools();
  }

  @Override
  public int getImgCropsize() {
    return initData.getImgCropsize();
  }

  @Override
  public int getImgIconsize() {
    return initData.getImgIconsize();
  }

  @Override
  public int getImgResizewidth() {
    return initData.getImgResizewidth();
  }

  @Override
  public int getImgThumbsize() {
    return initData.getImgThumbsize();
  }

  @Override
  public InitDataDTO getInitData() {
    return initData;
  }

  @Override
  public List<I18nLanguageSimpleDTO> getLanguages() {
    return initData.getLanguages();
  }

  @Override
  public Object[][] getLanguagesArray() {
    if (languagesArray == null) {
      languagesArray = mapLangs();
    }
    return languagesArray;
  }

  @Override
  public List<LicenseDTO> getLicenses() {
    return initData.getLicenses();
  }

  @Override
  public boolean getShowDeletedContent() {
    return currentUserInfo == null ? false : currentUserInfo.getShowDeletedContent();
  }

  @Override
  public String getSiteUrl() {
    final String baseURL = GWT.getModuleBaseURL();
    return baseURL.substring(0, baseURL.lastIndexOf("/" + GWT.getModuleName()));
  }

  @Override
  public Object[][] getTimezones() {
    if (timezonesArray == null) {
      mapTimezones();
    }
    return timezonesArray;
  }

  @Override
  public String getUserHash() {
    return userHash;
  }

  @Override
  public Collection<ToolSimpleDTO> getUserTools() {
    return initData.getUserTools();
  }

  @Override
  public boolean inSameToken(final StateToken token) {
    return getCurrentStateToken().equals(token);
  }

  @Override
  public boolean isCurrentStateAContent() {
    return currentState instanceof StateContentDTO;
  }

  @Override
  public boolean isCurrentStateAGroup() {
    return currentState == null ? false : !currentState.getGroup().isPersonal();
  }

  @Override
  public boolean isCurrentStateAPerson() {
    return currentState == null ? false : currentState.getGroup().isPersonal();
  }

  @Override
  public boolean isInCurrentUserSpace() {
    if (!isLogged()) {
      return false;
    }
    final StateToken currentStateToken = getCurrentStateToken();
    if (currentStateToken != null
        && currentStateToken.getGroup().equals(getCurrentUser().getShortName())) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isLogged() {
    return userHash != null;
  }

  @Override
  public boolean isNotLogged() {
    return !isLogged();
  }

  private Object[][] mapCountries() {
    assert initData != null;
    final Object[][] objs = new Object[initData.getCountries().size()][1];
    int i = 0;
    for (final I18nCountryDTO country : initData.getCountries()) {
      final Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
      objs[i++] = obj;
    }
    return objs;
  }

  private Object[][] mapLangs() {
    assert initData != null;
    final Object[][] objs = new Object[initData.getLanguages().size()][1];
    int i = 0;
    for (final I18nLanguageSimpleDTO language : initData.getLanguages()) {
      final Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
      objs[i++] = obj;
    }
    return objs;
  }

  private void mapTimezones() {
    assert initData != null;
    timezonesArray = new Object[initData.getTimezones().length][1];
    for (int i = 0; i < getTimezones().length; i++) {
      final Object[] obj = new Object[] { initData.getTimezones()[i] };
      timezonesArray[i] = obj;
    }
  }

  @Override
  public HandlerRegistration onAppStart(final boolean fireNow, final AppStartHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(AppStartEvent.getType(), handler);
    if (fireNow && initData != null) {
      handler.onAppStart(new AppStartEvent(initData));
    }
    return handlerReg;
  }

  @Override
  public HandlerRegistration onUserSignIn(final boolean fireNow, final UserSignInHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(UserSignInEvent.getType(), handler);
    if (fireNow && isLogged() && currentUserInfo != null) {
      handler.onUserSignIn(new UserSignInEvent(currentUserInfo, null));
    }
    return handlerReg;
  }

  @Override
  public HandlerRegistration onUserSignInOrSignOut(final boolean fireNow,
      final UserSignInOrSignOutHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(UserSignInOrSignOutEvent.getType(),
        handler);
    if (fireNow) {
      handler.onUserSignInOrSignOut(new UserSignInOrSignOutEvent(isLogged()));
    }
    return handlerReg;
  }

  @Override
  public HandlerRegistration onUserSignOut(final boolean fireNow, final UserSignOutHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(UserSignOutEvent.getType(), handler);
    if (fireNow && isNotLogged()) {
      handler.onUserSignOut(new UserSignOutEvent());
    }
    return handlerReg;
  }

  @Override
  public void setCurrentLanguage(final I18nLanguageDTO currentLanguage) {
    this.currentLanguage = currentLanguage;
  }

  @Override
  public void setCurrentState(final StateAbstractDTO currentState) {
    this.currentState = currentState;
  }

  @Override
  public void setCurrentUserInfo(final UserInfoDTO currentUserInfo, final String password) {
    this.currentUserInfo = currentUserInfo;
    if (currentUserInfo != null) {
      eventBus.fireEvent(new UserSignInEvent(this.currentUserInfo, password));
    } else {
      eventBus.fireEvent(new UserSignOutEvent());
    }
    eventBus.fireEvent(new UserSignInOrSignOutEvent(isLogged()));
  }

  @Override
  public void setInitData(final InitDataDTO initData) {
    this.initData = initData;
  }

  @Override
  public void setUserHash(final String userHash) {
    this.userHash = userHash;
  }

  @Override
  public void signOut() {
    cookieManager.removeAuthCookie();
    setUserHash(null);
    setCurrentUserInfo(null, null);
  }

  @Override
  public boolean userIsJoiningGroups() {
    return currentUserInfo.getGroupsIsAdmin().size() + currentUserInfo.getGroupsIsCollab().size() > 0;
  }
}
