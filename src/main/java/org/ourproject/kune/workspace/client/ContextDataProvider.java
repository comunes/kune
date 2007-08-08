package org.ourproject.kune.workspace.client;

import org.ourproject.kune.workspace.client.dto.ContextDataDTO;

public interface ContextDataProvider {
    interface ContextDataAcceptor {
	void accept(ContextDataDTO ctxData);
	void failed(Throwable caugth);
    }

    public void getContext(String id, ContextDataAcceptor acceptor);
}
