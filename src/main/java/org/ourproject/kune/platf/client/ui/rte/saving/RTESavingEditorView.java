package org.ourproject.kune.platf.client.ui.rte.saving;

import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorView;

import com.calclab.suco.client.events.Listener0;

public interface RTESavingEditorView extends RTEditorView {

    void askConfirmation(String confirmationTitle, String confirmationText, Listener0 onConfirm, Listener0 onCancel);
}
