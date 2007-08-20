package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.workspace.client.Workspace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.QuickTips;

public class Desktop extends AbsolutePanel {

    public Desktop(final Workspace workspace, final SiteBarListener listener) {
	QuickTips.init(); // extgwt tips
	SiteBar siteBar = SiteBarFactory.createSiteBar(listener);
	SiteMessage siteMessage = SiteBarFactory.getSiteMessage();
	this.add((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
	this.setSize("100%", "100%");
	this.add((Widget) siteBar.getView(), 0, 0);
	this.add((Widget) workspace.getView(), 0, 20);
	this.addStyleName("kuneybody");
	initResizeListener(this, workspace, siteMessage);
    }

    private void initResizeListener(final AbsolutePanel desktop, final Workspace workspace,
	    final SiteMessage siteMessage) {
	Window.addWindowResizeListener(new WindowResizeListener() {
	    public void onWindowResized(final int width, final int height) {
		workspace.adjustSize(width, height);
		siteMessage.adjustWidth(width);
		desktop.setWidgetPosition((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
	    }
	});
	Window.enableScrolling(false);
    }
}
