package org.ourproject.kune.docs.server.rpc;

import org.ourproject.kune.docs.client.rpc.KuneDocumentService;

public class KuneDocumentServiceDefault implements KuneDocumentService {
    private static final long serialVersionUID = 1L;

    public String test() {
        return "waw!";
    }

}
