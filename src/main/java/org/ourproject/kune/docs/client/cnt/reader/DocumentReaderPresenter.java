
package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;

public class DocumentReaderPresenter implements DocumentReader {
    private final DocumentReaderView view;

    public DocumentReaderPresenter(final DocumentReaderView view) {
        this.view = view;
    }

    public void showDocument(final String text) {
        view.setContent(text);
    }

    public View getView() {
        return view;
    }
}
