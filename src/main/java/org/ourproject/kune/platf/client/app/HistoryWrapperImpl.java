package org.ourproject.kune.platf.client.app;

import com.google.gwt.user.client.History;

public class HistoryWrapperImpl implements HistoryWrapper {

    public HistoryWrapperImpl() {
    }

    public void newItem(final String historyToken) {
        History.newItem(historyToken);
    }

    public String getToken() {
        return History.getToken();
    }

}
