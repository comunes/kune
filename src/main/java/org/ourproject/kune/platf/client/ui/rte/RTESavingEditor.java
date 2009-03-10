package org.ourproject.kune.platf.client.ui.rte;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface RTESavingEditor {

    void edit(String html, Listener<String> onSave, Listener0 onEditCancelled);

    RTEditor getBasicEditor();

    void onSavedSuccessful();

    void onSaveFailed();

}
