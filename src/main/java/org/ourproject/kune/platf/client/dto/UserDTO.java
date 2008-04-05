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
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
    private Long id;
    private String name;
    private String shortName;
    private I18nLanguageDTO language;
    private I18nCountryDTO country;
    private TimeZoneDTO timezone;
    private String password;
    private String email;

    public UserDTO() {
        this(null, null, null, null, null, null, null);
    }

    public UserDTO(final String name, final String shortName, final String password, final String email,
            final I18nLanguageDTO language, final I18nCountryDTO country, final TimeZoneDTO timezone) {
        this.name = name;
        this.shortName = shortName;
        this.password = password;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}
