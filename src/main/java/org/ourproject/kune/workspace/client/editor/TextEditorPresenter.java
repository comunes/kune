
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
