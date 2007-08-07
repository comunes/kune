package org.ourproject.kune.docs.client.folder;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.AbstractComponent;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider.ContextDataAcceptor;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public class NavigatorPresenter extends AbstractComponent implements ContextDataAcceptor {
    private final NavigationView view;
    private final ContextDataProvider provider;

    public NavigatorPresenter(final ContextDataProvider provider, final NavigationView view) {
	this.provider = provider;
	this.view = view;
    }

    public void setEncodedState(final String encodedState) {
	super.setEncodedState(encodedState);
	provider.getContext(HistoryToken.split(encodedState)[0], this);
    }

    public View getView() {
	return view;
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
