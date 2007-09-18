package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.workspace.client.presence.BuddiesPresencePresenter;
import org.ourproject.kune.workspace.client.presence.BuddiesPresenceView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BuddiesPresencePanel extends DropDownPanel implements BuddiesPresenceView {

    private final VerticalPanel buddiesPanel;

    public BuddiesPresencePanel(final BuddiesPresencePresenter presenter) {
	VerticalPanel vp = new VerticalPanel();
	setContent(vp);
	buddiesPanel = new VerticalPanel();
	IconHyperlink addBuddieLink = new IconHyperlink(Images.App.getInstance().addGreen(), "Add a buddie", "fixme");
	vp.add(buddiesPanel);
	vp.add(addBuddieLink);
	setHeaderText("My buddies");
	setContentVisible(true);
	addStyleName("kune-Margin-Medium-t");
	vp.setCellHorizontalAlignment(addBuddieLink, HorizontalPanel.ALIGN_CENTER);
	vp.setCellVerticalAlignment(addBuddieLink, VerticalPanel.ALIGN_MIDDLE);

	addBuddieLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		// TODO
	    }
	});
    }

    public void setBuddiesPresence() {
	buddiesPanel.clear();
	buddiesPanel.add(new IconLabel(Images.App.getInstance().personDef(), "luther.b"));
    }

}
