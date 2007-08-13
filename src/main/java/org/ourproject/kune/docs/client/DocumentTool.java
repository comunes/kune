package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;
import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public class DocumentTool implements Tool {
    public static final String NAME = "docs";

    public DocumentTool() {
    }

    public String getCaption() {
	// TODO Auto-generated method stub
	return null;
    }

    public WorkspaceComponent getContent() {
	return DocumentFactory.createDocumentContent();
    }

    public WorkspaceComponent getContext() {
	return DocumentFactory.createDocumentContext();
    }

    public String getName() {
	return NAME;
    }

    public void setContent(final ContentDTO content) {
	DocumentContent docContent = DocumentFactory.createDocumentContent();
	docContent.setContent(content);
	DocumentContext context = DocumentFactory.createDocumentContext();
	context.setContent(content);
    }

}
