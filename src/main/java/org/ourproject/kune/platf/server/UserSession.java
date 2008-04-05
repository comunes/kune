
package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private String userHash;
    private Long userId;
    private final UserManager manager;

    @Inject
    public UserSession(final UserManager manager) {
        this.manager = manager;
    }

    public void login(final User user, final String newUserHash) {
        setUser(user);
        setHash(newUserHash);
    }

    public void logout() {
        userId = null;
        userHash = null;
    }

    public User getUser() {
        return manager.find(userId);
    }

    public String getHash() {
        return userHash;
    }

    public boolean isUserLoggedIn() {
        return userId != null;
    }

    public boolean isUserNotLoggedIn() {
        return !isUserLoggedIn();
    }

    private void setUser(final User user) {
        userId = user.getId();
    }

    private void setHash(final String hash) {
        this.userHash = hash;
    }

}
