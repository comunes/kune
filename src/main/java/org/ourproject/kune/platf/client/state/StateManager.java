package org.ourproject.kune.platf.client.state;

import com.google.gwt.user.client.HistoryListener;

public interface StateManager extends HistoryListener {
    String getUser();
}
