package org.ourproject.kune.workspace.client;

import org.ourproject.kune.workspace.client.dto.ContentDataDTO;

public interface ContentDataDriver {
    interface ContentDataAcceptor {
	void accept(ContentDataDTO contentData);

	void failed(Throwable caught);
    }
    interface SaveListener {
	void saveCompleted();

	void failed(Throwable caught);
    }

    public void load(String ctxRef, String reference, ContentDataAcceptor acceptor);

    public void save(ContentDataDTO contentData, SaveListener listener);
}
