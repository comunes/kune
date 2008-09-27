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
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Event0;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContentPresenter implements DocumentContent, TextEditorListener {
    private DocumentContentView view;
    private final StateManager stateManager;
    private StateDTO content;
    private final Session session;
    private final RateIt rateIt;
    private final Provider<DocumentReader> docReaderProvider;
    private final Provider<TextEditor> textEditorProvider;
    private final Provider<FolderViewer> folderViewerProvider;
    private final Event0 onEditing;
    private final Event0 onEditCancelled;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final I18nUITranslationService i18n;
    private final KuneErrorHandler errorHandler;
    private final ActionToolbar toolbar;
    private final ActionRegistry<StateToken> actionRegistry;

    public DocumentContentPresenter(final StateManager stateManager, final I18nUITranslationService i18n,
	    final KuneErrorHandler errorHandler, final Session session, final RateIt rateIt,
	    final Provider<DocumentReader> docReaderProvider, final Provider<TextEditor> textEditorProvider,
	    final Provider<FolderViewer> folderViewerProvider,
	    final Provider<ContentServiceAsync> contentServiceProvider, final ActionToolbar toolbar,
	    final ActionRegistry<StateToken> actionRegistry) {
	this.stateManager = stateManager;
	this.i18n = i18n;
	this.errorHandler = errorHandler;
	this.session = session;
	this.rateIt = rateIt;
	this.docReaderProvider = docReaderProvider;
	this.textEditorProvider = textEditorProvider;
	this.folderViewerProvider = folderViewerProvider;
	this.contentServiceProvider = contentServiceProvider;
	this.toolbar = toolbar;
	this.actionRegistry = actionRegistry;
	this.onEditing = new Event0("onEditing");
	this.onEditCancelled = new Event0("onEditCancelled");
	stateManager.onStateChanged(new Listener<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		if (state.getToolName().equals(DocumentClientTool.NAME)) {
		    setState(state);
		}
	    }
	});
	stateManager.onToolChanged(new Listener2<String, String>() {
	    public void onEvent(final String oldTool, final String newTool) {
		if (oldTool != null && oldTool.equals(DocumentClientTool.NAME)) {
		    // Detach
		}
	    }
	});
    }

    public void init(final DocumentContentView view) {
	this.view = view;
    }

    public void onDeleteClicked() {
	Site.showProgressProcessing();
	contentServiceProvider.get().delContent(session.getUserHash(), content.getStateToken(),
		new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			Site.hideProgress();
			stateManager.reload();
		    }
		});
    }

    public void onEditCancelled() {
	showContent();
	onEditCancelled.fire();
	// Re-enable rateIt widget
	rateIt.setVisible(true);
	textEditorProvider.get().setToolbarVisible(false);
    }

    public void onEditCancelled(final Listener0 listener) {
	onEditCancelled.add(listener);
    }

    public void onEditClicked() {
	session.check(new AsyncCallbackSimple<Object>() {
	    public void onSuccess(final Object result) {
		if (content.hasDocument()) {
		    // Don't permit rate content while your are editing
		    rateIt.setVisible(false);
		    final TextEditor editor = textEditorProvider.get();
		    editor.setContent(content.getContent());
		    editor.setToolbarVisible(true);
		    view.setContent(editor.getView());
		} else {
		    // final FolderEditor editor = folderEditorProvider.get();
		    // editor.setFolder(content.getContainer());
		    // view.setContent(editor.getView());
		}
		onEditing.fire();
	    }
	});
    }

    public void onEditing(final Listener0 listener) {
	onEditing.add(listener);
    }

    public void onSave(final String text) {
	content.setContent(text);
	Site.showProgressSaving();
	contentServiceProvider.get().save(session.getUserHash(), content.getStateToken(), content.getContent(),
		new AsyncCallback<Integer>() {
		    public void onFailure(final Throwable caught) {
			Site.hideProgress();
			try {
			    throw caught;
			} catch (final SessionExpiredException e) {
			    errorHandler.doSessionExpired();
			} catch (final Throwable e) {
			    Site.error(i18n.t("Error saving document. Retrying..."));
			    errorHandler.process(caught);
			    onSaveFailed();
			}
		    }

		    public void onSuccess(final Integer result) {
			Site.hideProgress();
			onSaved();
		    }
		});
    }

    public void onSaved() {
	textEditorProvider.get().onSaved();
    }

    public void onSaveFailed() {
	textEditorProvider.get().onSaveFailed();
    }

    public void onTranslate() {
    }

    private void setState(final StateDTO state) {
	content = state;
	final String typeId = content.getTypeId();
	ActionItemCollection<StateToken> collection;
	if (content.hasDocument()) {
	    collection = actionRegistry.getCurrentActions(content.getStateToken(), typeId, content.getContentRights(),
		    true);
	} else {
	    collection = actionRegistry.getCurrentActions(content.getStateToken(), typeId,
		    content.getContainerRights(), true);
	}
	toolbar.disableMenusAndClearButtons();
	toolbar.showActions(collection, true);
	showContent();
    }

    private void showContent() {
	textEditorProvider.get().setToolbarVisible(false);
	if (content.hasDocument()) {
	    docReaderProvider.get().showDocument(content.getStateToken(), content.getContent(), content.getTypeId(),
		    content.getMimeType());
	    textEditorProvider.get().reset();
	} else {
	    final FolderViewer viewer = folderViewerProvider.get();
	    viewer.setFolder(content.getContainer());
	    view.setContent(viewer.getView());
	}
    }

}
