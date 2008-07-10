package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch;

import org.ourproject.kune.platf.client.View;

public class SiteSearchPresenter implements SiteSearch {

    private SiteSearchView view;

    public SiteSearchPresenter() {
    }

    public void doSearch(final String termToSearch) {
	// TODO
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
