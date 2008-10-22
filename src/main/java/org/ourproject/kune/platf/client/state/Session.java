/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public interface Session {

    /**
     * Duration remembering login: 2 weeks
     */
    int SESSION_DURATION = 1000 * 60 * 60 * 24 * 14;

    void check(AsyncCallbackSimple<?> callback);

    List<I18nCountryDTO> getCountries();

    Object[][] getCountriesArray();

    I18nLanguageDTO getCurrentLanguage();

    StateDTO getCurrentState();

    StateToken getCurrentStateToken();

    UserInfoDTO getCurrentUserInfo();

    String getGalleryPermittedExtensions();

    List<I18nLanguageSimpleDTO> getLanguages();

    Object[][] getLanguagesArray();

    List<LicenseDTO> getLicenses();

    Object[][] getTimezones();

    String getUserHash();

    boolean isLogged();

    void onInitDataReceived(Listener<InitDataDTO> listener);

    void onUserSignIn(Listener<UserInfoDTO> listener);

    void onUserSignOut(Listener0 listener);

    void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

    void setCurrentState(final StateDTO currentState);

    void setCurrentUserInfo(UserInfoDTO currentUserInfo);

    void setInitData(InitDataDTO initData);

    void setUserHash(String userHash);

}