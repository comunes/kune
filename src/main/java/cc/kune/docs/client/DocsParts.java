package cc.kune.docs.client;

import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsParts {

    @Inject
    public DocsParts(final Session session, final Provider<DocsViewerPresenter> docsViewer) {
        docsViewer.get();
    }
}