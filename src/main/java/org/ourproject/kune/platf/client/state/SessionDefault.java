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

package org.ourproject.kune.platf.client.state;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Signal0;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;

public class SessionDefault implements Session {
    private String userHash;
    private InitDataDTO initData;
    private UserInfoDTO currentUserInfo;
    private Object[][] languagesArray;
    private Object[][] countriesArray;
    private Object[][] timezonesArray;
    private StateDTO currentState;
    private I18nLanguageDTO currentLanguage;
    private final Signal<InitDataDTO> onInitDataReceived;
    private final Signal<UserInfoDTO> onUserSignIn;
    private final Signal0 onUserSignOut;
    private final Provider<UserServiceAsync> userServiceProvider;

    public SessionDefault(final String userHash, final Provider<UserServiceAsync> userServiceProvider) {
	this.userHash = userHash == null || userHash.equals("null") ? null : userHash;
	this.userServiceProvider = userServiceProvider;
	languagesArray = null;
	this.onInitDataReceived = new Signal<InitDataDTO>("initDataReceived");
	this.onUserSignIn = new Signal<UserInfoDTO>("onUserSignIn");
	this.onUserSignOut = new Signal0("onUserSignOut");
    }

    public void check(final AsyncCallbackSimple<?> callback) {
	Log.debug("Checking session (userhash: " + getUserHash() + ")");
	userServiceProvider.get().onlyCheckSession(getUserHash(), callback);
    }

    public List<I18nCountryDTO> getCountries() {
	return initData.getCountries();
    }

    public Object[][] getCountriesArray() {
	if (countriesArray == null) {
	    countriesArray = mapCountries();
	}
	return countriesArray;
    }

    public I18nLanguageDTO getCurrentLanguage() {
	return currentLanguage;
    }

    public StateDTO getCurrentState() {
	return currentState;
    }

    public UserInfoDTO getCurrentUserInfo() {
	return currentUserInfo;
    }

    public List<I18nLanguageSimpleDTO> getLanguages() {
	return initData.getLanguages();
    }

    public Object[][] getLanguagesArray() {
	if (languagesArray == null) {
	    languagesArray = mapLangs();
	}
	return languagesArray;
    }

    public List<LicenseDTO> getLicenses() {
	return initData.getLicenses();
    }

    public Object[][] getTimezones() {
	if (timezonesArray == null) {
	    mapTimezones();
	}
	return timezonesArray;
    }

    public String getUserHash() {
	return userHash;
    }

    public boolean isLogged() {
	return userHash != null;
    }

    public void onInitDataReceived(final Slot<InitDataDTO> slot) {
	onInitDataReceived.add(slot);
    }

    public void onUserSignIn(final Slot<UserInfoDTO> slot) {
	onUserSignIn.add(slot);
    }

    public void onUserSignOut(final Slot0 slot) {
	onUserSignOut.add(slot);
    }

    public void setCurrent(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage) {
	this.currentLanguage = currentLanguage;
    }

    public void setCurrentState(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public void setCurrentUserInfo(final UserInfoDTO currentUserInfo) {
	this.currentUserInfo = currentUserInfo;
	if (currentUserInfo != null) {
	    onUserSignIn.fire(currentUserInfo);
	} else {
	    onUserSignOut.fire();
	}
    }

    public void setInitData(final InitDataDTO initData) {
	this.initData = initData;
	onInitDataReceived.fire(initData);
    }

    public void setUserHash(final String userHash) {
	this.userHash = userHash;
    }

    private Object[][] mapCountries() {
	final Object[][] objs = new Object[initData.getCountries().size()][1];
	int i = 0;
	for (final Iterator<I18nCountryDTO> iterator = initData.getCountries().iterator(); iterator.hasNext();) {
	    final I18nCountryDTO country = iterator.next();
	    final Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
	    objs[i++] = obj;
	}
	return objs;
    }

    private Object[][] mapLangs() {
	final Object[][] objs = new Object[initData.getLanguages().size()][1];
	int i = 0;
	for (final Iterator<I18nLanguageSimpleDTO> iterator = initData.getLanguages().iterator(); iterator.hasNext();) {
	    final I18nLanguageSimpleDTO language = iterator.next();
	    final Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
	    objs[i++] = obj;
	}
	return objs;
    }

    private void mapTimezones() {
	timezonesArray = new Object[initData.getTimezones().length][1];
	for (int i = 0; i < getTimezones().length; i++) {
	    final Object[] obj = new Object[] { initData.getTimezones()[i] };
	    timezonesArray[i] = obj;
	}
    }

}
