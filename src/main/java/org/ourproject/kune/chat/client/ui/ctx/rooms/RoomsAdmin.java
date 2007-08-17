package org.ourproject.kune.chat.client.ui.ctx.rooms;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface RoomsAdmin {
    View getView();

    public void showRoom(final StateToken token, final ContainerDTO container, final AccessRightsDTO rights);
}
