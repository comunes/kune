package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener;

public class EntityOptionsPresenter extends AbstractOptionsPresenter implements EntityOptions {

    EntityOptionsView view;

    public EntityOptionsPresenter(StateManager stateManager) {
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                if (state.getGroup().isPersonal()) {
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

    public void init(EntityOptionsView view) {
        super.init(view);
        this.view = view;
    }
}
