package org.ourproject.kune.sitebar.client.login;

public interface LoginListener {
    void userLoggedIn(String nick, String hash);
    void onLoginCancelled();
}
