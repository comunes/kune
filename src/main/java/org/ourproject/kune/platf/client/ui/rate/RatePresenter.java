package org.ourproject.kune.platf.client.ui.rate;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;

public class RatePresenter {

    private RateView view;

    public RatePresenter(final StateManager stateManager) {
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                setState(state);
            }
        });
    }

    public void init(final RateView view) {
        this.view = view;
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }

    private void setState(final StateDTO state) {
        if (state.isRateable()) {
            view.setVisible(true);
            view.setRate(state.getRate());
            view.setByUsers(state.getRateByUsers());
        } else {
            view.setVisible(false);
        }

    }
}
