package org.ourproject.kune.platf.client.workspace.editor;

import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.Timer;

public class TextEditorPresenter {

    private boolean editingHtml = false;

    private TextEditorView view;

    private Timer saveTimer;

    private boolean savePending = false;

    private boolean autoSave = false;

    public void init(final String html, final TextEditorView view, final boolean autoSave) {
        this.view = view;
        this.view.setEnabledSaveButton(false);
        this.view.setHTML(html);
        this.view.setEnabled(true);
        this.autoSave = autoSave;
        this.editingHtml = false;
        saveTimer = new Timer() {
            public void run() {
                onSave();
            }
        };
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
        // TODO
        Site.info("Saved hardcoded in TextEditorPresenter");
    }

    public void onCancel() {
        // TODO
        Site.info("Cancel hardcoded in TextEditorPresenter");
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
}
