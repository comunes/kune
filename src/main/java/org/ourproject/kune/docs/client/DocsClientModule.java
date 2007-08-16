package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.actions.AddDocument;
import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.docs.client.actions.GoParentFolder;
import org.ourproject.kune.docs.client.actions.SaveDocument;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class DocsClientModule implements ClientModule {
    public void configure(final Register register) {
	register.addTool(new DocumentClientTool());
	register.addAction(SaveDocument.KEY, new SaveDocument());
	register.addAction(AddDocument.KEY, new AddDocument());
	register.addAction(AddFolder.KEY, new AddFolder());
	register.addAction(GoParentFolder.KEY, new GoParentFolder());
    }
}
