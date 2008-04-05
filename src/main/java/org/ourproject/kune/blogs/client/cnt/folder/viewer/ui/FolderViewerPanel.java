package org.ourproject.kune.blogs.client.cnt.folder.viewer.ui;

import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.workspace.client.workspace.ui.DefaultContentPanel;

public class FolderViewerPanel extends DefaultContentPanel implements FolderViewerView {

    public FolderViewerPanel() {
        setContent("Here folder properties (translation, etcetera)");
    }
}
