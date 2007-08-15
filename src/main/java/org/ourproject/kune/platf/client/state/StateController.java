package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.HistoryListener;

public interface StateController extends HistoryListener {
    String getUser();

    void reload();

    void setState(ContentDTO content);

    void setState(StateToken state);
}
