package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;

public class DocumentFactory {

    public static final DocumentReaderPresenter createDocumentReader(final DocumentContentDriver provider,
	    final DocumentReaderListener listener) {
	DocumentReaderView readerView = DocumentViewFactory.getDocumentReaderView(listener);
	return new DocumentReaderPresenter(provider, readerView);
    }

}
