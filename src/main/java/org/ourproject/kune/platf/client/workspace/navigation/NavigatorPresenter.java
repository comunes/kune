package org.ourproject.kune.platf.client.workspace.navigation;

import org.ourproject.kune.platf.client.workspace.AbstractComponent;

public class NavigatorPresenter extends AbstractComponent {

    private final NavigationView view;

    public NavigatorPresenter(NavigationView view) {
	this.view = view;
    }

    @Override
    public void setEncodedState(String encodedState) {
        super.setEncodedState(encodedState);
    }

    public Object getView() {
	return view;
    }

}
