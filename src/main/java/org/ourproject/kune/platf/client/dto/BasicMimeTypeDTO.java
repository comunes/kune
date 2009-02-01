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
 \*/
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BasicMimeTypeDTO implements IsSerializable {

    private String type;
    private String subtype;

    public BasicMimeTypeDTO() {
        this(null, null);
    }

    public BasicMimeTypeDTO(final String mimetype) {
        if (mimetype != null) {
            final String[] split = mimetype.split("/", 2);
            type = split[0];
            if (split.length > 1 && split[1].length() > 0) {
                subtype = split[1];
            }
        }
    }

    public BasicMimeTypeDTO(final String type, final String subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    /**
     * Duplicate code in BMT
     * 
     * @return
     */
    public boolean isImage() {
        return type != null && type.equals("image");
    }

    /**
     * Duplicate code in BMT
     * 
     * @return
     */
    public boolean isPdf() {
        return type != null && subtype != null && type.equals("application") && subtype.equals("pdf");
    }

    /**
     * Duplicate code in BMTDTO
     * 
     * @return
     */
    public boolean isText() {
        return type != null && subtype != null && type.equals("text") && subtype.equals("plain");
    }

    public void setSubtype(final String subtype) {
        this.subtype = subtype;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return subtype == null ? type : type + "/" + subtype;
    }

}
