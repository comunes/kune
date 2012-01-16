package cc.kune.core.client.state;

/**
 * The Class HistoryTokenNotLoggedCallback is used to indicate the action
 * related to this token (like #about, etc) should not be authenticated.
 */
public abstract class HistoryTokenAuthNotNeededCallback implements HistoryTokenCallback {

  /* (non-Javadoc)
   * @see cc.kune.core.client.state.HistoryTokenCallback#authMandatory()
   */
  @Override
  public boolean authMandatory() {
    return false;

  }

}
