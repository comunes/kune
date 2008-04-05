
package org.ourproject.kune.docs.client.cnt.folder.viewer.ui;

import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.ui.DefaultContentPanel;

public class FolderViewerPanel extends DefaultContentPanel implements FolderViewerView {

    public FolderViewerPanel() {
        setContent("Folder properties, translations ..." + Site.IN_DEVELOPMENT);
    }
}
