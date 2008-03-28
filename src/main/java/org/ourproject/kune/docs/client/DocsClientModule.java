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

package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.actions.AddDocumentAction;
import org.ourproject.kune.docs.client.actions.AddFolderAction;
import org.ourproject.kune.docs.client.actions.ContentAddAuthorAction;
import org.ourproject.kune.docs.client.actions.ContentDelContentAction;
import org.ourproject.kune.docs.client.actions.ContentRemoveAuthorAction;
import org.ourproject.kune.docs.client.actions.ContentRenameAction;
import org.ourproject.kune.docs.client.actions.ContentSetLanguageAction;
import org.ourproject.kune.docs.client.actions.ContentSetPublishedOnAction;
import org.ourproject.kune.docs.client.actions.ContentSetTagsAction;
import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.docs.client.actions.GoParentFolderAction;
import org.ourproject.kune.docs.client.actions.RenameTokenAction;
import org.ourproject.kune.docs.client.actions.SaveDocumentAction;
import org.ourproject.kune.docs.client.actions.WSSplitterStartResizingAction;
import org.ourproject.kune.docs.client.actions.WSSplitterStopResizingAction;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class DocsClientModule implements ClientModule {

    private final Session session;
    private final Workspace workspace;
    private final StateManager stateManager;

    public DocsClientModule(final Session session, final StateManager stateManager, final Workspace workspace) {
        this.session = session;
        this.stateManager = stateManager;
        this.workspace = workspace;
    }

    public void configure(final Register register) {
        register.addTool(new DocumentClientTool());
        register.addAction(DocsEvents.SAVE_DOCUMENT, new SaveDocumentAction(session));
        register.addAction(DocsEvents.ADD_DOCUMENT, new AddDocumentAction(stateManager, session));
        register.addAction(DocsEvents.ADD_FOLDER, new AddFolderAction(stateManager, session));
        register.addAction(DocsEvents.GO_PARENT_FOLDER, new GoParentFolderAction(stateManager, session));
        register.addAction(DocsEvents.ADD_AUTHOR, new ContentAddAuthorAction(stateManager, session));
        register.addAction(DocsEvents.REMOVE_AUTHOR, new ContentRemoveAuthorAction(stateManager, session));
        register.addAction(DocsEvents.SET_LANGUAGE, new ContentSetLanguageAction(session, workspace));
        register.addAction(DocsEvents.SET_PUBLISHED_ON, new ContentSetPublishedOnAction(session, workspace));
        register.addAction(DocsEvents.SET_TAGS, new ContentSetTagsAction(session, workspace));
        register.addAction(DocsEvents.RENAME_CONTENT, new ContentRenameAction(session));
        register.addAction(DocsEvents.DEL_CONTENT, new ContentDelContentAction(stateManager, session));
        register.addAction(DocsEvents.RENAME_TOKEN, new RenameTokenAction(session, stateManager));
        register.addAction(WorkspaceEvents.WS_SPLITTER_STARTRESIZING, new WSSplitterStartResizingAction());
        register.addAction(WorkspaceEvents.WS_SPLITTER_STOPRESIZING, new WSSplitterStopResizingAction());
    }
}
