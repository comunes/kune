package org.ourproject.kune.platf.client.state;

import com.google.gwt.user.client.HistoryListener;

public interface StateController extends HistoryListener {
    String getUser();
}
