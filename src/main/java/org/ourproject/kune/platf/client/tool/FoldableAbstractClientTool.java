package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.DragDropContentRegistry;
import org.ourproject.kune.platf.client.actions.RenamableContentRegistry;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public abstract class FoldableAbstractClientTool extends AbstractClientTool {
    protected final DragDropContentRegistry dragDropContentRegistry;
    protected final ContentIconsRegistry contentIconsRegistry;
    protected final RenamableContentRegistry renamableContentRegistry;

    public FoldableAbstractClientTool(String shortName, String longName, ToolSelector toolSelector,
            WsThemePresenter wsThemePresenter, WorkspaceSkeleton ws, final ContentIconsRegistry contentIconsRegistry,
            final DragDropContentRegistry dragDropContentRegistry, RenamableContentRegistry renamableContentRegistry) {
        super(shortName, longName, toolSelector, wsThemePresenter, ws);
        this.dragDropContentRegistry = dragDropContentRegistry;
        this.contentIconsRegistry = contentIconsRegistry;
        this.renamableContentRegistry = renamableContentRegistry;
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
