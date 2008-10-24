/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.editor.TextEditor;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class DocumentContentPresenter implements DocumentContent {
    private DocumentContentView view;
    private final Session session;
    private final Provider<DocumentReader> docReaderProvider;
    private final Provider<TextEditor> textEditorProvider;
    private final Provider<FolderViewer> folderViewerProvider;
    private final ActionToolbar<StateToken> toolbar;
    private final ActionRegistry<StateToken> actionRegistry;

    public DocumentContentPresenter(final StateManager stateManager, final Session session,
            final Provider<DocumentReader> docReaderProvider, final Provider<TextEditor> textEditorProvider,
            final Provider<FolderViewer> folderViewerProvider, final ActionToolbar<StateToken> toolbar,
            final ActionRegistry<StateToken> actionRegistry) {
        this.session = session;
        this.docReaderProvider = docReaderProvider;
        this.textEditorProvider = textEditorProvider;
        this.folderViewerProvider = folderViewerProvider;
        this.toolbar = toolbar;
        this.actionRegistry = actionRegistry;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
        stateManager.onToolChanged(new Listener2<String, String>() {
            public void onEvent(final String oldTool, final String newTool) {
                if (oldTool != null && oldTool.equals(DocumentClientTool.NAME)) {
                    toolbar.detach();
                }
            }
        });
    }

    public void detach() {
        toolbar.detach();
    }

    public void init(final DocumentContentView view) {
        this.view = view;
    }

    public void refreshState() {
        setState(session.getCurrentState());
    }

    private void setState(final StateAbstractDTO state) {
        if (state instanceof StateContainerDTO) {
            StateContainerDTO stateCntCtx = (StateContainerDTO) state;
            if (stateCntCtx.getToolName().equals(DocumentClientTool.NAME)) {
                // This tool
                if (stateCntCtx instanceof StateContentDTO) {
                    setState((StateContentDTO) stateCntCtx);
                } else if (stateCntCtx instanceof StateContainerDTO) {
                    setState(stateCntCtx);
                }
            }
        }
    }

    private void setState(final StateContainerDTO state) {
        ActionItemCollection<StateToken> collection = actionRegistry.getCurrentActions(state.getStateToken(),
                state.getTypeId(), session.isLogged(), state.getContainerRights(), true);
        setToolbar(collection);
        final FolderViewer viewer = folderViewerProvider.get();
        viewer.setFolder(state.getContainer());
        view.setContent(viewer.getView());
    }

    private void setState(final StateContentDTO state) {
        ActionItemCollection<StateToken> collection = actionRegistry.getCurrentActions(state.getStateToken(),
                state.getTypeId(), session.isLogged(), state.getContentRights(), true);
        setToolbar(collection);
        docReaderProvider.get().showDocument(state.getStateToken(), state.getContent(), state.getTypeId(),
                state.getMimeType());
        textEditorProvider.get().reset();
    }

    private void setToolbar(ActionItemCollection<StateToken> collection) {
        toolbar.disableMenusAndClearButtons();
        toolbar.setActions(collection);
        toolbar.attach();
    }
}
