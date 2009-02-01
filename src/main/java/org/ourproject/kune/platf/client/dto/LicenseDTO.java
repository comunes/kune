/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

public class LicenseDTO implements IsSerializable {

    private String shortName;

    private String longName;

    private String description;

    private String url;

    private boolean isCC;

    private boolean isCopyleft;

    private boolean isDeprecated;

    private String rdf;

    private String imageUrl;

    public LicenseDTO() {
        this(null, null, null, null, false, false, false, null, null);
    }

    public LicenseDTO(final String shortName, final String longName, final String description, final String url,
            final boolean isCC, final boolean isCopyleft, final boolean isDeprecated, final String rdf,
            final String imageUrl) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.url = url;
        this.isCC = isCC;
        this.isCopyleft = isCopyleft;
        this.isDeprecated = isDeprecated;
        this.rdf = rdf;
        this.imageUrl = imageUrl;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(final String longName) {
        this.longName = longName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRdf() {
        return rdf;
    }

    public void setRdf(final String rdf) {
        this.rdf = rdf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isCC() {
        return isCC;
    }

    public boolean isCopyleft() {
        return isCopyleft;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setCC(final boolean isCC) {
        this.isCC = isCC;
    }

    public void setCopyleft(final boolean isCopyleft) {
        this.isCopyleft = isCopyleft;
    }

    public void setDeprecated(final boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

}
