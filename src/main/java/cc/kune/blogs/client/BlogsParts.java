package cc.kune.blogs.client;

import cc.kune.blogs.client.actions.BlogsClientActions;
import cc.kune.blogs.shared.BlogsConstants;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BlogsParts {

  @Inject
  public BlogsParts(final Session session, final Provider<BlogsClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final BlogsClientActions blogsActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, BlogsConstants.TYPE_POST);
    viewerSelector.register(folderViewer, true, BlogsConstants.TYPE_ROOT, BlogsConstants.TYPE_BLOG);
  }
}