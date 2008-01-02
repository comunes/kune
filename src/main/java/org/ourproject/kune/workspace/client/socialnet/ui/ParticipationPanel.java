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

package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.UIConstants;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.socialnet.ParticipationView;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ParticipationPanel extends StackedDropDownPanel implements ParticipationView {

    private static final boolean COUNTS_VISIBLE = false;
    private final Images img = Images.App.getInstance();

    public ParticipationPanel(final AbstractPresenter presenter) {
	super(presenter, "#00D4AA", Kune.I18N.t("Participates as..."), Kune.I18N.t("Other groups in which participates"), COUNTS_VISIBLE);
    }

    public void clear() {
	super.clear();
    }

    public void addCategory(final String name, final String title) {
	super.addStackItem(name, title, COUNTS_VISIBLE);
    }

    public void addCategory(final String name, final String title, final String iconType) {
	super.addStackItem(name, title, getIcon(iconType), UIConstants.ICON_HORIZ_ALIGN_RIGHT, COUNTS_VISIBLE);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
	    final MemberAction[] memberActions) {
	StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
	for (int i = 0; i < memberActions.length; i++) {
	    subItems[i] = new StackSubItemAction(getIconFronEvent(memberActions[i].getAction()), memberActions[i]
		    .getText(), memberActions[i].getAction());
	}

	super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    private AbstractImagePrototype getIcon(final String event) {
	if (event == GroupMembersView.ICON_ALERT) {
	    return img.alert();
	}
	throw new IndexOutOfBoundsException("Icon unknown in ParticipationPanelk");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
	if (event == WorkspaceEvents.UNJOIN_GROUP) {
	    return img.del();
	}
	if (event == WorkspaceEvents.GOTO) {
	    return img.groupHome();
	}
	throw new IndexOutOfBoundsException("Event unknown in ParticipationPanel");
    }

    public void show() {
	this.setVisible(true);
    }

    public void hide() {
	this.setVisible(false);
    }

}
