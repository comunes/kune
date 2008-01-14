/*
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

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface Session {

    /**
     * Duration remembering login: 2 weeks
     */
    public static int SESSION_DURATION = 1000 * 60 * 60 * 24 * 14;

    public List getLicenses();

    public void setLicenses(final List licenses);

    public void setCurrent(final StateDTO currentState);

    public StateDTO getCurrentState();

    public void setCurrentState(final StateDTO currentState);

    public void setDefaultWsTheme(final String defaultWsTheme);

    public void setWsThemes(final String[] wsThemes);

    public String[] getWsThemes();

    public String getDefaultWsTheme();

    public boolean isLogged();

    public List getLanguages();

    public void setLanguages(final List languages);

    public List getCountries();

    public void setCountries(final List countries);

    public Object[][] getLanguagesArray();

    public Object[][] getCountriesArray();

    public void setTimezones(final String[] timezones);

    public String[] getTimezones();

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

    public I18nLanguageDTO getCurrentLanguage();

    String getUserHash();

    void setUserHash(String userHash);

}