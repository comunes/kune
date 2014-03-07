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
package cc.kune.core.client.state;

import java.util.Collection;
import java.util.List;

import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent;
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

import com.google.gwt.event.shared.HandlerRegistration;

// TODO: Auto-generated Javadoc
/**
 * The Interface Session.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface Session {

  /**
   * Gets the container state.
   * 
   * @return the container state
   */
  StateContainerDTO getContainerState();

  /**
   * Gets the content state.
   * 
   * @return the content state
   */
  StateContentDTO getContentState();

  /**
   * Gets the countries.
   * 
   * @return the countries
   */
  List<I18nCountryDTO> getCountries();

  /**
   * Gets the countries array.
   * 
   * @return the countries array
   */
  Object[][] getCountriesArray();

  /**
   * Gets the current c cversion.
   * 
   * @return the current c cversion
   */
  String getCurrentCCversion();

  /**
   * Gets the current group short name.
   * 
   * @return the current group short name
   */
  String getCurrentGroupShortName();

  /**
   * Gets the current language.
   * 
   * @return the current language
   */
  I18nLanguageDTO getCurrentLanguage();

  /**
   * Gets the current state.
   * 
   * @return the current state
   */
  StateAbstractDTO getCurrentState();

  /**
   * Gets the current state token.
   * 
   * @return the current state token
   */
  StateToken getCurrentStateToken();

  /**
   * Gets the current user.
   * 
   * @return the current user
   */
  UserSimpleDTO getCurrentUser();

  /**
   * Gets the current user info.
   * 
   * @return the current user info
   */
  UserInfoDTO getCurrentUserInfo();

  /**
   * Gets the def license.
   * 
   * @return the def license
   */
  LicenseDTO getDefLicense();

  /**
   * Gets the full translated languages.
   * 
   * @return the full translated languages
   */
  List<I18nLanguageSimpleDTO> getFullTranslatedLanguages();

  /**
   * Gets the gallery permitted extensions.
   * 
   * @return the gallery permitted extensions
   */
  String getGalleryPermittedExtensions();

  /**
   * Gets the group tools.
   * 
   * @return the group tools
   */
  Collection<ToolSimpleDTO> getGroupTools();

  /**
   * Gets the img cropsize.
   * 
   * @return the img cropsize
   */
  int getImgCropsize();

  /**
   * Gets the img iconsize.
   * 
   * @return the img iconsize
   */
  int getImgIconsize();

  /**
   * Gets the img resizewidth.
   * 
   * @return the img resizewidth
   */
  int getImgResizewidth();

  /**
   * Gets the img thumbsize.
   * 
   * @return the img thumbsize
   */
  int getImgThumbsize();

  /**
   * Gets the inits the data.
   * 
   * @return the inits the data
   */
  InitDataDTO getInitData();

  /**
   * Gets the languages.
   * 
   * @return the languages
   */
  List<I18nLanguageSimpleDTO> getLanguages();

  /**
   * Gets the languages array.
   * 
   * @return the languages array
   */
  Object[][] getLanguagesArray();

  /**
   * Gets the licenses.
   * 
   * @return the licenses
   */
  List<LicenseDTO> getLicenses();

  /**
   * Gets the show deleted content.
   * 
   * @return the show deleted content
   */
  boolean getShowDeletedContent();

  /**
   * Gets the site url.
   * 
   * @return the site url
   */
  String getSiteUrl();

  /**
   * Gets the timezones.
   * 
   * @return the timezones
   */
  Object[][] getTimezones();

  /**
   * Gets the user hash.
   * 
   * @return the user hash
   */
  String getUserHash();

  /**
   * Gets the user tools.
   * 
   * @return the user tools
   */
  Collection<ToolSimpleDTO> getUserTools();

  /**
   * In same token.
   * 
   * @param token
   *          the token
   * @return true, if successful
   */
  boolean inSameToken(StateToken token);

  /**
   * Checks if is current state a content.
   * 
   * @return true, if is current state a content
   */
  boolean isCurrentStateAContent();

  /**
   * Checks if is current state a group.
   * 
   * @return true, if is current state a group
   */
  boolean isCurrentStateAGroup();

  /**
   * Checks if is current state a person.
   * 
   * @return true, if is current state a person
   */
  boolean isCurrentStateAPerson();

  /**
   * Shows if is a embedded client.
   * 
   * @return true, if is embed
   */
  boolean isEmbedded();

  /**
   * If we have using the development version of the Gui (param ?dev=true)
   */
  boolean isGuiInDevelopment();

  /**
   * Checks if is in current user space.
   * 
   * @return true, if is in current user space
   */
  boolean isInCurrentUserSpace();

  /**
   * Checks if is logged.
   * 
   * @return true, if is logged
   */
  boolean isLogged();

  /**
   * Checks if the user is newbie.
   * 
   * @return true, if is the user is a newbie
   */
  boolean isNewbie();

  /**
   * Checks if is not logged.
   * 
   * @return true, if is not logged
   */
  boolean isNotLogged();

  /**
   * On app start.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onAppStart(boolean fireNow, AppStartEvent.AppStartHandler handler);

  /**
   * On user sign in.
   * 
   * @param fireNow
   *          if @true the handler is fired after added if the user is loggedin
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onUserSignIn(boolean fireNow, UserSignInEvent.UserSignInHandler handler);

  /**
   * On user sign in or sign out.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onUserSignInOrSignOut(boolean fireNow,
      UserSignInOrSignOutEvent.UserSignInOrSignOutHandler handler);

  /**
   * On user sign out.
   * 
   * @param fireNow
   *          if @true the handler is fired after added if the user is loggedin
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onUserSignOut(boolean fireNow, UserSignOutEvent.UserSignOutHandler handler);

  /**
   * Refresh current user info (without fire sign in events).
   * 
   * @param userInfo
   *          the user info
   */
  void refreshCurrentUserInfo(UserInfoDTO userInfo);

  /**
   * Sets the current language.
   * 
   * @param currentLanguage
   *          the new current language
   */
  void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

  /**
   * Sets the current state.
   * 
   * @param currentState
   *          the new current state
   */
  void setCurrentState(final StateAbstractDTO currentState);

  /**
   * Sets the current user info.
   * 
   * @param currentUserInfo
   *          the current user info
   * @param passwd
   *          the passwd
   */
  void setCurrentUserInfo(UserInfoDTO currentUserInfo, String passwd);

  void setEmbedded(boolean isEmbedded);

  /**
   * Sets the inits the data.
   * 
   * @param initData
   *          the new inits the data
   */
  void setInitData(InitDataDTO initData);

  /**
   * Sets the user hash.
   * 
   * @param userHash
   *          the new user hash
   */
  void setUserHash(String userHash);

  /**
   * Sign out.
   */
  void signOut();

  /**
   * User is joining to some groups (as admin or collaborator).
   * 
   * @return true, if successful
   */
  boolean userIsJoiningGroups();

}
