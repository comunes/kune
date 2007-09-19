package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public class ContentSubTitlePresenter implements ContentSubTitleComponent {

    private ContentSubTitleView view;

    public void init(final ContentSubTitleView view) {
	this.view = view;
    }

    public void setContentSubTitle(final String title, final Double rate, final Integer rateByUsers) {
	view.setContentSubTitle(title, rate, rateByUsers);
    }

    public View getView() {
	return view;
    }

}
