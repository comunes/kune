package org.ourproject.kune.workspace.client.themes;


import cc.kune.core.shared.dto.StateToken;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface WsBackManager {

    void addBackClear(Listener0 listener);

    void addSetBackImage(Listener<StateToken> listener);

    void clearBackImage();

    void setBackImage(StateToken token);

}
