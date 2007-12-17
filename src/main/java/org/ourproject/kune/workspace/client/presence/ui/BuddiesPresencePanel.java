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

package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.presence.BuddiesPresenceView;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class BuddiesPresencePanel extends StackedDropDownPanel implements BuddiesPresenceView {
    private static final int CONTACT_ONLINE = 0;
    private static final int CONTACT_OFFLINE = 1;
    private static final int CONTACT_BUSY = 2;
    private static final int CONTACT_INVISIBLE = 3;
    private static final int CONTACT_XA = 4;
    private static final int CONTACT_AWAY = 5;
    private static final int CONTACT_MESSAGE = 6;

    XmppIcons statusIcons = XmppIcons.App.getInstance();
    Images img = Images.App.getInstance();

    public BuddiesPresencePanel(final AbstractPresenter presenter) {
        super(presenter, "#CD87DE", Kune.I18N.t("My buddies"), Kune.I18N.t("Presence of my buddies"), true);
        super.addStackItem(Kune.I18N.t("Connected"), Kune.I18N.t("Buddies connected"), true);
        super.addStackItem(Kune.I18N.t("Not connected"), Kune.I18N.t("Buddies not connected"), true);
        super.addStackItem("test", Kune.I18N.t("Buddies not connected"), true);
        super.addBottomLink(img.addGreen(), Kune.I18N.t("Add new buddy"), "addbuddy", "Test");
        addRoster("fulano", Kune.I18N.t("Connected"), CONTACT_ONLINE);
        addRoster("fulano away", Kune.I18N.t("Connected"), CONTACT_AWAY);
        addRoster("fulano busy", Kune.I18N.t("Connected"), CONTACT_BUSY);
        addRoster("fulano busy2", Kune.I18N.t("Connected"), CONTACT_BUSY);
        addRoster("fulano off", Kune.I18N.t("Not connected"), CONTACT_OFFLINE);
        removeRoster("fulano busy2", Kune.I18N.t("Connected"));
        super.removeStackItem("test");
    }

    public void setBuddiesPresence() {
        // super.addStackSubItem("Connected",
        // Images.App.getInstance().personDef(), "fulano", "Waiting...",
        // MemberAction.DEFAULT_VISIT_GROUP);
        // super.addStackSubItem("Not connected",
        // Images.App.getInstance().personDef(), "fulano 2", "Away...",
        // MemberAction.DEFAULT_VISIT_GROUP);
    }

    public void addRoster(final String name, final String category, final int status) {
        StackSubItemAction[] actions = {
                new StackSubItemAction(img.chat(), Kune.I18N.t("Start a chat with this person"), WorkspaceEvents.GOTO),
                StackSubItemAction.DEFAULT_VISIT_GROUP };
        super.addStackSubItem(category, getStatusIcon(status), name, getStatusText(status), actions);
    }

    public void removeRoster(final String name, final String category) {
        super.removeStackSubItem(category, name);
    }

    private AbstractImagePrototype getStatusIcon(final int status) {
        switch (status) {
        case CONTACT_ONLINE:
            return statusIcons.online();
        case CONTACT_OFFLINE:
            return statusIcons.offline();
        case CONTACT_BUSY:
            return statusIcons.busy();
        case CONTACT_INVISIBLE:
            return statusIcons.invisible();
        case CONTACT_XA:
            return statusIcons.extendedAway();
        case CONTACT_AWAY:
            return statusIcons.away();
        case CONTACT_MESSAGE:
            return statusIcons.message();
        default:
            throw new IndexOutOfBoundsException("Xmpp status unknown");

        }
    }

    private String getStatusText(final int status) {
        switch (status) {
        case CONTACT_ONLINE:
            return "Online";
        case CONTACT_OFFLINE:
            return "Offline";
        case CONTACT_BUSY:
            return "Busy";
        case CONTACT_INVISIBLE:
            return "Invisible";
        case CONTACT_XA:
            return "Extended away";
        case CONTACT_AWAY:
            return "Away";
        case CONTACT_MESSAGE:
            return "Message from contact";
        default:
            throw new IndexOutOfBoundsException("Xmpp status unknown");

        }
    }
}
