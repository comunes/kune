package org.ourproject.kune.docs.server.rpc;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;

public class DocumentServiceDefault implements DocumentService {
    private static final long serialVersionUID = 1L;

    public ContextDataDTO getContext(String userHash, String contextRef) {
	return null;
    }

    public ContentDataDTO getContent(String userHash, String ctxRef, String docRef) {
	return null;
    }

}
