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

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class GroupMembersPanel extends StackedDropDownPanel implements GroupMembersView {
    private static final boolean COUNTS_VISIBLE = true;
    private final Images img = Images.App.getInstance();

    public GroupMembersPanel(final AbstractPresenter presenter) {
        super(presenter, "#00D4AA", "Group members", "People and groups collaborating in this group", COUNTS_VISIBLE);
    }

    public void addJoinLink() {
        // i18n
        super.addBottomLink(img.addGreen(), "Request to join", "fixme", WorkspaceEvents.REQ_JOIN_GROUP);
    }

    public void addUnjoinLink() {
        super.addBottomLink(img.del(), "Unjoin this group", "fixme", WorkspaceEvents.UNJOIN_GROUP);
    }

    public void addAddMemberLink() {
        // FIXME: add new event
        // i18n
        super.addBottomLink(img.addGreen(), "Add member", "fixme", WorkspaceEvents.ADD_ADMIN_MEMBER);
    }

    public void clear() {
        super.clear();
    }

    public void addCategory(final String name, final String title) {
        super.addStackItem(name, title, COUNTS_VISIBLE);
    }

    public void addCategory(final String name, final String title, final String iconType) {
        super.addStackItem(name, title, getIcon(iconType), StackedDropDownPanel.ICON_HORIZ_ALIGN_RIGHT, COUNTS_VISIBLE);
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
        throw new IndexOutOfBoundsException("Icon unknown in GroupMemebersPanel");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
        if (event == WorkspaceEvents.ACCEPT_JOIN_GROUP) {
            return img.accept();
        }
        if (event == WorkspaceEvents.DENY_JOIN_GROUP) {
            return img.cancel();
        }
        if (event == WorkspaceEvents.DEL_MEMBER) {
            return img.del();
        }
        if (event == WorkspaceEvents.GOTO) {
            return img.groupHome();
        }
        if (event == WorkspaceEvents.SET_ADMIN_AS_COLLAB) {
            return img.arrowDownGreen();
        }
        if (event == WorkspaceEvents.SET_COLLAB_AS_ADMIN) {
            return img.arrowUpGreen();
        }
        throw new IndexOutOfBoundsException("Event unknown in GroupMembersPanel");
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

}
