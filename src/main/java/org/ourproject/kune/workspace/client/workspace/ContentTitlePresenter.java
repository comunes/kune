package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public class ContentTitlePresenter implements ContentTitleComponent {

    private ContentTitleView view;

    public void init(final ContentTitleView view) {
	this.view = view;
    }

    public void setContentTitle(final String title) {
	view.setContentTitle(title);
    }

    public View getView() {
	return view;
    }

}
