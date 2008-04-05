
package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

public class DocumentContextPresenter implements DocumentContext {
    private final WorkspaceDeckView view;
    private final DocumentContextComponents components;

    public DocumentContextPresenter(final WorkspaceDeckView view) {
        this.view = view;
        this.components = new DocumentContextComponents(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
        return view;
    }

    public void setContext(final StateDTO content) {
        StateToken state = content.getStateToken();
        FolderContext folderContext = components.getFolderContext();
        folderContext.setContainer(state, content.getFolder(), content.getFolderRights());
        AdminContext adminContext = components.getAdminContext();
        adminContext.setState(content);
        view.show(folderContext.getView());
    }

    public void showAdmin() {
        AdminContext adminContext = components.getAdminContext();
        view.show(adminContext.getView());
    }

    public void showFolders() {
        FolderContext folderContext = components.getFolderContext();
        view.show(folderContext.getView());
    }
}
