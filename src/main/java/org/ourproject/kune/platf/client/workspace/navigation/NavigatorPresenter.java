package org.ourproject.kune.platf.client.workspace.navigation;

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

    public NavigatorPresenter(ContextDataProvider provider, NavigationView view, String initalState) {
	this.provider = provider;
	this.view = view;
	encodedState = initalState;
    }

    public void setEncodedState(String encodedState) {
	super.setEncodedState(encodedState);
	provider.getContext(HistoryToken.split(encodedState)[0], this);
    }

    public View getView() {
	return view;
    }

    public void accept(ContextDataDTO ctxData) {
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

    public void failed(Throwable caugth) {
    }

}
