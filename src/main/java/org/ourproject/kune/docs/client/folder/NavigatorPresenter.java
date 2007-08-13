package org.ourproject.kune.docs.client.folder;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.dto.ContextDataDTO;
import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public class NavigatorPresenter {
    private final NavigationView view;

    public NavigatorPresenter(final NavigationView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setReferences(final String ctxRef, final String cntRef) {
    }

    public void accept(final ContextDataDTO ctxData) {
	ContextItemDTO item;
	view.clear();
	ArrayList items = ctxData.getChildren();
	int size = items.size();
	for (int index = 0; index < size; index++) {
	    item = (ContextItemDTO) items.get(index);
	    view.add(item.getName(), item.getType(), item.getToken());
	}
	int defaultIndex = ctxData.getDefaultIndex();
	view.selectItem(defaultIndex);
    }

    public void failed(final Throwable caugth) {
    }

    public void setState(final String group, final String folder, final String document) {

    }

}
