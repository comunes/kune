package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.View;

public interface SearchSite {

    public View getView();

    public void doSearch(String termToSearch);

    public void doSearchOfType(String termToSearch, int type);

}
