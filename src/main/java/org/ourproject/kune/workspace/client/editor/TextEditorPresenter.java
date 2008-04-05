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

public class TextEditorPresenter implements TextEditor {
    private boolean editingHtml;
    private TextEditorView view;
    private boolean savePending;
    private final boolean autoSave;
    private final TextEditorListener listener;
    private boolean saveAndCloseConfirmed;

    public TextEditorPresenter(final TextEditorListener listener, final boolean isAutoSave) {
        this.listener = listener;
        autoSave = isAutoSave;
        savePending = false;
        editingHtml = false;
        saveAndCloseConfirmed = false;

    }

    public void init(final TextEditorView view) {
        this.view = view;
        this.view.setEnabledSaveButton(false);
        this.view.setEnabled(true);
    }

    public void setContent(final String html) {
        this.view.setHTML(html);
    }

    public void onEdit() {
        if (!savePending) {
            savePending = true;
            view.setEnabledSaveButton(true);
            if (autoSave) {
                view.scheduleSave(10000);
            }
        }
    }

    protected void onSave() {
        listener.onSave(view.getHTML());
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
        listener.onCancel();
    }

    protected void onEditHTML() {
        if (editingHtml) {
            // normal editor
            String html = view.getText();
            view.setHTML(html);
            view.editHTML(false);
            editingHtml = false;
        } else {
            // html editor
            String html = view.getHTML();
            view.setText(html);
            view.editHTML(true);
            editingHtml = true;
        }
    }

    public void onSaved() {
        if (saveAndCloseConfirmed) {
            onCancelConfirmed();
        } else {
            reset();
        }
    }

    public void reset() {
        view.saveTimerCancel();
        savePending = false;
        saveAndCloseConfirmed = false;
        view.setEnabledSaveButton(false);
    }

    public void onSaveFailed() {
        view.scheduleSave(20000);
        if (saveAndCloseConfirmed) {
            saveAndCloseConfirmed = false;
        }
    }

    public View getView() {
        return view;
    }

    public View getToolBar() {
        return view.getToolBar();
    }

    public String getContent() {
        return view.getHTML();
    }

    public void onSaveAndClose() {
        saveAndCloseConfirmed = true;
        onSave();
    }
}
