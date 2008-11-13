package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public abstract class FoldableAbstractClientTool extends AbstractClientTool {
    protected final ContentIconsRegistry contentIconsRegistry;
    protected final ContentCapabilitiesRegistry contentCapabilitiesRegistry;

    public FoldableAbstractClientTool(String shortName, String longName, ToolSelector toolSelector,
            WsThemePresenter wsThemePresenter, WorkspaceSkeleton ws, final ContentIconsRegistry contentIconsRegistry,
            ContentCapabilitiesRegistry contentCapabilitiesRegistry) {
        super(shortName, longName, toolSelector, wsThemePresenter, ws);
        this.contentIconsRegistry = contentIconsRegistry;
        this.contentCapabilitiesRegistry = contentCapabilitiesRegistry;
        registerDragDropTypes();
        registerIcons();
        registerRenamableTypes();
    }

    protected void registerDragDropTypes() {
    }

    protected void registerIcons() {
    }

    protected void registerRenamableTypes() {
    }
}
