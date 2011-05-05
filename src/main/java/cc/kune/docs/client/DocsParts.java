package cc.kune.docs.client;

import cc.kune.core.client.state.Session;
import cc.kune.docs.client.actions.DocsClientActions;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsParts {

  @Inject
  public DocsParts(final Session session, final Provider<DocsClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final DocsClientActions docsActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, DocsConstants.TYPE_DOCUMENT);
    viewerSelector.register(folderViewer, true, DocsConstants.TYPE_ROOT, DocsConstants.TYPE_FOLDER);
  }
}