package cc.kune.core.shared;

public final class SessionConstants {

  public final static int _AN_HOUR = 1000 * 60 * 60;
  public final static long A_DAY = _AN_HOUR * 24;
  public final static long ANON_SESSION_DURATION = A_DAY;
  public final static long ANON_SESSION_DURATION_AFTER_REG = A_DAY * 365;
  public final static long SESSION_DURATION = A_DAY * 30; // four weeks login
                                                          // session duration
  // public final static long SESSION_DURATION = 100; // For test
  public final static String USERHASH = "k007userHash";

  public SessionConstants() {

  }
}