package org.ourproject.kune.workspace.client.ui.ctx.admin;

import java.util.Iterator;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccessListPanel extends Composite implements View {

    public AccessListPanel(final AccessListsDTO accessLists) {
	VerticalPanel vp = new VerticalPanel();
	initWidget(vp);

	DisclosurePanel adminDisclosure = createDisclosurePanel("Admins", false);
	DisclosurePanel editDisclosure = createDisclosurePanel("Edit", false);
	DisclosurePanel viewDisclosure = createDisclosurePanel("View", false);
	vp.add(adminDisclosure);
	vp.add(editDisclosure);
	vp.add(viewDisclosure);
	setAccessLists(accessLists, adminDisclosure, editDisclosure, viewDisclosure);
    }

    private DisclosurePanel createDisclosurePanel(final String headerText, final boolean isOpen) {
	return new DisclosurePanel(new DisclosurePanelImages() {
	    public AbstractImagePrototype disclosurePanelClosed() {
		return Images.App.getInstance().arrowRightWhite();
	    }

	    public AbstractImagePrototype disclosurePanelOpen() {
		return Images.App.getInstance().arrowDownWhite();
	    }
	}, headerText, isOpen);
    }

    public void setAccessLists(final AccessListsDTO accessLists, final DisclosurePanel adminDisclosure,
	    final DisclosurePanel editDisclosure, final DisclosurePanel viewDisclosure) {
	setAccessListsInDiclosure(accessLists, adminDisclosure);
	setAccessListsInDiclosure(accessLists, editDisclosure);
	setAccessListsInDiclosure(accessLists, viewDisclosure);
    }

    private void setAccessListsInDiclosure(final AccessListsDTO accessList, final DisclosurePanel disclosure) {
	disclosure.clear();
	Iterator iter = accessList.getAdmin().iterator();
	while (iter.hasNext()) {
	    GroupDTO nextAdmin = (GroupDTO) iter.next();
	    disclosure.add(new Label(nextAdmin.getLongName()));
	}
    }

}
