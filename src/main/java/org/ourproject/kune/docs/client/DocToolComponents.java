package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;

class DocToolComponents {
    private DocumentContent content;
    private DocumentContext context;
    private final DocumentTool documentTool;

    public DocToolComponents(final DocumentTool documentTool) {
	this.documentTool = documentTool;
    }

    public DocumentContent getContent() {
	if (content == null) {
	    content = DocumentFactory.createDocumentContent(documentTool);
	}
	return content;
    }

    public DocumentContext getContext() {
	if (context == null) {
	    context = DocumentFactory.createDocumentContext();
	}
	return context;
    }

}
