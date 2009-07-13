package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface WsBackManager {

    void addBackClear(Listener0 listener);

    void addSetBackImage(Listener<StateToken> listener);

    void clearBackImage();

    void setBackImage(StateToken token);

}
