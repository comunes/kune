package cc.kune.docs.client;

import cc.kune.core.client.state.Session;
import cc.kune.docs.client.viewers.DocViewerPresenter;
import cc.kune.docs.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsParts {

    @Inject
    public DocsParts(final Session session, final Provider<DocViewerPresenter> docsViewer,
            final Provider<FolderViewerPresenter> folderViewer, final Provider<DocsClientTool> docsClientTool) {
        docsClientTool.get();
        docsViewer.get();
        folderViewer.get();
    }
}