package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;
import cc.kune.core.client.auth.WaveClientSimpleAuthenticator;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SitebarSignOutAction extends AbstractSignOutAction {
  /** The before sign out. */
  private final BeforeSignOut beforeSignOut;

  /** The event bus. */
  private final EventBus eventBus;

  /** The session. */
  private final Session session;

  /** The user service. */
  private final Provider<UserServiceAsync> userService;

  /** The wave auth. */
  private final WaveClientSimpleAuthenticator waveAuth;

  /**
   * Instantiates a new sitebar sign out action.
   * 
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param beforeSignOut
   *          the before sign out
   * @param userService
   *          the user service
   * @param session
   *          the session
   * @param waveAuth
   *          the wave auth
   */
  @Inject
  public SitebarSignOutAction(final EventBus eventBus, final BeforeSignOut beforeSignOut,
      final Provider<UserServiceAsync> userService, final Session session,
      final WaveClientSimpleAuthenticator waveAuth) {
    super();
    this.eventBus = eventBus;
    this.userService = userService;
    this.session = session;
    this.beforeSignOut = beforeSignOut;
    this.waveAuth = waveAuth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
   * common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    eventBus.fireEvent(new ProgressShowEvent());
    if (beforeSignOut.checkBeforeAction()) {
      waveAuth.doLogout(new AsyncCallback<Void>() {
        @Override
        public void onFailure(final Throwable caught) {
          onLogoutFail(caught);
        }

        @Override
        public void onSuccess(final Void result) {
          userService.get().logout(session.getUserHash(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
              onLogoutFail(caught);
            }

            @Override
            public void onSuccess(final Void arg0) {
              eventBus.fireEvent(new ProgressHideEvent());
              session.signOut();
            }

          });
        }
      });
    } else {
      eventBus.fireEvent(new ProgressHideEvent());
    }
  }

  /**
   * On logout fail.
   * 
   * @param caught
   *          the caught
   */
  private void onLogoutFail(final Throwable caught) {
    eventBus.fireEvent(new ProgressHideEvent());
    if (caught instanceof SessionExpiredException) {
      session.signOut();
    } else if (caught instanceof UserMustBeLoggedException) {
      session.signOut();
    } else {
      throw new UIException("Other kind of exception in doLogout", caught);
    }
  }

}
