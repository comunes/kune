package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.workspace.client.Workspace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.QuickTips;

public class Desktop extends AbsolutePanel {

    public Desktop(final Workspace workspace, final SiteBarListener listener) {
	SiteBar siteBar = SiteBarFactory.createSiteBar(listener);
	SiteMessage siteMessage = SiteBarFactory.getSiteMessage();
	VerticalPanel generalVP = new VerticalPanel();
	this.add(generalVP);
	this.add((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
	this.setSize("100%", "100%");
	generalVP.add((Widget) siteBar.getView());
	generalVP.add((Widget) workspace.getView());
	generalVP.addStyleName("kuneybody");
	initResizeListener(this, workspace, siteMessage);
	QuickTips.init(); // extgwt tips
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
