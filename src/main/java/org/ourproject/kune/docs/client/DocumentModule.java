package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class DocumentModule implements ClientModule {
    public void configure(Register register) {
	register.register(new DocumentTool());
    }

    public void registerActions(Dispatcher dispatcher) {
    }
}
