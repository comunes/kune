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

package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public class TextEditorPresenter implements TextEditor {
    private boolean editingHtml;
    private TextEditorView view;
    private boolean savePending;
    private final boolean autoSave;
    private boolean saveAndCloseConfirmed;
    private Listener<String> onSave;
    private Listener0 onEditCancelled;
    private final ActionToolbar<StateToken> toolbar;
    private ActionToolbarButtonDescriptor<StateToken> save;
    private ActionToolbarButtonDescriptor<StateToken> close;
    private final I18nUITranslationService i18n;

    public TextEditorPresenter(final boolean isAutoSave, final ActionToolbar<StateToken> toolbar,
	    final I18nUITranslationService i18n) {
	this.toolbar = toolbar;
	autoSave = isAutoSave;
	this.i18n = i18n;
	savePending = false;
	editingHtml = false;
	saveAndCloseConfirmed = false;
	createActions();
    }

    public void editContent(final String content, final Listener<String> onSave, final Listener0 onEditCancelled) {
	this.onSave = onSave;
	this.onEditCancelled = onEditCancelled;
	toolbar.attach();
	view.attach();
	setContent(content);
    }

    public String getContent() {
	return view.getHTML();
    }

    public View getView() {
	return view;
    }

    public void init(final TextEditorView view) {
	this.view = view;
	toolbar.setEnableAction(save, false);
	this.view.setEnabled(true);
    }

    public void onEdit() {
	if (!savePending) {
	    savePending = true;
	    toolbar.setEnableAction(save, true);
	    if (autoSave) {
		view.scheduleSave(10000);
	    }
	}
    }

    public void onSaveAndClose() {
	saveAndCloseConfirmed = true;
	onSave();
    }

    public void onSaved() {
	if (saveAndCloseConfirmed) {
	    onCancelConfirmed();
	} else {
	    reset();
	}
    }

    public void onSaveFailed() {
	view.scheduleSave(20000);
	if (saveAndCloseConfirmed) {
	    saveAndCloseConfirmed = false;
	}
    }

    public void reset() {
	view.saveTimerCancel();
	savePending = false;
	saveAndCloseConfirmed = false;
	toolbar.setEnableAction(save, false);
    }

    protected void onCancel() {
	if (savePending) {
	    view.saveTimerCancel();
	    view.showSaveBeforeDialog();
	} else {
	    onCancelConfirmed();
	}
    }

    protected void onCancelConfirmed() {
	reset();
	view.detach();
	toolbar.detach();
	onEditCancelled.onEvent();
    }

    protected void onEditHTML() {
	if (editingHtml) {
	    // normal editor
	    final String html = view.getText();
	    view.setHTML(html);
	    view.editHTML(false);
	    editingHtml = false;
	} else {
	    // html editor
	    final String html = view.getHTML();
	    view.setText(html);
	    view.editHTML(true);
	    editingHtml = true;
	}
    }

    protected void onSave() {
	onSave.onEvent(view.getHTML());
    }

    private void createActions() {
	save = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Viewer, ActionToolbarPosition.topbar,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			onSave();
		    }
		});
	save.setTextDescription(i18n.tWithNT("Save", "used in button"));
	// save.setIconUrl("images/");

	close = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Viewer, ActionToolbarPosition.topbar,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			onCancel();
		    }
		});
	close.setTextDescription(i18n.tWithNT("Close", "used in button"));
	// close.setIconUrl("images/");

	final ActionItemCollection<StateToken> collection = new ActionItemCollection<StateToken>();
	collection.add(new ActionItem<StateToken>(save, null));
	collection.add(new ActionItem<StateToken>(close, null));
	toolbar.setActions(collection);
    }

    private void setContent(final String html) {
	this.view.setHTML(html);
    }
}
