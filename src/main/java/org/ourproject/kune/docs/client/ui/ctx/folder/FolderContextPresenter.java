package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;

public class FolderContextPresenter implements FolderContext {
    private final FolderContentView view;

    public FolderContextPresenter(final FolderContentView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

}
