package org.ourproject.kune.platf.client.ui.rte.insertspecialchar;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialog;

import com.calclab.suco.client.events.Listener;

public interface InsertSpecialCharDialog extends AbstractTabbedDialog {

    String DEF_LABEL = "Click on a character below to insert it into the document.";

    void hide();

    void onInsert(char character);

    void setOnInsertSpecialChar(Listener<String> listener);

    void show();
}
