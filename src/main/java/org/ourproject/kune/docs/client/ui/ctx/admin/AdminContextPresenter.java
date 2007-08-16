package org.ourproject.kune.docs.client.ui.ctx.admin;

import org.ourproject.kune.platf.client.View;

public class AdminContextPresenter implements AdminContext {

    private final AdminContextView view;

    public AdminContextPresenter(final AdminContextView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

}
