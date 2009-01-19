package org.ourproject.kune.workspace.client.editor.insert.linkext;

import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.editor.insert.abstractlink.TextEditorInsertAbstractPresenter;

public class TextEditorInsertLinkExtPresenter extends TextEditorInsertAbstractPresenter implements
        TextEditorInsertLinkExt {

    interface Action {
        void onValid(String url);
    }

    private TextEditorInsertLinkExtView view;

    public TextEditorInsertLinkExtPresenter(TextEditorInsertElement editorInsertElement) {
        super(editorInsertElement);
    }

    public void init(TextEditorInsertLinkExtView view) {
        super.init(view);
        this.view = view;
    }

    public void onInsert() {
        doActionIfValid(new Action() {
            public void onValid(String url) {
                onInsert("", url);
            }
        });
    }

    public void onPreview() {
        doActionIfValid(new Action() {
            public void onValid(String url) {
                view.setPreviewUrl(url);
            }
        });
    }

    private void doActionIfValid(Action action) {
        String url = view.getUrl();
        if (url.matches(TextUtils.URL_REGEXP)) {
            action.onValid(url);
        } else {
            if (!url.startsWith("http://")) {
                url = "http://" + url;
                if (url.matches(TextUtils.URL_REGEXP)) {
                    view.setUrl(url);
                    action.onValid(url);
                } else {
                    view.isValid();
                }
            } else {
                // Seems is not valid
                view.isValid();
            }
        }
    }
}
