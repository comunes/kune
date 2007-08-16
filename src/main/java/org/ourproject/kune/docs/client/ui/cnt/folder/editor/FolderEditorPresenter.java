package org.ourproject.kune.docs.client.ui.cnt.folder.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public class FolderEditorPresenter implements FolderEditor {

    private final FolderEditorView view;

    public FolderEditorPresenter(final FolderEditorView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setFolder(final ContainerDTO folder) {

    }
}
