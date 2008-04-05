
package org.ourproject.kune.docs.client.cnt.folder.viewer;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public class FolderViewerPresenter implements FolderViewer {

    private final FolderViewerView view;

    public FolderViewerPresenter(final FolderViewerView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setFolder(final ContainerDTO folder) {
    }
}
