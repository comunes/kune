package cc.kune.core.client.state;

import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SiteTokenListeners {
    private final Provider<Register> register;
    private final Provider<SignIn> signIn;
    private final Provider<StateManager> stateManager;

    @Inject
    public SiteTokenListeners(final EventBus eventBus, final Provider<StateManager> stateManager,
            final Provider<SignIn> signIn, final Provider<Register> register) {
        this.stateManager = stateManager;
        this.signIn = signIn;
        this.register = register;
        init();
        eventBus.addHandler(AppStartEvent.getType(), new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
            }
        });

    }

    private void init() {
        stateManager.get().addSiteToken(SiteCommonTokens.SIGNIN, new HistoryTokenCallback() {
            @Override
            public void onHistoryToken() {
                signIn.get().doSignIn();
            }
        });
        stateManager.get().addSiteToken(SiteCommonTokens.REGISTER, new HistoryTokenCallback() {
            @Override
            public void onHistoryToken() {
                register.get().doRegister();
            }
        });
    }
}
