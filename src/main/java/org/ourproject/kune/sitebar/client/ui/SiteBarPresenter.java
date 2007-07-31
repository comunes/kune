package org.ourproject.kune.sitebar.client.ui;

import com.google.gwt.user.client.Window;

public class SiteBarPresenter implements SiteBarListener {

    private SiteBarView view;

    public SiteBarPresenter() {
    }

    public void doLogin() {
        view.showLoginDialog();
    }

    public void doNewGroup() {
    }

    public void doSearch(String string) {
        if (string == null) Window.alert("Type something to search!");
        else Window.alert("In development!");
    }

    public void init(SiteBarView view) {
        this.view = view;
    }

}
