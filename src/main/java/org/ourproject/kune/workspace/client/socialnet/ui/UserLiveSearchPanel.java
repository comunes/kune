package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchView;

public class UserLiveSearchPanel extends EntityLiveSearchPanel {

    public UserLiveSearchPanel(final AbstractPresenter presenter) {
        super(presenter, EntityLiveSearchView.SEARCH_USERS);
    }

}
