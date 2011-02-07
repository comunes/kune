/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.rte.insertlink;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertLinkDialogPresenter extends AbstractTabbedDialogPresenter implements InsertLinkDialog {

    private Listener<LinkInfo> onCreateListener;
    private LinkInfo linkInfo;
    private Listener0 onInsertLinkPressed;

    public InsertLinkDialogPresenter() {
        linkInfo = new LinkInfo("");
    }

    public void fireOnInsertLink(final LinkInfo linkInfo) {
        onCreateListener.onEvent(linkInfo);
        super.hide();
    }

    public LinkInfo getLinkInfo() {
        return linkInfo;
    }

    public void onCancel() {
        super.hide();
    }

    public void setLinkInfo(final LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    public void setOnCreateLink(final Listener<LinkInfo> listener) {
        this.onCreateListener = listener;
    }

    public void setOnInsertLinkPressed(final Listener0 onInsertLinkPressed) {
        this.onInsertLinkPressed = onInsertLinkPressed;
    }

    protected void onInsert() {
        onInsertLinkPressed.onEvent();
    }

}