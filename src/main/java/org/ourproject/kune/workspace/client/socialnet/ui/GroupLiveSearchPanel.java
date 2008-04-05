package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchView;

public class GroupLiveSearchPanel extends EntityLiveSearchPanel {

    public GroupLiveSearchPanel(final AbstractPresenter presenter) {
        super(presenter, EntityLiveSearchView.SEARCH_GROUPS);
    }

}