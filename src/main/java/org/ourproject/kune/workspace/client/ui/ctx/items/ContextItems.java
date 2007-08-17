package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public interface ContextItems {
    public View getView();

    public void registerType(String typeName, AbstractImagePrototype pageWhite);

    public void canCreate(String typeName, String label, String eventName);

    public void setParentTreeVisible(boolean visible);

    public void showContainer(StateToken state, ContainerDTO container, AccessRightsDTO rights);

    public void setControlsVisible(boolean visible);
}
