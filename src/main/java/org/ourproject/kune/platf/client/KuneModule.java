package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.workspace.client.actions.InitAction;

public class KuneModule implements ClientModule {
    public void configure(final Register register) {
	register.addAction(InitAction.NAME, new InitAction());
    }
}
