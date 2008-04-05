
package org.ourproject.kune.docs.client.ctx.folder;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

public class FolderContextPresenter implements FolderContext {
    private final ContextItems contextItems;

    public FolderContextPresenter(final ContextItems contextItems) {
        this.contextItems = contextItems;
        ContextItemsImages contextImages = ContextItemsImages.App.getInstance();
        contextItems.registerType(DocumentClientTool.TYPE_DOCUMENT, contextImages.pageWhite());
        contextItems.registerType(DocumentClientTool.TYPE_FOLDER, contextImages.folder());
        contextItems.canCreate(DocumentClientTool.TYPE_DOCUMENT, Kune.I18N.t("New document"), DocsEvents.ADD_DOCUMENT);
        contextItems.canCreate(DocumentClientTool.TYPE_FOLDER, Kune.I18N.t("New folder"), DocsEvents.ADD_FOLDER);
        contextItems.setParentTreeVisible(true);
    }

    public View getView() {
        return contextItems.getView();
    }

    public void setContainer(final StateToken state, final ContainerDTO container, final AccessRightsDTO rights) {
        contextItems.showContainer(state, container, rights);
    }

}
