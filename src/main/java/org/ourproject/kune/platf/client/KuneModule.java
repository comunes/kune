package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.workspace.client.actions.InitAction;
import org.ourproject.kune.workspace.client.actions.TabAction;

public class KuneModule implements ClientModule {
    public void configure(final Register register) {
	register.addAction(InitAction.NAME, new InitAction());
	register.addAction(TabAction.NAME, new TabAction());
    }
}
