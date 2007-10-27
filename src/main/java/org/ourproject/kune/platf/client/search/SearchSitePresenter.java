package org.ourproject.kune.platf.client.search;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

public class SearchSitePresenter implements SearchSite {

    private SearchSiteView view;

    public void init(final SearchSiteView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doClose() {
        // TODO Auto-generated method stub

    }

    public void doSearchGroups() {
        // TODO Auto-generated method stub

    }

    public void doSearchUsers() {
        // TODO Auto-generated method stub

    }

    public void doSearch(final String asString) {
        KuneServiceAsync kuneService = KuneService.App.getInstance();
    }
}
