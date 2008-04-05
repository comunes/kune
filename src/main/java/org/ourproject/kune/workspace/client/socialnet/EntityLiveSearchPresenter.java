package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;

public class EntityLiveSearchPresenter extends AbstractPresenter {

    private EntityLiveSearchView view;
    private EntityLiveSearchListener listener;

    public void init(final EntityLiveSearchView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void addListener(final EntityLiveSearchListener listener) {
        this.listener = listener;
    }

    public void show() {
        view.show();
    }

    public void hide() {
        view.hide();
    }

    public void fireListener(final String shortName, final String longName) {
        listener.onSelection(shortName, longName);
        hide();
    }

}
