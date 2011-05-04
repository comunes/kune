/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.sitebar.search;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SiteSearchPresenter implements SiteSearch {

    private final String defaultSearchText;
    private SiteSearchView view;

    @Inject
    public SiteSearchPresenter(final I18nTranslationService i18n) {
        defaultSearchText = i18n.t("Search");
    }

    public void doSearch(final String termToSearch) {
        // provider.get().doSearch(termToSearch);
        view.setTextSearchSmall();
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
