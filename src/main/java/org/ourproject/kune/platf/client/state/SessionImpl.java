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
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;

/**
 * RESPONSABILITIES: - Store the user's application state - Generates URLable's
 * historyTokens
 * 
 * @author danigb
 * 
 */
public class SessionImpl implements Session {
    private String userHash;
    private List<LicenseDTO> licenses;
    private List<I18nLanguageSimpleDTO> languages;
    private List<I18nCountryDTO> countries;
    private Object[][] languagesArray;
    private Object[][] countriesArray;
    private String[] timezones;
    private Object[][] timezonesArray;
    private StateDTO currentState;
    private String[] wsThemes;
    private String defaultWsTheme;
    private I18nLanguageDTO currentLanguage;

    public SessionImpl(final String usersHash, final I18nLanguageDTO initialLang) {
        this.userHash = usersHash;
        licenses = null;
        languages = null;
        languagesArray = null;
        countries = null;
        currentLanguage = initialLang;
    }

    public List<LicenseDTO> getLicenses() {
        return licenses;
    }

    public void setLicenses(final List<LicenseDTO> licenses) {
        this.licenses = licenses;
    }

    public void setCurrent(final StateDTO currentState) {
        this.currentState = currentState;
    }

    public StateDTO getCurrentState() {
        return currentState;
    }

    public void setCurrentState(final StateDTO currentState) {
        this.currentState = currentState;
    }

    public void setDefaultWsTheme(final String defaultWsTheme) {
        this.defaultWsTheme = defaultWsTheme;
    }

    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

    public String[] getWsThemes() {
        return wsThemes;
    }

    public String getDefaultWsTheme() {
        return defaultWsTheme;
    }

    public boolean isLogged() {
        return userHash != null;
    }

    public List<I18nLanguageSimpleDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(final List<I18nLanguageSimpleDTO> languages) {
        this.languages = languages;
    }

    public List<I18nCountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(final List<I18nCountryDTO> countries) {
        this.countries = countries;
    }

    public Object[][] getLanguagesArray() {
        if (languagesArray == null) {
            languagesArray = mapLangs();
        }
        return languagesArray;
    }

    public Object[][] getCountriesArray() {
        if (countriesArray == null) {
            countriesArray = mapCountries();
        }
        return countriesArray;
    }

    public void setTimezones(final String[] timezones) {
        this.timezones = timezones;
    }

    public Object[][] getTimezones() {
        if (timezonesArray == null) {
            mapTimezones();
        }
        return timezonesArray;
    }

    private Object[][] mapCountries() {
        Object[][] objs = new Object[countries.size()][1];
        int i = 0;
        for (Iterator<I18nCountryDTO> iterator = countries.iterator(); iterator.hasNext();) {
            I18nCountryDTO country = iterator.next();
            Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }

    private Object[][] mapLangs() {
        Object[][] objs = new Object[languages.size()][1];
        int i = 0;
        for (Iterator<I18nLanguageSimpleDTO> iterator = languages.iterator(); iterator.hasNext();) {
            I18nLanguageSimpleDTO language = iterator.next();
            Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }

    private void mapTimezones() {
        timezonesArray = new Object[timezones.length][1];
        for (int i = 0; i < timezones.length; i++) {
            Object[] obj = new Object[] { timezones[i] };
            timezonesArray[i] = obj;
        }
    }

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public I18nLanguageDTO getCurrentLanguage() {
        return currentLanguage;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

}
