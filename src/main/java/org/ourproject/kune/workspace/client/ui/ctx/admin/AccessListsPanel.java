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
import org.ourproject.kune.platf.client.ui.IconHyperlink;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccessListsPanel extends Composite implements View {

    private static final Images img = Images.App.getInstance();
    private final VerticalPanel adminsVP;
    private final VerticalPanel editorsVP;
    private final VerticalPanel viewersVP;

    public AccessListsPanel() {
	// i18n
	final DisclosurePanel disclosure = new DisclosurePanel(new DisclosurePanelImages() {
	    public AbstractImagePrototype disclosurePanelClosed() {
		return img.arrowRightWhite();
	    }

	    public AbstractImagePrototype disclosurePanelOpen() {
		return img.arrowDownWhite();
	    }
	}, "Permissions", true);

	final VerticalPanel vp = new VerticalPanel();
	// i18n
	final Label adminsLabel = new Label("Who can admin this:");
	adminsVP = new VerticalPanel();
	final Label editorsLabel = new Label("Who more can edit:");
	editorsVP = new VerticalPanel();
	final Label viewersLabel = new Label("Who more can view:");
	viewersVP = new VerticalPanel();

	// Layout
	initWidget(disclosure);
	disclosure.add(vp);
	vp.add(adminsLabel);
	vp.add(adminsVP);
	vp.add(editorsLabel);
	vp.add(editorsVP);
	vp.add(viewersLabel);
	vp.add(viewersVP);

	// Set properties
	disclosure.setTitle("Who can admin, edit or view this content");
	addStyleName("kune-AccessList");
	setWidth("100%");
	vp.setWidth("100%");
	vp.setCellWidth(adminsLabel, "100%");
	vp.setCellWidth(editorsLabel, "100%");
	vp.setCellWidth(viewersLabel, "100%");
	vp.setCellWidth(adminsVP, "100%");
	vp.setCellWidth(editorsVP, "100%");
	vp.setCellWidth(viewersVP, "100%");

	adminsLabel.addStyleName("kune-AccessListSubLabel");
	editorsLabel.addStyleName("kune-AccessListSubLabel");
	viewersLabel.addStyleName("kune-AccessListSubLabel");

    }

    public void setAccessLists(final AccessListsDTO accessLists) {
	setGroupList(accessLists.getAdmins(), adminsVP);
	setGroupList(accessLists.getEditors(), editorsVP);
	setGroupList(accessLists.getViewers(), viewersVP);
    }

    private void setGroupList(final GroupListDTO groupList, final VerticalPanel groupVP) {
	groupVP.clear();
	if (groupList.getMode() == GroupListDTO.EVERYONE) {
	    // i18n
	    groupVP.add(new IconHyperlink(img.everybody(), "Everybody", "fixme"));
	} else if (groupList.getMode() == GroupListDTO.NOBODY) {
	    // i18n
	    groupVP.add(new IconHyperlink(img.nobody(), "Nobody", "fixme"));
	} else {
	    final Iterator iter = groupList.getList().iterator();
	    while (iter.hasNext()) {
		final GroupDTO next = (GroupDTO) iter.next();
		groupVP.add(new IconHyperlink(img.groupDefIcon(), next.getLongName(), "linkToGroupDefContent"));
	    }
	}
    }
}
