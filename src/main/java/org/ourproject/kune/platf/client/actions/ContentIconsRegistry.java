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
 */package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;

public class ContentIconsRegistry {
    private final HashMap<String, String> contentTypesIcons;

    public ContentIconsRegistry() {
        contentTypesIcons = new HashMap<String, String>();
    }

    public String getContentTypeIcon(final String typeId) {
        final String icon = contentTypesIcons.get(typeId);
        return icon == null ? "" : icon;
    }

    /**
     * If there is a specific icon for a type/subtype pair or a generic type
     * icon in defect
     * 
     * @param typeId
     *            the kune typeId (see *ClientTool)
     * @param mimeType
     * @return
     */
    public String getContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType) {
        String icon = getContentTypeIcon(concatenate(typeId, mimeType));
        if (icon.equals("")) {
            if (mimeType == null) {
                return getContentTypeIcon(typeId);
            }
        } else {
            return icon;
        }
        final String subtype = mimeType.getSubtype();
        if (subtype != null && subtype.length() > 0) {
            icon = getContentTypeIcon(typeId, new BasicMimeTypeDTO(mimeType.getType()));
        }
        return icon.equals("") ? getContentTypeIcon(typeId) : icon;
    }

    public void registerContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType, final String iconUrl) {
        registerContentTypeIcon(concatenate(typeId, mimeType), iconUrl);
    }

    public void registerContentTypeIcon(final String contentTypeId, final String iconUrl) {
        contentTypesIcons.put(contentTypeId, iconUrl);
    }

    private String concatenate(final String typeId, final BasicMimeTypeDTO mimeType) {
        if (mimeType != null) {
            return typeId + "|" + mimeType;
        } else {
            return typeId;
        }
    }
}
