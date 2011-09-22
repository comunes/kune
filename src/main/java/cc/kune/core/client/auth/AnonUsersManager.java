package cc.kune.core.client.auth;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class AnonUsersManager {

  @Inject
  public AnonUsersManager(final Session session, final CookiesManager cookiesManager,
      final I18nTranslationService i18n) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        if (session.isNotLogged()) {
          final String anonCookie = cookiesManager.getAnonCookie();
          if (anonCookie == null) {
            // First access, set cookie to short period (1day), and show message
            cookiesManager.setAnonCookie(false);
            final String register = TextUtils.generateHtmlLink("#" + SiteTokens.REGISTER,
                i18n.tWithNT("register", "register, in lowercase"), false);
            final String signin = TextUtils.generateHtmlLink("#" + SiteTokens.SIGNIN,
                i18n.tWithNT("sign in", "register, in lowercase"), false);
            NotifyUser.info(
                i18n.tWithNT(
                    "You did not sign-in, so you can just see some public contents in this website, "
                        + "but not edit or collaborate with others. Please [%s] or [%s] in order to get full access to this site tools and contents",
                    "This will be something like 'Please register or sign in', but instead of %s some links",
                    register, signin), true);
          } else {
            if (Boolean.valueOf(anonCookie)) {
              // Registered already: we set the cookie for some big period again
              cookiesManager.setAnonCookie(true);
            } else {
              // Non registered yet: but we show the message already today
            }
          }
        }
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        cookiesManager.setAnonCookie(true);
      }
    });
  }
}
