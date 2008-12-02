package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.calclab.suco.client.listener.Listener;

public class EntityOptionsPresenter implements EntityOptions {

    private EntityOptionsView view;

    public EntityOptionsPresenter(StateManager stateManager) {
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                if (state.getGroup().isPersonalGroup()) {
                    view.setPersonalTitle();
                } else {
                    view.setGroupTitle();
                }
                if (state.getGroupRights().isAdministrable()) {
                    view.setButtonVisible(true);
                } else {
                    view.setButtonVisible(false);
                    view.hide();
                }
            }
        });
    }

    public void addOptionTab(View tab) {
        view.addOptionTab(tab);
    }

    public View getView() {
        return view;
    }

    public void hideMessages() {
        view.hideMessages();
    }

    public void init(EntityOptionsView view) {
        this.view = view;
    }

    public void setErrorMessage(String message, SiteErrorType type) {
        view.setErrorMessage(message, type);
    }

    public void show() {
        view.createAndShow();
    }
}
