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
package org.ourproject.kune.platf.client.ui.rte.insertlink.abstractlink;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;

public class InsertLinkAbstractPresenter implements InsertLinkAbstract {

    protected InsertLinkAbstractView view;
    protected final InsertLinkDialog editorInsertDialog;
    private final Listener0 onInsertLinkPressed;

    public InsertLinkAbstractPresenter(final InsertLinkDialog editorInsertDialog) {
        this.editorInsertDialog = editorInsertDialog;
        onInsertLinkPressed = new Listener0() {
            public void onEvent() {
                if (isValid()) {
                    LinkInfo linkInfo = updateLinkInfo();
                    editorInsertDialog.fireOnInsertLink(linkInfo);
                } else {
                    Log.debug("Form in InsertLink not valid");
                }
            }
        };
    }

    public LinkInfo getLinkInfo() {
        return editorInsertDialog.getLinkInfo();
    }

    public View getView() {
        return view;
    }

    public void init(InsertLinkAbstractView view) {
        this.view = view;
        editorInsertDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        editorInsertDialog.setOnInsertLinkPressed(onInsertLinkPressed);
    }

    public void onInsert(LinkInfo linkInfo) {
        editorInsertDialog.fireOnInsertLink(linkInfo);
        reset();
    }

    public void onOverFieldChanged(String title) {
        editorInsertDialog.setLinkTitle(title);
    }

    public void onTextFieldChanged(String text) {
        editorInsertDialog.setLinkText(text);
    }

    public void reset() {
        view.reset();
    }

    protected LinkInfo updateLinkInfo() {
        return new LinkInfo(view.getText(), view.getTitle(), view.getHref(), view.inSameWindow());
    }
}
