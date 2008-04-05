
package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.actions.GotoAction;
import org.ourproject.kune.platf.client.actions.GotoContainerAction;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

public class PlatformClientModule implements ClientModule {
    private final StateManager stateManager;
    private final Session session;

    public PlatformClientModule(final Session session, final StateManager stateManager) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void configure(final Register register) {
        register.addAction(PlatformEvents.GOTO, new GotoAction(stateManager));
        register.addAction(PlatformEvents.GOTO_CONTAINER, new GotoContainerAction(stateManager, session));
    }
}
