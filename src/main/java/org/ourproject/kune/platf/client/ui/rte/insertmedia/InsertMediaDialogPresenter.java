package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertMediaDialogPresenter extends AbstractTabbedDialogPresenter implements InsertMediaDialog {

    private Listener0 onInsertPressed;
    Listener<String> onCreateListener;

    public void fireOnInsertMedia(String html) {
        onCreateListener.onEvent(html);
        super.hide();
    }

    public void setOnCreate(Listener<String> listener) {
        onCreateListener = listener;
    }

    public void setOnInsertPressed(Listener0 onInsertPressed) {
        this.onInsertPressed = onInsertPressed;
    }

    protected void onCancel() {
        super.hide();
    }

    protected void onInsert() {
        onInsertPressed.onEvent();
    }
}
