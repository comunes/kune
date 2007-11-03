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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

public class SearchSitePresenter implements SearchSite {

    private SearchSiteView view;

    public void init(final SearchSiteView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doClose() {
        // TODO Auto-generated method stub

    }

    public void doSearchGroups() {
        // TODO Auto-generated method stub

    }

    public void doSearchUsers() {
        // TODO Auto-generated method stub

    }

    public void doSearch(final String asString) {
        KuneServiceAsync kuneService = KuneService.App.getInstance();
    }
}
