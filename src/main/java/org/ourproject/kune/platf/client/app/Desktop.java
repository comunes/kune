package org.ourproject.kune.platf.client.app;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.workspace.client.Workspace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Widget;

public class Desktop extends DefaultGDesktopPane {

    public Desktop(final Workspace workspace) {
	SiteBar siteBar = SiteBarFactory.getSiteBar();
	SiteMessage siteMessage = SiteBarFactory.getSiteMessage();
	this.addWidget((Widget) workspace.getView(), 0, 20);
	this.addWidget((Widget) siteBar.getView(), 0, 0);
	this.addWidget((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
	Gwm.setOverlayLayerDisplayOnDragAction(false);
	this.setTheme("alphacubecustom");
	initResizeListener(this, workspace, siteMessage);
    }

    private void initResizeListener(final DefaultGDesktopPane desktop, final Workspace workspace,
	    final SiteMessage siteMessage) {
	Window.addWindowResizeListener(new WindowResizeListener() {
	    public void onWindowResized(final int width, final int height) {
		workspace.adjustSize(width, height);
		siteMessage.adjustWidth(width);
		desktop.setWidgetPosition((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
	    }
	});
	Window.enableScrolling(false);

	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		GWT.log("Adjusting workspace width", null);
		int windowWidth = Window.getClientWidth();
		workspace.adjustSize(windowWidth, Window.getClientHeight());
		siteMessage.adjustWidth(windowWidth);
	    }
	});
    }
}
