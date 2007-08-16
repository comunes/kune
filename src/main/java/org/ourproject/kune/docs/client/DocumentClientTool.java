package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.tool.ToolTriggerDefault;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class DocumentClientTool implements ClientTool, DocumentContentListener {
    public static final String NAME = "docs";
    private final ToolTriggerDefault trigger;
    private final DocToolComponents components;

    public DocumentClientTool() {
	trigger = new ToolTriggerDefault(NAME, "documentos");
	components = new DocToolComponents(this);
    }

    public WorkspaceComponent getContent() {
	return components.getContent();
    }

    public WorkspaceComponent getContext() {
	return components.getContext();
    }

    public String getName() {
	return NAME;
    }

    public void setContent(final StateDTO content) {
	DocumentContent docContent = components.getContent();
	docContent.setContent(content);
	DocumentContext context = components.getContext();
	context.setContent(content);
	// TODO: revistar el interface de trigger (setState)
	trigger.setState(content.getState().toString());
    }

    public ToolTrigger getTrigger() {
	return trigger;
    }

    public void onEdit() {
	components.getContext().showAdmin();
    }

    public void onCancel() {
	components.getContext().showFolders();
    }

}
