package org.ourproject.kune.sitebar.client.bar;

public interface SiteBarListener {
    void doLogin();
    void doLogout();
    void doNewGroup();
    void doSearch(String string);
}