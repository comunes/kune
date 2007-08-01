package org.ourproject.kune.sitebar.client.ui;

public interface SiteBarListener {
    void doLogin();
    void doLogout();
    void doNewGroup();
    void doSearch(String string);
}