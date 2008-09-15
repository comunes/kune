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
import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.platf.client.dto.StateDTO;
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

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Signal0;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.calclab.suco.client.signal.Slot2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContentPresenter implements DocumentContent, TextEditorListener {
    private DocumentContentView view;
    private final StateManager stateManager;
    private StateDTO content;
    private final Session session;
    private final RateIt rateIt;
    private final Provider<DocumentReaderControl> docReaderControlProvider;
    private final Provider<DocumentReader> docReaderProvider;
    private final Provider<FolderEditor> folderEditorProvider;
    private final Provider<TextEditor> textEditorProvider;
    private final Provider<FolderViewer> folderViewerProvider;
    private final Signal0 onEditing;
    private final Signal0 onEditCancelled;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final I18nUITranslationService i18n;
    private final KuneErrorHandler errorHandler;

    public DocumentContentPresenter(final StateManager stateManager, final I18nUITranslationService i18n,
	    final KuneErrorHandler errorHandler, final Session session, final RateIt rateIt,
	    final Provider<DocumentReader> docReaderProvider,
	    final Provider<DocumentReaderControl> docReaderControlProvider,
	    final Provider<TextEditor> textEditorProvider, final Provider<FolderViewer> folderViewerProvider,
	    final Provider<FolderEditor> folderEditorProvider,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	this.stateManager = stateManager;
	this.i18n = i18n;
	this.errorHandler = errorHandler;
	this.session = session;
	this.rateIt = rateIt;
	this.docReaderProvider = docReaderProvider;
	this.docReaderControlProvider = docReaderControlProvider;
	this.textEditorProvider = textEditorProvider;
	this.folderViewerProvider = folderViewerProvider;
	this.folderEditorProvider = folderEditorProvider;
	this.contentServiceProvider = contentServiceProvider;
	this.onEditing = new Signal0("onEditing");
	this.onEditCancelled = new Signal0("onEditCancelled");
	stateManager.onStateChanged(new Slot<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		if (state.getToolName().equals(DocumentClientTool.NAME)) {
		    content = state;
		    showContent();
		}
	    }
	});
	stateManager.onToolChanged(new Slot2<String, String>() {
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
	contentServiceProvider.get().delContent(session.getUserHash(),
		session.getCurrentState().getGroup().getShortName(), content.getDocumentId(),
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

    public void onEditCancelled(final Slot0 slot) {
	onEditCancelled.add(slot);
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
		    final FolderEditor editor = folderEditorProvider.get();
		    editor.setFolder(content.getContainer());
		    view.setContent(editor.getView());
		}
		onEditing.fire();
	    }
	});
    }

    public void onEditing(final Slot0 slot) {
	onEditing.add(slot);
    }

    public void onSave(final String text) {
	content.setContent(text);
	Site.showProgressSaving();
	contentServiceProvider.get().save(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
		content.getDocumentId(), content.getContent(), new AsyncCallback<Integer>() {
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

    private void showContent() {
	textEditorProvider.get().setToolbarVisible(false);
	if (content.hasDocument()) {
	    docReaderProvider.get().showDocument(content.getContent());
	    textEditorProvider.get().reset();
	    docReaderControlProvider.get().setRights(content.getContentRights());
	    docReaderControlProvider.get().show();
	    docReaderProvider.get().show();
	} else {
	    final FolderViewer viewer = folderViewerProvider.get();
	    viewer.setFolder(content.getContainer());
	    view.setContent(viewer.getView());
	}
    }

}
