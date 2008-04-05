package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.View;

public interface SearchSiteView extends View {

    public static final int GROUP_USER_SEARCH = 1;
    public static final int CONTENT_SEARCH = 2;

    void search(String text, int currentSearch);

    String getComboTextToSearch();

    void hide();

}
