package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

public class ContentIconsRegistry {
    private final HashMap<String, String> contentTypesIcons;

    public ContentIconsRegistry() {
	contentTypesIcons = new HashMap<String, String>();
    }

    public String getContentTypeIcon(final String typeId) {
	final String icon = contentTypesIcons.get(typeId);
	return icon == null ? "" : icon;
    }

    public void registerContentTypeIcon(final String contentTypeId, final String iconUrl) {
	contentTypesIcons.put(contentTypeId, iconUrl);
    }
}
