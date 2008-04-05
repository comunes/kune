
package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ui.DocumentFactory;

class DocToolComponents {
    private DocumentContent content;
    private DocumentContext context;
    private final DocumentClientTool documentClientTool;

    public DocToolComponents(final DocumentClientTool documentClientTool) {
        this.documentClientTool = documentClientTool;
    }

    public DocumentContent getContent() {
        if (content == null) {
            content = DocumentFactory.createDocumentContent(documentClientTool);
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
