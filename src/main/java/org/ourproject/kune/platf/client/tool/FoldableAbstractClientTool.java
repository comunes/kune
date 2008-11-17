package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public abstract class FoldableAbstractClientTool extends AbstractClientTool {
    protected final ContentCapabilitiesRegistry contentCapabilitiesRegistry;

    public FoldableAbstractClientTool(String shortName, String longName, ToolSelector toolSelector,
            WsThemePresenter wsThemePresenter, WorkspaceSkeleton ws,
            ContentCapabilitiesRegistry contentCapabilitiesRegistry) {
        super(shortName, longName, toolSelector, wsThemePresenter, ws);
        this.contentCapabilitiesRegistry = contentCapabilitiesRegistry;
    }

    public void registerContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType, final String iconUrl) {
        contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(typeId, mimeType, iconUrl);
    }

    public void registerContentTypeIcon(final String contentTypeId, final String iconUrl) {
        contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, iconUrl);
    }

    protected void registerAclEditableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getAclEditable().register(typeIds);
    }

    protected void registerAuthorableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getAuthorable().register(typeIds);
    }

    protected void registerComentableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getComentable().register(typeIds);
    }

    protected void registerDragableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getDragable().register(typeIds);
    }

    protected void registerDropableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getDropable().register(typeIds);
    }

    protected void registerEmailSubscribeAbleTypes(String... typeIds) {
        contentCapabilitiesRegistry.getEmailSubscribeAble().register(typeIds);
    }

    protected void registerLicensableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getLicensable().register(typeIds);
    }

    protected void registerPublishModerableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getPublishModerable().register(typeIds);
    }

    protected void registerRateableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getRateable().register(typeIds);
    }

    protected void registerRenamableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getRenamable().register(typeIds);
    }

    protected void registerTageableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getTageable().register(typeIds);
    }

    protected void registerTranslatableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getTranslatable().register(typeIds);
    }

    protected void registerVersionableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getVersionable().register(typeIds);
    }

    protected void registerXmppComentableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getXmppComentable().register(typeIds);
    }

    protected void registerXmppNotifyCapableTypes(String... typeIds) {
        contentCapabilitiesRegistry.getXmppNotificyCapable().register(typeIds);
    }
}
