package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.dto.StateToken;

public class ActionContextToolbarPresenter extends ActionToolbarPresenter<StateToken> implements ActionContextToolbar {

    public ActionContextToolbarPresenter(ActionToolbarView<StateToken> toolbar) {
        super(toolbar);
    }

}
