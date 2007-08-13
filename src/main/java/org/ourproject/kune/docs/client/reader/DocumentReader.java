package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public interface DocumentReader {
    void load(String contextRef, ContextItemDTO selectedItem);
}
