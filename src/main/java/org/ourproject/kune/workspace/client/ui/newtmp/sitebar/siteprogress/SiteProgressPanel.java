package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress;

import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLink;

import com.calclab.suco.client.provider.Provider;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteProgressPanel implements SiteProgressView {
    private static final int MAX_TIME_PROGRESS_BAR = 20000;

    private final Widget progressPanel;
    private final Widget progressText;
    private Timer timeProgressMaxTime;

    private final Provider<SitePublicSpaceLink> publicLinkProvider;

    public SiteProgressPanel(final SiteProgressPresenter presenter,
	    final Provider<SitePublicSpaceLink> publicLinkProvider) {
	this.publicLinkProvider = publicLinkProvider;
	progressPanel = RootPanel.get("kuneprogresspanel");
	progressText = RootPanel.get("kuneprogresstext");
    }

    public void hideProgress() {
	timeProgressMaxTime.cancel();
	progressPanel.setVisible(false);
	publicLinkProvider.get().setVisible(true);
    }

    public void showProgress(final String text) {
	if (timeProgressMaxTime == null) {
	    timeProgressMaxTime = new Timer() {
		public void run() {
		    hideProgress();
		}
	    };
	}
	timeProgressMaxTime.schedule(MAX_TIME_PROGRESS_BAR);
	publicLinkProvider.get().setVisible(false);
	progressPanel.setVisible(true);
	DOM.setInnerText(progressText.getElement(), text);
    }
}
