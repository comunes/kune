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
/*
 *
 * This file is part of kune.
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.state;

import java.util.Collection;
import java.util.List;

import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
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

public interface Session {

  void check(AsyncCallbackSimple<Void> callback);

  StateContainerDTO getContainerState();

  StateContentDTO getContentState();

  List<I18nCountryDTO> getCountries();

  Object[][] getCountriesArray();

  String getCurrentCCversion();

  String getCurrentGroupShortName();

  I18nLanguageDTO getCurrentLanguage();

  StateAbstractDTO getCurrentState();

  StateToken getCurrentStateToken();

  UserSimpleDTO getCurrentUser();

  UserInfoDTO getCurrentUserInfo();

  LicenseDTO getDefLicense();

  String getGalleryPermittedExtensions();

  Collection<ToolSimpleDTO> getGroupTools();

  int getImgCropsize();

  int getImgIconsize();

  int getImgResizewidth();

  int getImgThumbsize();

  InitDataDTO getInitData();

  List<I18nLanguageSimpleDTO> getLanguages();

  Object[][] getLanguagesArray();

  List<LicenseDTO> getLicenses();

  boolean getShowDeletedContent();

  String getSiteUrl();

  Object[][] getTimezones();

  String getUserHash();

  Collection<ToolSimpleDTO> getUserTools();

  boolean inSameToken(StateToken token);

  boolean isCurrentStateAContent();

  boolean isCurrentStateAGroup();

  boolean isCurrentStateAPerson();

  boolean isInCurrentUserSpace();

  boolean isLogged();

  boolean isNotLogged();

  HandlerRegistration onAppStart(boolean fireNow, AppStartEvent.AppStartHandler handler);

  /**
   * @param fireNow
   *          if @true the handler is fired after added if the user is loggedin
   * @param handler
   */
  HandlerRegistration onUserSignIn(boolean fireNow, UserSignInEvent.UserSignInHandler handler);

  HandlerRegistration onUserSignInOrSignOut(boolean fireNow,
      UserSignInOrSignOutEvent.UserSignInOrSignOutHandler handler);

  /**
   * @param fireNow
   *          if @true the handler is fired after added if the user is loggedin
   * @param handler
   */
  HandlerRegistration onUserSignOut(boolean fireNow, UserSignOutEvent.UserSignOutHandler handler);

  void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

  void setCurrentState(final StateAbstractDTO currentState);

  void setCurrentUserInfo(UserInfoDTO currentUserInfo, String passwd);

  void setInitData(InitDataDTO initData);

  void setUserHash(String userHash);

  void signOut();

  /**
   * Checks if the user is newbie.
   *
   * @return true, if is the user is a newbie
   */
  boolean isNewbie();

  /**
   * User is joining to some groups (as admin or collaborator).
   *
   * @return true, if successful
   */
  boolean userIsJoiningGroups();

}