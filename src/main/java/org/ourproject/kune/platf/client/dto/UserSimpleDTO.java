package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserSimpleDTO implements IsSerializable {

    private Long id;

    private String name;

    private String shortName;

    private I18nLanguageDTO language;

    private I18nCountryDTO country;

    private TimeZoneDTO timezone;

    public UserSimpleDTO() {
        this(null, null, null, null, null);
    }

    public UserSimpleDTO(final String name, final String shortName, final I18nLanguageDTO language,
            final I18nCountryDTO country, final TimeZoneDTO timezone) {
        this.name = name;
        this.shortName = shortName;
        this.language = language;
        this.country = country;
        this.timezone = timezone;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public I18nCountryDTO getCountry() {
        return country;
    }

    public void setCountry(final I18nCountryDTO country) {
        this.country = country;
    }

    public TimeZoneDTO getTimezone() {
        return timezone;
    }

    public void setTimezone(final TimeZoneDTO timezone) {
        this.timezone = timezone;
    }

}
