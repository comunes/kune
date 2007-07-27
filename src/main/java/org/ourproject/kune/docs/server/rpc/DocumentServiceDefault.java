package org.ourproject.kune.docs.server.rpc;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.dto.ContentTreeDTO;

public class DocumentServiceDefault implements DocumentService {
    private static final long serialVersionUID = 1L;

    public ContentTreeDTO getContentTree(String userHash) {
        return null;
    }
}
