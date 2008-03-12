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

package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Kune;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;

public class AbstractSearcherPanel {

    protected static final int PAGINATION_SIZE = 10;

    public AbstractSearcherPanel() {
    }

    protected void query(final Store store, final GridPanel grid, final String query) {
        UrlParam[] newParams = new UrlParam[] { new UrlParam("query", query), new UrlParam("start", 0),
                new UrlParam("limit", PAGINATION_SIZE) };
        store.setBaseParams(newParams);
        store.load(0, PAGINATION_SIZE);
        // createPagingToolbar(store, grid);
    }

    protected void createPagingToolbar(final Store store, final GridPanel grid) {
        PagingToolbar pag = new PagingToolbar(store);
        pag.setPageSize(PAGINATION_SIZE);
        pag.setDisplayInfo(true);
        pag.setDisplayMsg(Kune.I18N.tWithNT("Displaying results {0} - {1} of {2}",
                "Respect {} values in translations, "
                        + "these will produce: 'Displaying results 1 - 25 of 95465' for instance"));
        pag.setEmptyMsg(Kune.I18N.t("No results to display"));
        // FIXME (remove from liquibase)
        // pag.setAfterPageText(Kune.I18N.tWithNT("of {0}", "Used to show
        // multiple results: '1 of 30'"));
        // pag.setBeforePageText(Kune.I18N.t("Page"));
        // pag.setFirstText(Kune.I18N.t("First Page"));
        // pag.setLastText(Kune.I18N.t("Last Page"));
        // pag.setNextText(Kune.I18N.t("Next Page"));
        // pag.setPrevText(Kune.I18N.t("Previous Page"));
        // pag.setRefreshText(Kune.I18N.t("Refresh"));

        grid.addListener(new PanelListenerAdapter() {
            public void onRender(final Component component) {
                store.load(0, PAGINATION_SIZE);
            }
        });
        grid.setBottomToolbar(pag);
    }

    protected Store createStore(final FieldDef[] fieldDefs, final String url, final String id) {
        JsonReader reader = new JsonReader(new RecordDef(fieldDefs));
        reader.setRoot("list");
        reader.setTotalProperty("size");
        reader.setId(id);
        HttpProxy proxy = new HttpProxy(url, Connection.POST);
        return new Store(proxy, reader, true);
    }

}