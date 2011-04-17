package cc.kune.docs.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

public interface DocsGinjector extends Ginjector {

    DocsParts getDocsParts();

    AsyncProvider<DocsViewerPresenter> getDocsViewerPresenter();

}
