package cc.kune.docs.client;

import cc.kune.docs.client.viewers.DocViewerPresenter;
import cc.kune.docs.client.viewers.FolderViewerPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

public interface DocsGinjector extends Ginjector {

    DocsParts getDocsParts();

    AsyncProvider<DocViewerPresenter> getDocsViewerPresenter();

    AsyncProvider<FolderViewerPresenter> getFolderViewerPresenter();
}
