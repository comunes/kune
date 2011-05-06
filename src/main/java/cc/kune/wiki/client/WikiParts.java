package cc.kune.wiki.client;

import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.wiki.client.actions.WikiClientActions;
import cc.kune.wiki.shared.WikiConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WikiParts {

  @Inject
  public WikiParts(final Session session, final Provider<WikiClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final WikiClientActions wikiActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, WikiConstants.TYPE_WIKIPAGE);
    viewerSelector.register(folderViewer, true, WikiConstants.TYPE_ROOT, WikiConstants.TYPE_FOLDER);
  }
}