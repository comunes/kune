package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener0;

public class RTESavingEditorPanel implements RTESavingEditorView {

    public void askConfirmation(String confirmationTitle, String confirmationText, Listener0 onConfirm,
            Listener0 onCancel) {
        NotifyUser.askConfirmation(confirmationTitle, confirmationText, onConfirm, onCancel);
    }

}
