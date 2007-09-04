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

import org.ourproject.kune.platf.client.app.DesktopView;
import org.ourproject.kune.platf.client.app.ui.DesktopPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.license.LicenseComponent;
import org.ourproject.kune.workspace.client.license.LicensePanel;
import org.ourproject.kune.workspace.client.license.LicensePresenter;
import org.ourproject.kune.workspace.client.license.LicenseView;
import org.ourproject.kune.workspace.client.workspace.Workspace;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;
import org.ourproject.kune.workspace.client.workspace.ui.WorkspacePanel;

public class WorkspaceFactory {

    public static Workspace createWorkspace() {
	WorkspaceView view = new WorkspacePanel();
	WorkspacePresenter workspace = new WorkspacePresenter(view);
	return workspace;
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
	TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
	TextEditorPanel panel = new TextEditorPanel(presenter);
	presenter.init(panel);
	return presenter;
    }

    public static DesktopView createDesktop(final Workspace workspace, final SiteBarListener listener) {
	return new DesktopPanel(workspace, listener);
    }

    public static LicenseComponent createLicenseComponent() {
	LicenseView view = new LicensePanel();
	LicensePresenter presenter = new LicensePresenter(view);
	return presenter;
    }
}
