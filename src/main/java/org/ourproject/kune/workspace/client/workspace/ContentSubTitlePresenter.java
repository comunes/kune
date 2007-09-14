package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public class ContentSubTitlePresenter implements ContentSubTitleComponent {

    private ContentSubTitleView view;

    public void init(final ContentSubTitleView view) {
	this.view = view;
    }

    public void setContentSubTitle(final String title) {
	view.setContentSubTitle(title);
    }

    public View getView() {
	return view;
    }

}
