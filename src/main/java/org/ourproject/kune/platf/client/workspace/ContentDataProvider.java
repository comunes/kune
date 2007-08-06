package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;

public interface ContentDataProvider {
    interface ContentDataAcceptor {
	void accept(ContentDataDTO ctxData);

	void failed(Throwable caugth);
    }

    public void getContent(String ctxRef, String reference, ContentDataAcceptor acceptor);

}
