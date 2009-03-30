/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.dialogs.tabbed;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;

public abstract class AbstractTabbedDialogPresenter implements AbstractTabbedDialog {

    private AbstractTabbedDialogView view;

    public void activateTab(final int index) {
        view.activateTab(index);
    }

    public void addTab(final View tab) {
        view.addTab(tab);
    }

    public View getView() {
        return view;
    }

    public void hide() {
        view.hide();
    }

    public void hideMessages() {
        view.hideMessages();
    }

    public void init(final AbstractTabbedDialogView view) {
        this.view = view;
    }

    public void insertTab(final int index, final View tab) {
        view.insertTab(index, tab);
    }

    public void setErrorMessage(final String message, final Level level) {
        view.setErrorMessage(message, level);
    }

    public void show() {
        hideMessages();
        view.createAndShow();
    }

}
