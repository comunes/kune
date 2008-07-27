package org.ourproject.kune.platf.client.ui.rate;

import org.ourproject.kune.platf.client.dto.StateDTO;

public class RatePresenter {

    private RateView view;

    public RatePresenter() {
    }

    public void init(final RateView view) {
	this.view = view;
    }

    public void setState(final StateDTO state) {
	if (state.isRateable()) {
	    view.setVisible(true);
	    view.setRate(state.getRate());
	    view.setByUsers(state.getRateByUsers());
	} else {
	    view.setVisible(false);
	}

    }

    public void setVisible(final boolean visible) {
	view.setVisible(visible);
    }
}
