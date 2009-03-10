package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;

import com.calclab.suco.client.events.Listener0;

public interface RTESavingEditorView extends View {

    void askConfirmation(String confirmationTitle, String confirmationText, Listener0 onConfirm, Listener0 onCancel);
}
