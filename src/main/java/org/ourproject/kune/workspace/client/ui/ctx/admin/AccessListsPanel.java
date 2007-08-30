/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.ui.ctx.admin;

import java.util.Iterator;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccessListsPanel extends Composite implements View {

    private final DisclosurePanel adminDisclosure;
    private final DisclosurePanel editDisclosure;
    private final DisclosurePanel viewDisclosure;

    public AccessListsPanel() {
	VerticalPanel vp = new VerticalPanel();
	initWidget(vp);

	adminDisclosure = createDisclosurePanel("Admins", false);
	editDisclosure = createDisclosurePanel("Edit", false);
	viewDisclosure = createDisclosurePanel("View", false);
	vp.add(adminDisclosure);
	vp.add(editDisclosure);
	vp.add(viewDisclosure);
    }

    public void setAccessList(final AccessListsDTO accessLists) {
	setAccessLists(accessLists, adminDisclosure, editDisclosure, viewDisclosure);
    }

    private DisclosurePanel createDisclosurePanel(final String headerText, final boolean isOpen) {
	return new DisclosurePanel(new DisclosurePanelImages() {
	    public AbstractImagePrototype disclosurePanelClosed() {
		return Images.App.getInstance().arrowRightBlack();
	    }

	    public AbstractImagePrototype disclosurePanelOpen() {
		return Images.App.getInstance().arrowDownBlack();
	    }
	}, headerText, isOpen);
    }

    private void setAccessLists(final AccessListsDTO accessLists, final DisclosurePanel adminDisclosure,
	    final DisclosurePanel editDisclosure, final DisclosurePanel viewDisclosure) {
	setAccessListsInDiclosure(accessLists.getAdmins(), adminDisclosure);
	setAccessListsInDiclosure(accessLists.getEditors(), editDisclosure);
	setAccessListsInDiclosure(accessLists.getViewers(), viewDisclosure);
    }

    private void setAccessListsInDiclosure(final GroupListDTO groupList, final DisclosurePanel disclosure) {
	disclosure.clear();
	if (groupList != null) {
	    Iterator iter = groupList.getList().iterator();
	    while (iter.hasNext()) {
		GroupDTO next = (GroupDTO) iter.next();
		disclosure.add(new Label(next.getLongName()));
	    }
	}
    }

}
