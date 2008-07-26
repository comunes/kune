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
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;

import com.calclab.suco.client.signal.Slot;

public interface Session {

    /**
     * Duration remembering login: 2 weeks
     */
    public static int SESSION_DURATION = 1000 * 60 * 60 * 24 * 14;

    public void check(AsyncCallbackSimple<?> callback);

    public List<I18nCountryDTO> getCountries();

    public Object[][] getCountriesArray();

    public I18nLanguageDTO getCurrentLanguage();

    public StateDTO getCurrentState();

    public List<I18nLanguageSimpleDTO> getLanguages();

    public Object[][] getLanguagesArray();

    public List<LicenseDTO> getLicenses();

    public Object[][] getTimezones();

    public boolean isLogged();

    public void setCurrent(final StateDTO currentState);

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

    public void setCurrentState(final StateDTO currentState);

    public void setInitData(InitDataDTO initData);

    String getUserHash();

    void onInitDataReceived(Slot<InitDataDTO> slot);

    void onUserSignIn(Slot<UserInfoDTO> slot);

    void onUserSignOut(Slot<Object> slot);

    void setCurrentUserInfo(UserInfoDTO currentUserInfo);

    void setUserHash(String userHash);

}