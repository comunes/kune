package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class DocumentModule implements ClientModule {
    public void configure(Register register) {
        register.registerTool("documentos", DocumentService.App.getInstance(), new DocumentViewFactory());
    }

	public void registerActions(Dispatcher dispatcher) {
	}
}
