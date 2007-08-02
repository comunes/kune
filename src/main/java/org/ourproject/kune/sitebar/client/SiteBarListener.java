package org.ourproject.kune.sitebar.client;

public interface SiteBarListener {
    void doLogin();
    void doLogout();
    void doNewGroup();
    void doSearch(String string);
}