package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;

public interface RTEditor {

    void addAction(ActionDescriptor<Object> action);

    void addActions(ActionCollection<Object> actions);

    void editContent(String content);

    View getView();

    void setAccessRol(AccessRolDTO accessRol);

    void setExtended(boolean extended);

}
