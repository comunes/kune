package org.ourproject.kune.platf.client.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface Session {

    /**
     * Duration remembering login: 2 weeks
     */
    public static int SESSION_DURATION = 1000 * 60 * 60 * 24 * 14;

    public List<LicenseDTO> getLicenses();

    public void setLicenses(final List<LicenseDTO> licenses);

    public void setCurrent(final StateDTO currentState);

    public StateDTO getCurrentState();

    public void setCurrentState(final StateDTO currentState);

    public void setDefaultWsTheme(final String defaultWsTheme);

    public void setWsThemes(final String[] wsThemes);

    public String[] getWsThemes();

    public String getDefaultWsTheme();

    public boolean isLogged();

    public List<I18nLanguageSimpleDTO> getLanguages();

    public void setLanguages(final List<I18nLanguageSimpleDTO> languages);

    public List<I18nCountryDTO> getCountries();

    public void setCountries(final List<I18nCountryDTO> countries);

    public Object[][] getLanguagesArray();

    public Object[][] getCountriesArray();

    public void setTimezones(final String[] timezones);

    public Object[][] getTimezones();

    public void setCurrentLanguage(final I18nLanguageDTO currentLanguage);

    public I18nLanguageDTO getCurrentLanguage();

    String getUserHash();

    void setUserHash(String userHash);

}