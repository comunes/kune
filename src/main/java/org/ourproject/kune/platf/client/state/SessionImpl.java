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

import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Slot;

/**
 * RESPONSABILITIES: - Store the user's application state - Generates URLable's
 * historyTokens
 * 
 * @author danigb
 * 
 */
public class SessionImpl implements Session {
    private String userHash;
    private Object[][] languagesArray;
    private Object[][] countriesArray;
    private Object[][] timezonesArray;
    private StateDTO currentState;
    private I18nLanguageDTO currentLanguage;
    private InitDataDTO initData;
    private final Signal<InitDataDTO> onInitDataReceived;

    public SessionImpl(final String userHash, final I18nLanguageDTO initialLanguage) {
	this.userHash = userHash;
	currentLanguage = initialLanguage;
	languagesArray = null;
	this.onInitDataReceived = new Signal<InitDataDTO>("initDataReceived");
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

    public void setCurrent(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage) {
	this.currentLanguage = currentLanguage;
    }

    public void setCurrentState(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public void setInitData(final InitDataDTO initData) {
	this.initData = initData;
	onInitDataReceived.fire(initData);
    }

    public void setUserHash(final String userHash) {
	this.userHash = userHash;
    }

    private Object[][] mapCountries() {
	final Object[][] objs = new Object[getCountries().size()][1];
	int i = 0;
	for (final Iterator<I18nCountryDTO> iterator = getCountries().iterator(); iterator.hasNext();) {
	    final I18nCountryDTO country = iterator.next();
	    final Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
	    objs[i++] = obj;
	}
	return objs;
    }

    private Object[][] mapLangs() {
	final Object[][] objs = new Object[getLanguages().size()][1];
	int i = 0;
	for (final Iterator<I18nLanguageSimpleDTO> iterator = getLanguages().iterator(); iterator.hasNext();) {
	    final I18nLanguageSimpleDTO language = iterator.next();
	    final Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
	    objs[i++] = obj;
	}
	return objs;
    }

    private void mapTimezones() {
	timezonesArray = new Object[getTimezones().length][1];
	for (int i = 0; i < getTimezones().length; i++) {
	    final Object[] obj = new Object[] { getTimezones()[i] };
	    timezonesArray[i] = obj;
	}
    }

}
