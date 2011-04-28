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
package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.OldAbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertMediaDialogPresenter extends OldAbstractTabbedDialogPresenter implements InsertMediaDialog {

    private Listener0 onInsertPressed;
    Listener<String> onCreateListener;

    public void fireOnInsertMedia(String html) {
        onCreateListener.onEvent(html);
        super.hide();
    }

    public void setOnCreate(Listener<String> listener) {
        onCreateListener = listener;
    }

    public void setOnInsertPressed(Listener0 onInsertPressed) {
        this.onInsertPressed = onInsertPressed;
    }

    protected void onCancel() {
        super.hide();
    }

    protected void onInsert() {
        onInsertPressed.onEvent();
    }
}
