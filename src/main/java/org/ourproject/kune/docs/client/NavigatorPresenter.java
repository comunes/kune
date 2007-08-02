package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.workspace.AbstractComponent;

public class NavigatorPresenter extends AbstractComponent {

    private final NavigationView view;

    public NavigatorPresenter(NavigationView view) {
	this.view = view;
    }

    public Object getView() {
	return view;
    }

}
