package org.ourproject.kune.sitebar.client.login;

public interface LoginListener {
    void userLoggedIn(String nickOrEmail, String hash);

    void onLoginCancelled();
}
