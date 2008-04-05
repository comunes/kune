package org.ourproject.kune.blogs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public class BlogReaderControlPresenter implements BlogReaderControl {
    private final BlogReaderControlView view;

    public BlogReaderControlPresenter(final BlogReaderControlView view) {
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
