package org.ourproject.kune.sitebar.client.ui;

public interface LoginListener {
    void userLoggedIn(String nick, String hash);
    void onLoginCancelled();
}
