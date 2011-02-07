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

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nLanguageDTO implements IsSerializable {

    private String code;
    private String englishName;
    private String nativeName;
    private String direction;
    private String pluralization;
    private String dateFormat;
    private String dateFormatShort;

    public String getCode() {
        return code;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getDateFormatShort() {
        return dateFormatShort;
    }

    public String getDirection() {
        return direction;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getPluralization() {
        return pluralization;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDateFormatShort(String dateFormatShort) {
        this.dateFormatShort = dateFormatShort;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public void setEnglishName(final String englishName) {
        this.englishName = englishName;
    }

    public void setNativeName(final String nativeName) {
        this.nativeName = nativeName;
    }

    public void setPluralization(final String pluralization) {
        this.pluralization = pluralization;
    }

}
