package cc.kune.core.server;

import cc.kune.core.server.manager.UserManager;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserSessionManager {

  private final UserManager manager;
  private final Provider<UserSession> userSessionProv;

  @Inject
  public UserSessionManager(final UserManager manager, final Provider<UserSession> userSessionProv) {
    this.manager = manager;
    this.userSessionProv = userSessionProv;
  }

  public String getHash() {
    return getUserSession().getHash();
  }

  public User getUser() {
    return manager.find(getUserSession().getUserId());
  }

  private UserSession getUserSession() {
    return userSessionProv.get();
  }

  public boolean isUserLoggedIn() {
    return getUserSession().getUserId() != null;
  }

  public boolean isUserNotLoggedIn() {
    return !isUserLoggedIn();
  }

  public void login(final Long userId, final String newUserHash) {
    getUserSession().setUserId(userId);
    getUserSession().setHash(newUserHash);
  }

  public void logout() {
    getUserSession().setUserId(null);
    getUserSession().setHash(null);
  }
}
