package org.ourproject.kune.platf.client.ui.rte.saving;

import org.ourproject.kune.platf.client.actions.BeforeActionListener;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface RTESavingEditor {

    void edit(String html, Listener<String> onSave, Listener0 onEditCancelled);

    RTEditor getBasicEditor();

    /**
     * This is use for listen to changes as url changes, window close, and
     * save/cancel or stop these actions
     * 
     * @return
     */
    BeforeActionListener getBeforeSavingListener();

    void onSavedSuccessful();

    void onSaveFailed();

}
