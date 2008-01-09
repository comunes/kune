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

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.WorkspaceUIExtensionPoint;

public class SearchSitePresenter extends AbstractPresenter implements SearchSite {

    private SearchSiteView view;
    private int currentSearch;
    private final ArrayList searchHistory;

    public SearchSitePresenter() {
        searchHistory = new ArrayList();
        currentSearch = SearchSiteView.GROUP_USER_SEARCH;
    }

    public void init(final SearchSiteView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doClose() {
        view.hide();
    }

    public void doSearch(final int typeOfSearch) {
        doSearchOfType(view.getComboTextToSearch(), typeOfSearch);
    }

    public void doSearchOfType(final String text, final int typeOfSearch) {
        this.currentSearch = typeOfSearch;
        doSearch(text);
    }

    public void doSearch(final String text) {
        searchHistory.add(text);
        view.search(text, currentSearch);
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

    public void attachIconToBottomBar(final View view) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ATTACH_TO_EXT_POINT,
                WorkspaceUIExtensionPoint.CONTENT_BOTTOM_ICONBAR, view);
    }

}
