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
package cc.kune.core.client.state.impl;

import java.util.Collection;
import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.events.WaveSessionAvailableEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.SessionConstants;
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

/**
 * The Class SessionDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SessionDefault implements Session {
  private final CookiesManager cookieManager;
  private Object[][] countriesArray;
  private I18nLanguageDTO currentLanguage;
  private StateAbstractDTO currentState;
  private UserInfoDTO currentUserInfo;
  private final EventBus eventBus;
  private InitDataDTO initData;

  private Boolean isDev;
  private boolean isEmbedded = false;
  /** The languages array. */
  private Object[][] languagesArray;
  private Object[][] timezonesArray;
  private String userHash;
  private final Provider<UserServiceAsync> userServiceProvider;

  /**
   * Instantiates a new session default.
   * 
   * @param cookieManager
   *          the cookie manager
   * @param userServiceProvider
   *          the user service provider
   * @param eventBus
   *          the event bus
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getContentState()
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentCCversion()
   */
  @Override
  public String getCurrentCCversion() {
    return initData.getCurrentCCversion();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentGroupShortName()
   */
  @Override
  public String getCurrentGroupShortName() {
    return currentState == null ? null : currentState.getStateToken().getGroup();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentLanguage()
   */
  @Override
  public I18nLanguageDTO getCurrentLanguage() {
    return currentLanguage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentState()
   */
  @Override
  public StateAbstractDTO getCurrentState() {
    return currentState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentStateToken()
   */
  @Override
  public StateToken getCurrentStateToken() {
    return currentState == null ? null : currentState.getStateToken();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentUser()
   */
  @Override
  public UserSimpleDTO getCurrentUser() {
    return currentUserInfo == null ? null : currentUserInfo.getUser();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getCurrentUserInfo()
   */
  @Override
  public UserInfoDTO getCurrentUserInfo() {
    return currentUserInfo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getDefLicense()
   */
  @Override
  public LicenseDTO getDefLicense() {
    return initData.getDefaultLicense();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getFullTranslatedLanguages()
   */
  @Override
  public List<I18nLanguageSimpleDTO> getFullTranslatedLanguages() {
    return initData.getFullTranslatedLanguages();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getGalleryPermittedExtensions()
   */
  @Override
  public String getGalleryPermittedExtensions() {
    return initData.getGalleryPermittedExtensions();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getGroupTools()
   */
  @Override
  public Collection<ToolSimpleDTO> getGroupTools() {
    return initData.getGroupTools();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getImgCropsize()
   */
  @Override
  public int getImgCropsize() {
    return initData.getImgCropsize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getImgIconsize()
   */
  @Override
  public int getImgIconsize() {
    return initData.getImgIconsize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getImgResizewidth()
   */
  @Override
  public int getImgResizewidth() {
    return initData.getImgResizewidth();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getImgThumbsize()
   */
  @Override
  public int getImgThumbsize() {
    return initData.getImgThumbsize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getInitData()
   */
  @Override
  public InitDataDTO getInitData() {
    return initData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getLanguages()
   */
  @Override
  public List<I18nLanguageSimpleDTO> getLanguages() {
    return initData.getLanguages();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getLanguagesArray()
   */
  @Override
  public Object[][] getLanguagesArray() {
    if (languagesArray == null) {
      languagesArray = mapLangs();
    }
    return languagesArray;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getLicenses()
   */
  @Override
  public List<LicenseDTO> getLicenses() {
    return initData.getLicenses();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getShowDeletedContent()
   */
  @Override
  public boolean getShowDeletedContent() {
    return currentUserInfo == null ? false : currentUserInfo.getShowDeletedContent();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getSiteUrl()
   */
  @Override
  public String getSiteUrl() {
    final String baseURL = GWT.getModuleBaseURL();
    return baseURL.substring(0, baseURL.lastIndexOf("/" + GWT.getModuleName()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getTimezones()
   */
  @Override
  public Object[][] getTimezones() {
    if (timezonesArray == null) {
      mapTimezones();
    }
    return timezonesArray;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getUserHash()
   */
  @Override
  public String getUserHash() {
    return userHash;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#getUserTools()
   */
  @Override
  public Collection<ToolSimpleDTO> getUserTools() {
    return initData.getUserTools();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.Session#inSameToken(cc.kune.core.shared.domain
   * .utils.StateToken)
   */
  @Override
  public boolean inSameToken(final StateToken token) {
    return getCurrentStateToken().equals(token);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isCurrentStateAContent()
   */
  @Override
  public boolean isCurrentStateAContent() {
    return currentState instanceof StateContentDTO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isCurrentStateAGroup()
   */
  @Override
  public boolean isCurrentStateAGroup() {
    return currentState == null ? false : !currentState.getGroup().isPersonal();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isCurrentStateAPerson()
   */
  @Override
  public boolean isCurrentStateAPerson() {
    return currentState == null ? false : currentState.getGroup().isPersonal();
  }

  @Override
  public boolean isEmbedded() {
    return isEmbedded;
  }

  @Override
  public boolean isGuiInDevelopment() {
    if (isDev == null) {
      final String isDevParam = WindowUtils.getParameter(SessionConstants.DEVELOPMENT);
      isDev = isDevParam == null ? false : Boolean.valueOf(isDevParam);
    }
    return isDev;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isInCurrentUserSpace()
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isLogged()
   */
  @Override
  public boolean isLogged() {
    return userHash != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isNewbie()
   */
  @Override
  public boolean isNewbie() {
    return currentUserInfo != null ? currentUserInfo.getSignInCount() <= SessionConstants.MIN_SIGN_IN_FOR_NEWBIES
        : true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#isNotLogged()
   */
  @Override
  public boolean isNotLogged() {
    return !isLogged();
  }

  /**
   * Map countries.
   * 
   * @return the object[][]
   */
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

  /**
   * Map langs.
   * 
   * @return the object[][]
   */
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

  /**
   * Map timezones.
   */
  private void mapTimezones() {
    assert initData != null;
    timezonesArray = new Object[initData.getTimezones().length][1];
    for (int i = 0; i < getTimezones().length; i++) {
      final Object[] obj = new Object[] { initData.getTimezones()[i] };
      timezonesArray[i] = obj;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#onAppStart(boolean,
   * cc.kune.core.client.events.AppStartEvent.AppStartHandler)
   */
  @Override
  public HandlerRegistration onAppStart(final boolean fireNow, final AppStartHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(AppStartEvent.getType(), handler);
    if (fireNow && initData != null) {
      handler.onAppStart(new AppStartEvent(initData));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#onUserSignIn(boolean,
   * cc.kune.core.client.events.UserSignInEvent.UserSignInHandler)
   */
  @Override
  public HandlerRegistration onUserSignIn(final boolean fireNow, final UserSignInHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(UserSignInEvent.getType(), handler);
    if (fireNow && isLogged() && currentUserInfo != null) {
      handler.onUserSignIn(new UserSignInEvent(currentUserInfo, null));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#onUserSignInOrSignOut(boolean,
   * cc.kune
   * .core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#onUserSignOut(boolean,
   * cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler)
   */
  @Override
  public HandlerRegistration onUserSignOut(final boolean fireNow, final UserSignOutHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(UserSignOutEvent.getType(), handler);
    if (fireNow && isNotLogged()) {
      handler.onUserSignOut(new UserSignOutEvent());
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.Session#refreshCurrentUserInfo(cc.kune.core.shared
   * .dto.UserInfoDTO)
   */
  @Override
  public void refreshCurrentUserInfo(final UserInfoDTO currentUserInfo) {
    this.currentUserInfo = currentUserInfo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.Session#setCurrentLanguage(cc.kune.core.shared
   * .dto.I18nLanguageDTO)
   */
  @Override
  public void setCurrentLanguage(final I18nLanguageDTO currentLanguage) {
    this.currentLanguage = currentLanguage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.Session#setCurrentState(cc.kune.core.shared.dto
   * .StateAbstractDTO)
   */
  @Override
  public void setCurrentState(final StateAbstractDTO currentState) {
    this.currentState = currentState;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.Session#setCurrentUserInfo(cc.kune.core.shared
   * .dto.UserInfoDTO, java.lang.String)
   */
  @Override
  public void setCurrentUserInfo(final UserInfoDTO currentUserInfo, final String password) {
    this.currentUserInfo = currentUserInfo;
    if (currentUserInfo != null) {
      // First of all, prepare wave panel
      eventBus.fireEvent(new WaveSessionAvailableEvent(currentUserInfo));
      // Later the rest
      eventBus.fireEvent(new UserSignInEvent(this.currentUserInfo, password));
    } else {
      eventBus.fireEvent(new UserSignOutEvent());
    }
    eventBus.fireEvent(new UserSignInOrSignOutEvent(isLogged()));
  }

  @Override
  public void setEmbedded(final boolean isEmbedded) {
    this.isEmbedded = isEmbedded;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#setInitData(cc.kune.core.shared.dto.
   * InitDataDTO)
   */
  @Override
  public void setInitData(final InitDataDTO initData) {
    this.initData = initData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#setUserHash(java.lang.String)
   */
  @Override
  public void setUserHash(final String userHash) {
    this.userHash = userHash;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#signOut()
   */
  @Override
  public void signOut() {
    cookieManager.removeAuthCookie();
    setUserHash(null);
    setCurrentUserInfo(null, null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.Session#userIsJoiningGroups()
   */
  @Override
  public boolean userIsJoiningGroups() {
    return currentUserInfo.getGroupsIsAdmin().size() + currentUserInfo.getGroupsIsCollab().size() > 0;
  }
}
