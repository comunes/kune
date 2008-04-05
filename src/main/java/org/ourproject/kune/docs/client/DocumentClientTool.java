
package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public class DocumentClientTool extends AbstractClientTool implements DocumentContentListener {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String NAME = "docs";
    private final DocToolComponents components;

    public DocumentClientTool() {
        super(Kune.I18N.t("documents"));
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

    public void setContent(final StateDTO state) {
        DocumentContent docContent = components.getContent();
        docContent.setContent(state);

        // TODO: check trigger interface (setState)
        trigger.setState(state.getStateToken().toString());
    }

    public void setContext(final StateDTO state) {
        DocumentContext context = components.getContext();
        context.setContext(state);
    }

    public void onEdit() {
        components.getContext().showAdmin();
    }

    public void onCancel() {
        components.getContext().showFolders();
    }

}
