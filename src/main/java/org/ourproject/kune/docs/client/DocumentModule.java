package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.actions.SaveDocument;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class DocumentModule implements ClientModule {
    public void configure(final Register register) {
	register.addTool(new DocumentTool());
	register.addAction(SaveDocument.KEY, new SaveDocument());
    }
}
