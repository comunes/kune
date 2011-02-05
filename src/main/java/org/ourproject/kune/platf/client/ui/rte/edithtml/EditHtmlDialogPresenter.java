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
package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class EditHtmlDialogPresenter extends AbstractTabbedDialogPresenter implements EditHtmlDialog {

    private Listener<String> updateListener;
    private Listener0 cancelListener;
    private EditHtmlAgent agent;

    public EditHtmlDialogPresenter() {
    }

    public String getHtml() {
        return agent.getHtml();
    }

    public void onCancel() {
        if (cancelListener != null) {
            cancelListener.onEvent();
        }
        hide();
    }

    public void onUpdate() {
        if (updateListener != null) {
            // FIXME
            updateListener.onEvent(agent.getHtml());
        }
        hide();
    }

    public void setAgent(EditHtmlAgent agent) {
        this.agent = agent;
    }

    public void setCancelListener(Listener0 cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setHtml(String html) {
        agent.setHtml(html);
    }

    public void setUpdateListener(Listener<String> updateListener) {
        this.updateListener = updateListener;
    }
}
