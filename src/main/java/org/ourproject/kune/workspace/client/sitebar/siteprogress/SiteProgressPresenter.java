package org.ourproject.kune.workspace.client.sitebar.siteprogress;

import org.ourproject.kune.platf.client.View;

public class SiteProgressPresenter implements SiteProgress {

    private SiteProgressView view;

    public SiteProgressPresenter() {
    }

    public View getView() {
	return view;
    }

    public void hideProgress() {
	view.hideProgress();
    }

    public void init(final SiteProgressView view) {
	this.view = view;
    }

    public void showProgress(final String text) {
	view.showProgress(text);
    }

}
