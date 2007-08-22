/*
 *
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

package org.ourproject.kune.workspace.client;

import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspacePanel;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;

public class WorkspaceFactory {
    private static Workspace workspace;

    public static Workspace getWorkspace() {
	if (workspace == null) {
	    WorkspaceView view = new WorkspacePanel();
	    workspace = new WorkspacePresenter(view);
	}
	return workspace;
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
        TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
        TextEditorPanel panel = new TextEditorPanel(presenter);
        presenter.init(panel);
        return presenter;
    }
}
