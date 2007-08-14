package org.ourproject.kune.docs.client.ui.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public interface DocumentReader {
    void showDocument(String text, AccessRightsDTO accessRights);

    View getView();
}
