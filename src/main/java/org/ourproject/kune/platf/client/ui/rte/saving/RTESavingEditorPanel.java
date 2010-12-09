package org.ourproject.kune.platf.client.ui.rte.saving;

import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPresenter;

import cc.kune.core.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.events.Listener0;

public class RTESavingEditorPanel extends RTEditorPanel implements RTESavingEditorView {

    public RTESavingEditorPanel(final RTEditorPresenter presenter, final I18nUITranslationService i18n,
            final GlobalShortcutRegister globalShortcutReg, final GuiBindingsRegister bindReg) {
        super(presenter, i18n, globalShortcutReg, bindReg);
    }

    public void askConfirmation(final String confirmationTitle, final String confirmationText,
            final Listener0 onConfirm, final Listener0 onCancel) {
        NotifyUser.askConfirmation(confirmationTitle, confirmationText, onConfirm, onCancel);
    }

}
