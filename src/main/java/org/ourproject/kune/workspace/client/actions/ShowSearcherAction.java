/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
