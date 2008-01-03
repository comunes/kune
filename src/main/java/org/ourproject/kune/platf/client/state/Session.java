package org.ourproject.kune.platf.client.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface Session {

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