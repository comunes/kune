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

package org.ourproject.kune.platf.client.search;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.View;

public class SearchSitePresenter implements SearchSite {

    private static final String GROUPS_SEARCH = "groups";
    private static final String USERS_SEARCH = "users";
    private SearchSiteView view;
    private String currentSearch;
    private final ArrayList searchHistory;

    public SearchSitePresenter() {
        searchHistory = new ArrayList();
        currentSearch = GROUPS_SEARCH;
    }

    public void init(final SearchSiteView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doClose() {
    }

    public void doSearchGroups() {
        currentSearch = GROUPS_SEARCH;
        doSearch(view.getComboTextToSearch());
    }

    public void doSearchUsers() {
        currentSearch = USERS_SEARCH;
        doSearch(view.getComboTextToSearch());
    }

    public void doSearch(final String text) {
        searchHistory.add(text);
        if (currentSearch == GROUPS_SEARCH) {
            view.searchGroups(text);
        }
    }

    public Object[][] getSearchHistory() {
        Object[][] objs = new Object[searchHistory.size()][1];
        int i = 0;
        for (Iterator iterator = searchHistory.iterator(); iterator.hasNext();) {
            String search = (String) iterator.next();
            Object[] obj = new Object[] { search };
            objs[i++] = obj;
        }
        return objs;

    }
}
