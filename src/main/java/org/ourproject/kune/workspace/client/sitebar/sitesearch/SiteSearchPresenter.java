/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.sitebar.sitesearch;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.search.SiteSearcher;

import com.calclab.suco.client.ioc.Provider;

public class SiteSearchPresenter implements SiteSearch {

    private SiteSearchView view;
    private final Provider<SiteSearcher> provider;
    private final String defaultSearchText;

    public SiteSearchPresenter(final Provider<SiteSearcher> provider, I18nTranslationService i18n) {
        this.provider = provider;
        defaultSearchText = i18n.t("Search");
    }

    public void doSearch(final String termToSearch) {
        provider.get().doSearch(termToSearch);
        view.setTextSearchSmall();
    }

    public View getView() {
        return view;
    }

    public void init(final SiteSearchView view) {
        this.view = view;
        setDefText();
    }

    public void onSearchFocus() {
        view.setTextSearchBig();
        view.selectSearchText();
    }

    public void onSearchLostFocus(final String search) {
        if (search.length() == 0 || search.equals(defaultSearchText)) {
            view.setTextSearchSmall();
        }
        if (search.length() == 0) {
            setDefText();
        }
    }

    private void setDefText() {
        view.setTextSearch(defaultSearchText);
    }
}
