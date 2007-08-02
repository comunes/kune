package org.ourproject.kune.platf.client.workspace.navigation;

import java.util.ArrayList;

import org.ourproject.kune.docs.client.DocumentContextProvider;
import org.ourproject.kune.platf.client.workspace.AbstractComponent;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider.ContextDataAcceptor;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public class NavigatorPresenter extends AbstractComponent implements ContextDataAcceptor {
    private final NavigationView view;
    private final DocumentContextProvider provider;

    public NavigatorPresenter(DocumentContextProvider provider, NavigationView view) {
	this.provider = provider;
	this.view = view;
    }

    public void setEncodedState(String encodedState) {
	super.setEncodedState(encodedState);
	provider.getContext(encodedState, this);
    }

    public Object getView() {
	return view;
    }

    public void accept(ContextDataDTO ctxData) {
	view.clear();
	ArrayList items = ctxData.getItems();
	int size = items.size();
	for (int index = 0; index < size; index++) {
	    ContextItemDTO item = (ContextItemDTO) items.get(index);
	    view.add(item.getName(), item.getType(), item.getReference());
	}
    }

    public void failed(Throwable caugth) {
    }

}
