package cc.kune.core.client.sitebar.auth;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.HistoryTokenMustBeAuthCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.gspace.client.options.general.UserOptGeneral;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class VerifyEmailClientManager {

  @Inject
  VerifyEmailClientManager(final Session session, final SiteTokenListeners tokens,
      final Provider<UserOptGeneral> optGeneral, final I18nTranslationService i18n,
      final Provider<UserServiceAsync> userService) {
    tokens.put(SiteTokens.VERIFY_EMAIL, new HistoryTokenMustBeAuthCallback() {
      @Override
      public void onHistoryToken(final String token) {
        userService.get().verifyPasswordHash(session.getUserHash(), token,
            new AsyncCallbackSimple<Void>() {
              @Override
              public void onSuccess(final Void result) {
                NotifyUser.info("Great. Your email is now verified.");
                session.getCurrentUser().setEmailVerified(true);
                optGeneral.get().update();
              }
            });
      }
    });

  }
}
