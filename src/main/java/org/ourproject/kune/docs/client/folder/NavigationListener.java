package org.ourproject.kune.docs.client.folder;

import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public interface NavigationListener {
    void contextChanged(String contextRef, ContextItemDTO selectedItem);
}
