
package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.List;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ContextItemsPresenter implements ContextItems {
    protected ContextItemsView view;

    public ContextItemsPresenter() {
    }

    public void showContainer(final StateToken state, final ContainerDTO container, final AccessRightsDTO rights) {
        GWT.log("current folder: " + container.getId(), null);
        GWT.log("parent: " + container.getParentFolderId(), null);
        state.setDocument(null);
        if (container.getParentFolderId() == null) {
            // We translate root folder names (documents, chat room,
            // etcetera)
            view.setCurrentName(Kune.I18N.t(container.getName()));
        } else {
            view.setCurrentName(container.getName());
        }
        view.clear();
        List<ContainerDTO> folders = container.getChilds();
        for (int index = 0; index < folders.size(); index++) {
            ContainerDTO child = folders.get(index);
            state.setFolder(child.getId().toString());
            view.addItem(child.getName(), child.getTypeId(), state.getEncoded(), rights.isEditable());
        }

        state.setFolder(container.getId().toString());
        List<ContentDTO> contents = container.getContents();
        for (int index = 0; index < contents.size(); index++) {
            ContentDTO dto = contents.get(index);
            state.setDocument(dto.getId().toString());
            view.addItem(dto.getTitle(), dto.getTypeId(), state.getEncoded(), rights.isEditable());
        }
        view.setParentButtonEnabled(container.getParentFolderId() != null);
        view.setControlsVisible(rights.isEditable());
        view.setAbsolutePath(container.getAbsolutePath());
    }

    public View getView() {
        return view;
    }

    public void init(final ContextItemsView view) {
        this.view = view;
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
        view.registerType(typeName, image);
    }

    public void canCreate(final String typeName, final String label, final String eventName) {
        view.addCommand(typeName, label, eventName);
    }

    public void create(final String typeName, final String value, final String eventName) {
        if (value != null) {
            Dispatcher dispatcher = DefaultDispatcher.getInstance();
            dispatcher.fire(eventName, value);
        }
    }

    public void setParentTreeVisible(final boolean visible) {
        view.setParentTreeVisible(visible);
    }

    public void onNew(final String typeName) {
        view.showCreationField(typeName);
    }

    public void onGoUp() {
        DefaultDispatcher.getInstance().fire(DocsEvents.GO_PARENT_FOLDER, null);
    }

    public void setControlsVisible(final boolean visible) {
        view.setControlsVisible(visible);
    }

    public void onTitleRename(final String text, final String token) {
        Site.showProgressSaving();
        DefaultDispatcher.getInstance().fire(DocsEvents.RENAME_CONTENT,
                new ParamCallback<String, Object>(text, new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        DefaultDispatcher.getInstance().fire(WorkspaceEvents.RELOAD_CONTEXT, null);
                        Site.hideProgress();
                    }
                }));
    }
}
