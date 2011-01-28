package cc.kune.core.client.logs;

import cc.kune.core.client.i18n.I18nReadyEvent;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignOutEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class EventsLogger {

    @Inject
    public EventsLogger(final EventBus eventBus) {
        eventBus.addHandler(AppStartEvent.getType(), new AppStartEvent.AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                GWT.log("App Started");
            }
        });
        eventBus.addHandler(I18nReadyEvent.getType(), new I18nReadyEvent.I18nReadyHandler() {

            @Override
            public void onI18nReady(final I18nReadyEvent event) {
                GWT.log("I18n Ready");
            }
        });
        eventBus.addHandler(UserSignOutEvent.getType(), new UserSignOutEvent.UserSignOutHandler() {

            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                GWT.log("User signed out");
            }
        });
        eventBus.addHandler(UserSignInEvent.getType(), new UserSignInEvent.UserSignInHandler() {

            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                GWT.log("User signed in");
            }
        });
    }
}
