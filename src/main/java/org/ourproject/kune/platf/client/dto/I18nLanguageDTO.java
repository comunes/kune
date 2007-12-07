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

package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nLanguageDTO implements IsSerializable {

    private String code;
    private String englishName;
    private String nativeName;
    private String direction;
    private String pluralization;

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getDirection() {
        return direction;
    }

    public String getPluralization() {
        return pluralization;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setEnglishName(final String englishName) {
        this.englishName = englishName;
    }

    public void setNativeName(final String nativeName) {
        this.nativeName = nativeName;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public void setPluralization(final String pluralization) {
        this.pluralization = pluralization;
    }

}
