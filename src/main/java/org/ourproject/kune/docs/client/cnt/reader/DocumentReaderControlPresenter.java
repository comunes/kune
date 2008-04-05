
package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public class DocumentReaderControlPresenter implements DocumentReaderControl {
    private final DocumentReaderControlView view;

    public DocumentReaderControlPresenter(final DocumentReaderControlView view) {
        this.view = view;
    }

    public void setRights(final AccessRightsDTO accessRights) {
        view.setEditEnabled(accessRights.isEditable());
        view.setDeleteEnabled(accessRights.isAdministrable());
        view.setTranslateEnabled(accessRights.isEditable());
    }

    public View getView() {
        return view;
    }
}
