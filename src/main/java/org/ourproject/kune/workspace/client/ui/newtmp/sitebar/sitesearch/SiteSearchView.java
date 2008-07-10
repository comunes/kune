package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch;

import org.ourproject.kune.platf.client.View;

public interface SiteSearchView extends View {

    void clearSearchText();

    void setDefaultTextSearch();

    void setTextSearchBig();

    void setTextSearchSmall();
}
