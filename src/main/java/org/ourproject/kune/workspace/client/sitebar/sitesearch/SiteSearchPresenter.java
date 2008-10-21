package org.ourproject.kune.workspace.client.sitebar.sitesearch;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.search.SiteSearcher;

import com.calclab.suco.client.ioc.Provider;

public class SiteSearchPresenter implements SiteSearch {

    private SiteSearchView view;
    private final Provider<SiteSearcher> provider;

    public SiteSearchPresenter(final Provider<SiteSearcher> provider) {
        this.provider = provider;
    }

    public void doSearch(final String termToSearch) {
        provider.get().doSearch(termToSearch);
    }

    public View getView() {
        return view;
    }

    public void init(final SiteSearchView view) {
        this.view = view;
    }

    public void onSearchFocus() {
        view.setTextSearchBig();
        view.clearSearchText();
    }

    public void onSearchLostFocus(final String search) {
        if (search.length() == 0) {
            view.setDefaultTextSearch();
            view.setTextSearchSmall();
        }
    }

}
