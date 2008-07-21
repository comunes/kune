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
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class SiteSearcherPresenter extends AbstractPresenter implements SiteSearcher {

    private SiteSearcherView view;
    private SiteSearcherType currentSearch;
    private final HashMap<String, Integer> searchHistory;

    public SiteSearcherPresenter() {
	searchHistory = new HashMap<String, Integer>();
	currentSearch = SiteSearcherType.group_user;
    }

    public void doClose() {
	view.hide();
    }

    public void doSearch(final SiteSearcherType typeOfSearch) {
	doSearchOfType(view.getTextToSearch(), typeOfSearch);
    }

    public void doSearch(final String text) {
	Site.showProgressLoading();
	searchHistory.put(text, null);
	view.search(text, currentSearch);
	view.show();
	Site.hideProgress();
    }

    public void doSearchOfType(final String text, final SiteSearcherType typeOfSearch) {
	this.currentSearch = typeOfSearch;
	doSearch(text);
    }

    public Object[][] getSearchHistory() {
	final Object[][] objs = new Object[searchHistory.size()][1];
	int i = 0;
	for (final Iterator<String> iterator = searchHistory.keySet().iterator(); iterator.hasNext();) {
	    final String search = iterator.next();
	    final Object[] obj = new Object[] { search };
	    objs[i++] = obj;
	}
	return objs;
    }

    public View getView() {
	return view;
    }

    public void init(final SiteSearcherView view) {
	this.view = view;
    }

}
