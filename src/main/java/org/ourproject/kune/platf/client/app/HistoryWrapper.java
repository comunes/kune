package org.ourproject.kune.platf.client.app;

public interface HistoryWrapper {

    public void newItem(final String historyToken);

    public String getToken();

}