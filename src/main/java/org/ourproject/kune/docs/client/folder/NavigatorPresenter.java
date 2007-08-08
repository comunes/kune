package org.ourproject.kune.docs.client.folder;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.AbstractComponent;
import org.ourproject.kune.workspace.client.ContextDataProvider;
import org.ourproject.kune.workspace.client.ContextDataProvider.ContextDataAcceptor;
import org.ourproject.kune.workspace.client.dto.ContextDataDTO;
import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public class NavigatorPresenter extends AbstractComponent implements ContextDataAcceptor {
    private final NavigationView view;
    private final ContextDataProvider provider;

    public NavigatorPresenter(final ContextDataProvider provider, final NavigationView view) {
	this.provider = provider;
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setReferences(final String ctxRef, final String cntRef) {
	provider.getContext(ctxRef, this);
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

}
