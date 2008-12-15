package org.ourproject.kune.workspace.client.editor.insert;

import org.ourproject.kune.platf.client.app.TextEditorInsertElementGroup;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.options.AbstractOptionsPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener0;

public class TextEditorInsertElementPanel extends AbstractOptionsPanel implements TextEditorInsertElementView {
    public static final String TEXT_EDT_INSERT_DIALOG = "k-ted-iep-dialog";
    public static final String TEXT_EDT_INSERT_DIALOG_ERROR_ID = "k-ted-iep-dialog-err";
    private final TextEditorInsertElementGroup textEditorInsertElementGroup;

    public TextEditorInsertElementPanel(final TextEditorInsertElementPresenter presenter, final WorkspaceSkeleton ws,
            Images images, I18nTranslationService i18n, final TextEditorInsertElementGroup textEditorInsertElementGroup) {
        super(TEXT_EDT_INSERT_DIALOG, i18n.tWithNT("Insert an element",
                "Option in a text editor to insert links and other elements"), 380, 170, 380, 170, images,
                TEXT_EDT_INSERT_DIALOG_ERROR_ID);
        // super.setIconCls("k-options-icon");
        this.textEditorInsertElementGroup = textEditorInsertElementGroup;
        super.addHideListener(new Listener0() {
            public void onEvent() {
                textEditorInsertElementGroup.resetAll();
            }
        });
    }

    @Override
    public void createAndShow() {
        textEditorInsertElementGroup.createAll();
        super.createAndShow();
    }
}