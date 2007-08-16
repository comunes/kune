package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.docs.client.actions.AddDocument;
import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

public class FolderContextPresenter implements FolderContext {
    private final ContextItems contextItems;

    public FolderContextPresenter(final ContextItems contextItems) {
	this.contextItems = contextItems;
	ContextItemsImages contextImages = ContextItemsImages.App.getInstance();
	contextItems.registerType("file", contextImages.pageWhite());
	contextItems.registerType("folder", contextImages.folder());
	// i18n
	contextItems.canCreate("file", "Add new document", AddDocument.EVENT);
	contextItems.canCreate("folder", "Add new folder", AddFolder.EVENT);
	contextItems.setParentTreeVisible(true);
    }

    public View getView() {
	return contextItems.getView();
    }

    public void setContainer(final StateToken state, final ContainerDTO container, final AccessRightsDTO rights) {
	contextItems.setContainer(state, container, rights);
    }

}
