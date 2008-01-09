/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.state;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO;
import org.ourproject.kune.workspace.client.dto.StateDTO;

/**
 * RESPONSABILITIES: - Store the user's application state - Generates URLable's
 * historyTokens
 * 
 * @author danigb
 * 
 */
public class SessionImpl implements Session {
    private String userHash;
    private List licenses;
    private List languages;
    private List countries;
    private Object[][] languagesArray;
    private Object[][] countriesArray;
    private StateDTO currentState;
    private String[] wsThemes;
    private String defaultWsTheme;
    private String[] timezones;
    private I18nLanguageDTO currentLanguage;

    public SessionImpl(final String usersHash, final I18nLanguageDTO initialLang) {
        this.userHash = usersHash;
        licenses = null;
        languages = null;
        languagesArray = null;
        countries = null;
        currentLanguage = initialLang;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getLicenses()
     */
    public List getLicenses() {
        return licenses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setLicenses(java.util.List)
     */
    public void setLicenses(final List licenses) {
        this.licenses = licenses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setCurrent(org.ourproject.kune.workspace.client.dto.StateDTO)
     */
    public void setCurrent(final StateDTO currentState) {
        this.currentState = currentState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getCurrentState()
     */
    public StateDTO getCurrentState() {
        return currentState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setCurrentState(org.ourproject.kune.workspace.client.dto.StateDTO)
     */
    public void setCurrentState(final StateDTO currentState) {
        this.currentState = currentState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setDefaultWsTheme(java.lang.String)
     */
    public void setDefaultWsTheme(final String defaultWsTheme) {
        this.defaultWsTheme = defaultWsTheme;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setWsThemes(java.lang.String[])
     */
    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getWsThemes()
     */
    public String[] getWsThemes() {
        return wsThemes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getDefaultWsTheme()
     */
    public String getDefaultWsTheme() {
        return defaultWsTheme;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#isLogged()
     */
    public boolean isLogged() {
        return userHash != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getLanguages()
     */
    public List getLanguages() {
        return languages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setLanguages(java.util.List)
     */
    public void setLanguages(final List languages) {
        this.languages = languages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getCountries()
     */
    public List getCountries() {
        return countries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#setCountries(java.util.List)
     */
    public void setCountries(final List countries) {
        this.countries = countries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getLanguagesArray()
     */
    public Object[][] getLanguagesArray() {
        if (languagesArray == null) {
            languagesArray = mapLangs(languages);
        }
        return languagesArray;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ourproject.kune.platf.client.state.Session#getCountriesArray()
     */
    public Object[][] getCountriesArray() {
        if (countriesArray == null) {
            countriesArray = mapCountries(countries);
        }
        return countriesArray;
    }

    private Object[][] mapCountries(final List countries) {
        Object[][] objs = new Object[countries.size()][1];
        int i = 0;
        for (Iterator iterator = countries.iterator(); iterator.hasNext();) {
            I18nCountryDTO country = (I18nCountryDTO) iterator.next();
            Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }

    private Object[][] mapLangs(final List languages) {
        Object[][] objs = new Object[languages.size()][1];
        int i = 0;
        for (Iterator iterator = languages.iterator(); iterator.hasNext();) {
            I18nLanguageSimpleDTO language = (I18nLanguageSimpleDTO) iterator.next();
            Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }

    public void setTimezones(final String[] timezones) {
        this.timezones = timezones;
    }

    public String[] getTimezones() {
        return timezones;
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
