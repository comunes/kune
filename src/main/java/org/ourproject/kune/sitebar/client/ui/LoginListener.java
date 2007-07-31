package org.ourproject.kune.sitebar.client.ui;

public interface LoginListener {
    void doLogin(String nick, String pass);
    void doCancel();
    void onDataChanged(String nick, String pass);
}
