package cc.kune.core.client.state;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.SessionExpiredEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent.UserMustBeLoggedHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class SessionExpirationManager {
  @Inject
  public SessionExpirationManager(final EventBus eventBus, final Session session,
      final I18nTranslationService i18n) {
    eventBus.addHandler(UserMustBeLoggedEvent.getType(), new UserMustBeLoggedHandler() {
      @Override
      public void onUserMustBeLogged(final UserMustBeLoggedEvent event) {
        if (session.isLogged()) {
          SessionExpiredEvent.fire(eventBus);
        } else {
          NotifyUser.important(i18n.t("Please sign in or register to collaborate"));
        }
      }
    });
    eventBus.addHandler(SessionExpiredEvent.getType(), new SessionExpiredEvent.SessionExpiredHandler() {
      @Override
      public void onSessionExpired(final SessionExpiredEvent event) {
        if (session.isLogged()) {
          session.signOut();
        }
        NotifyUser.info(i18n.t("Please sign in again"));
      }
    });
  }

}
