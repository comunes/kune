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
import org.ourproject.kune.platf.client.ui.rte.insertlink.TextEditorInsertElement;

public class TextEditorInsertAbstractPresenter implements TextEditorInsertAbstract {

    private TextEditorInsertAbstractView view;
    private final TextEditorInsertElement editorInsertElement;

    public TextEditorInsertAbstractPresenter(TextEditorInsertElement editorInsertElement) {
        this.editorInsertElement = editorInsertElement;
    }

    public View getView() {
        return view;
    }

    public void init(TextEditorInsertAbstractView view) {
        this.view = view;
        editorInsertElement.addOptionTab(view);
    }

    public void onInsert(String name, String link) {
        editorInsertElement.fireOnCreateLink(name, link);
        reset();
    }

    public void reset() {
        view.reset();
    }
}
