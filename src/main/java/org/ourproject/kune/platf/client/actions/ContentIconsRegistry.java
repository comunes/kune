package org.ourproject.kune.platf.client.actions;

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
