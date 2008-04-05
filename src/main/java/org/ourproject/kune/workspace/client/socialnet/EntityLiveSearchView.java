package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;

public interface EntityLiveSearchView extends View {

    final public static int SEARCH_GROUPS = 1;
    final public static int SEARCH_USERS = 2;

    void show();

    void hide();

    void center();

}
