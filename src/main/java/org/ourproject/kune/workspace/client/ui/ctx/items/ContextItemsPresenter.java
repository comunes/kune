package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.List;

import org.ourproject.kune.docs.client.actions.GoParentFolder;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

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
	view.setCurrentName(container.getName());
	view.clear();
	List folders = container.getChilds();
	for (int index = 0; index < folders.size(); index++) {
	    ContainerDTO child = (ContainerDTO) folders.get(index);
	    state.setFolder(child.getId().toString());
	    view.addItem(child.getName(), child.getTypeId(), state.getEncoded());
	}

	state.setFolder(container.getId().toString());
	List contents = container.getContents();
	for (int index = 0; index < contents.size(); index++) {
	    ContentDTO dto = (ContentDTO) contents.get(index);
	    state.setDocument(dto.getId().toString());
	    view.addItem(dto.getTitle(), dto.getTypeId(), state.getEncoded());
	}

	view.setParentButtonEnabled(container.getParentFolderId() != null);
	view.setControlsVisible(rights.isEditable);
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
	Dispatcher dispatcher = Dispatcher.App.instance;
	dispatcher.fire(eventName, value);
    }

    public void setParentTreeVisible(final boolean visible) {
	view.setParentTreeVisible(visible);
    }

    public void onNew(final String typeName) {
	view.showCreationField(typeName);
    }

    public void onGoUp() {
	Dispatcher.App.instance.fire(GoParentFolder.KEY, null);
    }

    public void setControlsVisible(final boolean visible) {
	view.setControlsVisible(visible);
    }

}
