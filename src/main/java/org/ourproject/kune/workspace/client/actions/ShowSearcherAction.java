package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ShowSearcherActionParams;
import org.ourproject.kune.workspace.client.search.SearchSite;
import org.ourproject.kune.workspace.client.search.ui.SearchSitePanel;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.sitebar.SiteBarFactory;

public class ShowSearcherAction implements Action<ShowSearcherActionParams> {

    public void execute(final ShowSearcherActionParams params) {
        onShowSearcherAction(params.getTermToSearch(), params.getTypeOfSearch());
    }

    private void onShowSearcherAction(final String termToSearch, final Integer typeOfSearch) {
        Site.showProgressLoading();
        final SearchSite search = SiteBarFactory.getSearch();
        if (termToSearch != null) {
            if (typeOfSearch != null) {
                search.doSearchOfType(termToSearch, typeOfSearch.intValue());
            } else {
                search.doSearch(termToSearch);
            }
        }
        SearchSitePanel searchPanel = (SearchSitePanel) search.getView();
        searchPanel.show();
        Site.hideProgress();
    }
}
