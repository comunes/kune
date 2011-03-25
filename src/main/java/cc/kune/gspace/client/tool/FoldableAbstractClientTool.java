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
package cc.kune.gspace.client.tool;


import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;

public abstract class FoldableAbstractClientTool extends AbstractClientTool {
    public static final String UPLOADEDFILE_SUFFIX = "uploaded";
    public static final String WAVE_SUFFIX = "wave";

    protected final ContentCapabilitiesRegistry contentCapabilitiesRegistry;

    public FoldableAbstractClientTool(final String shortName, final String longName, final ToolSelector toolSelector,
            final ContentCapabilitiesRegistry contentCapabilitiesRegistry) {
        super(shortName, longName, toolSelector);
        this.contentCapabilitiesRegistry = contentCapabilitiesRegistry;
    }

    protected void registerAclEditableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getAclEditable().register(typeIds);
    }

    protected void registerAuthorableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getAuthorable().register(typeIds);
    }

    protected void registerComentableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getComentable().register(typeIds);
    }

    public void registerContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType, final String iconUrl) {
        contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(typeId, mimeType, iconUrl);
    }

    public void registerContentTypeIcon(final String contentTypeId, final String iconUrl) {
        contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, iconUrl);
    }

    protected void registerDragableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getDragable().register(typeIds);
    }

    protected void registerDropableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getDropable().register(typeIds);
    }

    protected void registerEmailSubscribeAbleTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getEmailSubscribeAble().register(typeIds);
    }

    protected void registerLicensableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getLicensable().register(typeIds);
    }

    protected void registerPublishModerableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getPublishModerable().register(typeIds);
    }

    protected void registerRateableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getRateable().register(typeIds);
    }

    protected void registerRenamableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getRenamable().register(typeIds);
    }

    protected void registerTageableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getTageable().register(typeIds);
    }

    protected void registerTranslatableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getTranslatable().register(typeIds);
    }

    protected void registerUploadTypesAndMimes(final String typeUploadedfile) {
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("image"), "images/nav/picture.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("video"), "images/nav/film.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("audio"), "images/nav/music.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "pdf"), "images/nav/page_pdf.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "zip"), "images/nav/page_zip.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "zip"), "images/nav/page_zip.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("text"), "images/nav/page_text.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "msword"),
                "images/nav/page_word.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "excel"),
                "images/nav/page_excel.png");
        registerContentTypeIcon(typeUploadedfile, new BasicMimeTypeDTO("application", "mspowerpoint"),
                "images/nav/page_pps.png");
        registerContentTypeIcon(typeUploadedfile, "images/nav/page.png");
    }

    protected void registerVersionableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getVersionable().register(typeIds);
    }

    protected void registerXmppComentableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getXmppComentable().register(typeIds);
    }

    protected void registerXmppNotifyCapableTypes(final String... typeIds) {
        contentCapabilitiesRegistry.getXmppNotificyCapable().register(typeIds);
    }
}
