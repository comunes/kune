package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPanel;

public class ContentEditorPanel extends RTESavingEditorPanel implements ContentEditorView {

    public ContentEditorPanel(final ContentEditorPresenter presenter, final I18nUITranslationService i18n,
            final GlobalShortcutRegister globalShortcutReg, final GuiBindingsRegister bindReg) {
        super(presenter, i18n, globalShortcutReg, bindReg);
    }

}
