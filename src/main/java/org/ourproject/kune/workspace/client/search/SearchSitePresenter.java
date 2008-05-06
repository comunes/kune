/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.search;

import java.util.HashMap;
import java.util.Iterator;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetChild;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetId;

import com.allen_sauer.gwt.log.client.Log;

public class SearchSitePresenter extends AbstractPresenter implements SearchSite {

    private SearchSiteView view;
    private int currentSearch;
    private final HashMap<String, Integer> searchHistory;

    public SearchSitePresenter() {
        searchHistory = new HashMap<String, Integer>();
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
        searchHistory.put(text, null);
        Log.debug("Search History: " + searchHistory.toString());
        view.search(text, currentSearch);
    }

    public Object[][] getSearchHistory() {
        Object[][] objs = new Object[searchHistory.size()][1];
        int i = 0;
        for (Iterator<String> iterator = searchHistory.keySet().iterator(); iterator.hasNext();) {
            String search = iterator.next();
            Object[] obj = new Object[] { search };
            objs[i++] = obj;
        }
        return objs;
    }

    public void attachIconToBottomBar(final View view) {
        DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXTENSIBLE_WIDGET,
                new ExtensibleWidgetChild(ExtensibleWidgetId.CONTENT_BOTTOM_ICONBAR, view));
    }

}
