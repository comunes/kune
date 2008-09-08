package org.ourproject.kune.workspace.client.skel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class SiteBar extends Panel {

    private final TitleBar titleBar;

    public SiteBar() {
	super.setBorder(false);
	titleBar = new TitleBar();
	titleBar.setStyleName("k-sitebar");
	super.add(titleBar);
    }

    public void add(final Widget widget) {
	titleBar.add(widget);
    }

    public Widget addFill() {
	return titleBar.addFill();
    }

    public Widget addSeparator() {
	return titleBar.addSeparator();
    }

    public Widget addSpacer() {
	return titleBar.addSpacer();
    }

}
