package org.ourproject.kune.sitebar.client.ui;

public class NewGroupPresenter {

    private NewGroupView view;

    void init(NewGroupView view) {
        this.view = view;
        reset();
    }

    private void reset() {
        view.clearData();
    }

    void doCreate(String shortName, String longName, String publicDesc, int type) {

    }

    void doCancel() {

    }
}
