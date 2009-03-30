package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialog;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface InsertMediaDialog extends AbstractTabbedDialog {

    void fireOnInsertMedia(String html);

    void setOnCreate(Listener<String> listener);

    void setOnInsertPressed(Listener0 onInsertPressed);

}
