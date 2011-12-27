package cc.kune.core.server.notifier;

public interface UsersOnline {

  /**
   * For now the implementation of this can be very inaccurate (if we
   * login/logout several times with different clients) and not scalable (stored
   * in a MAP). Possible fix, to use jabber status
   * 
   * @param shortname
   *          of the user
   * @return true if is logged
   */
  boolean isLogged(String shortname);

}
