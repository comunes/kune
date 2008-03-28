package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.docs.client.cnt.DocumentContent;

public class SaveDocumentActionParams {
    private final StateDTO stateDTO;
    private final DocumentContent documentContent;

    public SaveDocumentActionParams(final StateDTO stateDTO, final DocumentContent documentContent) {
        this.stateDTO = stateDTO;
        this.documentContent = documentContent;
    }

    public StateDTO getStateDTO() {
        return stateDTO;
    }

    public DocumentContent getDocumentContent() {
        return documentContent;
    }

}
