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
package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;

public class InsertMediaAbstractPresenter implements InsertMediaAbstract {

    protected InsertMediaAbstractView view;
    protected final InsertMediaDialog insertMediaDialog;
    private final Listener0 onInsertMediaPressed;

    public InsertMediaAbstractPresenter(final InsertMediaDialog insertMediaDialog) {
        this.insertMediaDialog = insertMediaDialog;
        onInsertMediaPressed = new Listener0() {
            public void onEvent() {
                if (isValid()) {
                    String embedInfo = updateMediaInfo();
                    insertMediaDialog.fireOnInsertMedia(embedInfo);
                } else {
                    Log.debug("Form in InsertMedia not valid");
                }
            }
        };
    }

    public View getView() {
        return view;
    }

    public void init(final InsertMediaAbstractView view) {
        this.view = view;
        insertMediaDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        insertMediaDialog.setOnInsertPressed(onInsertMediaPressed);
    }

    public void reset() {
        view.reset();
    }

    protected String updateMediaInfo() {
        return "";
    }
}
