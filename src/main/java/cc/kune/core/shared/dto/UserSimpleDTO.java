/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserSimpleDTO implements IsSerializable {
    private I18nCountryDTO country;
    private boolean hasLogo;
    private Long id;
    private I18nLanguageDTO language;
    private String name;
    private String shortName;
    private StateToken stateToken;
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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserSimpleDTO other = (UserSimpleDTO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (shortName == null) {
            if (other.shortName != null) {
                return false;
            }
        } else if (!shortName.equals(other.shortName)) {
            return false;
        }
        return true;
    }

    public I18nCountryDTO getCountry() {
        return country;
    }

    public boolean getHasLogo() {
        return hasLogo;
    }

    public Long getId() {
        return id;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public StateToken getStateToken() {
        return stateToken;
    }

    public TimeZoneDTO getTimezone() {
        return timezone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
        return result;
    }

    public boolean hasLogo() {
        return hasLogo;
    }

    public void setCountry(final I18nCountryDTO country) {
        this.country = country;
    }

    public void setHasLogo(final boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setStateToken(final StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTimezone(final TimeZoneDTO timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "UserSimpleDTO(" + shortName + ")";
    }

}
