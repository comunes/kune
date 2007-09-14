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
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.DropDownPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccessListsPanel extends Composite implements View {

    private final DropDownPanel adminsPanel;
    private final DropDownPanel editorsPanel;
    private final DropDownPanel viewersPanel;
    private Label headerText;
    private final VerticalPanel adminsVP;
    private final VerticalPanel editorsVP;
    private final VerticalPanel viewersVP;

    public AccessListsPanel() {
	VerticalPanel vp = new VerticalPanel();
	initWidget(vp);

	headerText = new Label();
	// i18n
	adminsPanel = new DropDownPanel("Who can admin this", true);
	editorsPanel = new DropDownPanel("Who can edit this", true);
	viewersPanel = new DropDownPanel("Who can view this", true);
	vp.add(headerText);
	vp.add(adminsPanel);
	vp.add(editorsPanel);
	vp.add(viewersPanel);
	adminsVP = new VerticalPanel();
	editorsVP = new VerticalPanel();
	viewersVP = new VerticalPanel();
	adminsPanel.setContent(adminsVP);
	editorsPanel.setContent(editorsVP);
	viewersPanel.setContent(viewersVP);
	adminsPanel.setBorderColor(Kune.getInstance().c.getAdminsDropDown());
	editorsPanel.setBorderColor(Kune.getInstance().c.getEditorsDropDown());
	viewersPanel.setBorderColor(Kune.getInstance().c.getViewersDropDown());
	adminsPanel.setBackgroundColor("CCFFAA");
	editorsPanel.setBackgroundColor("CCFFAA");
	viewersPanel.setBackgroundColor("CCFFAA");
	adminsPanel.addStyleName("kune-Margin-Medium-tl");
	editorsPanel.addStyleName("kune-Margin-Medium-tl");
	viewersPanel.addStyleName("kune-Margin-Medium-tl");
    }

    public Label getHeaderText() {
	return headerText;
    }

    public void setHeaderText(final Label text) {
	this.headerText = text;
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
	    groupVP.add(new Label("Everybody"));
	} else if (groupList.getMode() == GroupListDTO.NOBODY) {
	    // i18n
	    groupVP.add(new Label("Nobody"));
	} else {
	    Iterator iter = groupList.getList().iterator();
	    while (iter.hasNext()) {
		GroupDTO next = (GroupDTO) iter.next();
		groupVP.add(new Label(next.getLongName()));
	    }
	}
    }

}
