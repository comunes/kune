package cc.kune.core.client.state;

/**
 * The Class HistoryTokenMustBeAuthCallback is used to indicate the action
 * related to this token (like #inbox, etc) should be authenticated.
 */
public abstract class HistoryTokenMustBeAuthCallback implements HistoryTokenCallback {

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.HistoryTokenCallback#authMandatory()
   */
  @Override
  public boolean authMandatory() {
    return true;
  }

}
