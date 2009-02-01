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
package org.ourproject.kune.workspace.client.editor.insert.linkext;

import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.editor.insert.abstractlink.TextEditorInsertAbstractPresenter;

public class TextEditorInsertLinkExtPresenter extends TextEditorInsertAbstractPresenter implements
        TextEditorInsertLinkExt {

    interface Action {
        void onValid(String url);
    }

    private TextEditorInsertLinkExtView view;

    public TextEditorInsertLinkExtPresenter(TextEditorInsertElement editorInsertElement) {
        super(editorInsertElement);
    }

    public void init(TextEditorInsertLinkExtView view) {
        super.init(view);
        this.view = view;
    }

    public void onInsert() {
        doActionIfValid(new Action() {
            public void onValid(String url) {
                onInsert("", url);
            }
        });
    }

    public void onPreview() {
        doActionIfValid(new Action() {
            public void onValid(String url) {
                view.setPreviewUrl(url);
            }
        });
    }

    private void doActionIfValid(Action action) {
        String url = view.getUrl();
        if (url.matches(TextUtils.URL_REGEXP)) {
            action.onValid(url);
        } else {
            if (!url.startsWith("http://")) {
                url = "http://" + url;
                if (url.matches(TextUtils.URL_REGEXP)) {
                    view.setUrl(url);
                    action.onValid(url);
                } else {
                    view.isValid();
                }
            } else {
                // Seems is not valid
                view.isValid();
            }
        }
    }
}
