package org.ourproject.kune.platf.client.ui.rte.insertspecialchar;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;

public class InsertSpecialCharDialogPresenter extends AbstractTabbedDialogPresenter implements InsertSpecialCharDialog {

    private Listener<String> onInsertSpecialChar;

    public void onInsert(char character) {
        hide();
        if (onInsertSpecialChar != null) {
            onInsertSpecialChar.onEvent("" + character);
        }
    }

    public void setOnInsertSpecialChar(Listener<String> listener) {
        this.onInsertSpecialChar = listener;
    }
}
