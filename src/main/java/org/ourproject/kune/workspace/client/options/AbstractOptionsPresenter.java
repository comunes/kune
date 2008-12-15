package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public abstract class AbstractOptionsPresenter {

    private AbstractOptionsView view;

    public void addOptionTab(View tab) {
        view.addOptionTab(tab);
    }

    public View getView() {
        return view;
    }

    public void hide() {
        view.hide();
    }

    public void hideMessages() {
        view.hideMessages();
    }

    public void init(AbstractOptionsView view) {
        this.view = view;
    }

    public void setErrorMessage(String message, SiteErrorType type) {
        view.setErrorMessage(message, type);
    }

    public void show() {
        view.createAndShow();
    }

}
