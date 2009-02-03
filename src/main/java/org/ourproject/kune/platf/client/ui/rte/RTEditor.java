package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface RTEditor {

    void addAction(ActionDescriptor<StateToken> action);

    void addActions(ActionCollection<StateToken> actions);

    void editContent(String content, Listener<String> onSave, Listener0 onEditCancelled);

    View getView();

    void onSavedSuccessful();

    void onSaveFailed();

    void setAccessRol(AccessRolDTO accessRol);

    void setAutosave(boolean autosave);

    void setExtended(boolean extended);

}
