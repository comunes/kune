package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.ui.rte.RTEditorPresenter.ActionPosition;

public interface RTEditor {

    void addAction(ActionDescriptor<Object> action, boolean basic, ActionPosition position);

    void addActions(ActionCollection<Object> actions, boolean basic, ActionPosition position);

    void editContent(String content);

    ActionToolbar<Object> getSndBar();

    ActionToolbar<Object> getTopBar();

    View getView();

    void setAccessRol(AccessRolDTO accessRol);

    void setExtended(boolean extended);

}
