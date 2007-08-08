package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.Timer;

public class TextEditorPresenter implements TextEditor {
    private boolean editingHtml;
    private TextEditorView view;
    private final Timer saveTimer;
    private boolean savePending;
    private final boolean autoSave;
    private final TextEditorListener listener;

    public TextEditorPresenter(final TextEditorListener listener, final boolean isAutoSave) {
	this.listener = listener;
	autoSave = isAutoSave;
	savePending = false;
	editingHtml = false;
	saveTimer = new Timer() {
	    public void run() {
		onSave();
	    }
	};
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
		saveTimer.schedule(10000);
	    }
	}
    }

    public void onSave() {
	listener.onSave();
    }

    public void onCancel() {
	listener.onCancel();
    }

    public void onEditHTML() {
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

    public void afterSaved() {
	saveTimer.cancel();
	savePending = false;
	view.setEnabledSaveButton(false);
    }

    public void afterFailedSave() {
	saveTimer.schedule(20000);
    }

    public View getView() {
	return view;
    }

    public String getContent() {
	return view.getHTML();
    }
}
