package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;

public interface ContextDataProvider {
    interface ContextDataAcceptor {
	void accept(ContextDataDTO ctxData);
	void failed(Throwable caugth);
    }

    public void getContext(String id, ContextDataAcceptor acceptor);
}
