package org.ourproject.kune.platf.client.ui.rte.inserttable;

import com.calclab.suco.client.events.Listener;

public interface InsertTableDialog {

    void hide();

    void setOnInsertTable(Listener<String> listener);

    void show();

}
