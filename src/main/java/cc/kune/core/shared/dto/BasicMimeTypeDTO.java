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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BasicMimeTypeDTO implements IsSerializable {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";

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

    public boolean isAvi() {
        return type != null && subtype != null && type.equals("video")
                && (subtype.equals("x-msvideo") || subtype.equals("msvideo"));
    }

    public boolean isFlv() {
        return type != null && subtype != null && type.equals("video")
                && (subtype.equals("flv") || subtype.equals("x-flv"));
    }

    /**
     * Duplicate code in BMT
     * 
     * @return
     */
    public boolean isImage() {
        return type != null && type.equals(IMAGE);
    }

    public boolean isMp3() {
        return type != null && subtype != null && type.equals("audio")
                && (subtype.equals("mp3") || subtype.equals("x-mp3") || (subtype.equals("mpeg")));
    }

    public boolean isOgg() {
        // http://wiki.xiph.org/index.php/MIME_Types_and_File_Extensions
        return type != null && subtype != null && subtype.equals("ogg");
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
