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
 \*/
package org.ourproject.kune.platf.server.domain;

import javax.persistence.Embeddable;

@Embeddable
public class BasicMimeType {

    private String mimetype;
    private String mimesubtype;

    public BasicMimeType() {
        this(null, null);
    }

    public BasicMimeType(final String mimetype) {
        if (mimetype != null) {
            final String[] split = mimetype.split("/", 2);
            this.mimetype = split[0];
            if (split.length > 1 && split[1].length() > 0) {
                this.mimesubtype = split[1];
            }
        }
    }

    public BasicMimeType(final String type, final String subtype) {
        this.mimetype = type;
        this.mimesubtype = subtype;
    }

    public String getSubtype() {
        return mimesubtype;
    }

    public String getType() {
        return mimetype;
    }

    /**
     * Duplicate code in BMTDTO
     * 
     * @return
     */
    public boolean isImage() {
        return mimetype != null && mimetype.equals("image");
    }

    /**
     * Duplicate code in BMTDTO
     * 
     * @return
     */
    public boolean isPdf() {
        return mimetype != null && mimesubtype != null && mimetype.equals("application") && mimesubtype.equals("pdf");
    }

    /**
     * Duplicate code in BMTDTO
     * 
     * @return
     */
    public boolean isText() {
        return mimetype != null && mimesubtype != null && mimetype.equals("text") && mimesubtype.equals("plain");
    }

    public void setSubtype(final String subtype) {
        this.mimesubtype = subtype;
    }

    public void setType(final String type) {
        this.mimetype = type;
    }

    @Override
    public String toString() {
        return mimesubtype == null ? mimetype : mimetype + "/" + mimesubtype;
    }

}
