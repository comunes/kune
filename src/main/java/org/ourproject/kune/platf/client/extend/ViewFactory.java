package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.dto.ContentTreeDTO;

public interface ViewFactory {
    Object getContextView(ContentTreeDTO tree);
    Object getContentView(Object content);
}
