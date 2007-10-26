package org.ourproject.kune.platf.client.search;

import org.ourproject.kune.platf.client.View;

public interface SearchSite {

    public View getView();

    public void doSearch(String termToSearch);

}
