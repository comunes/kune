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
 */
package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;

public class AbstractSearcherPanel {

    protected static final int PAGINATION_SIZE = 10;
    protected final I18nTranslationService i18n;

    public AbstractSearcherPanel(final I18nTranslationService i18n) {
        this.i18n = i18n;
    }

    protected void createPagingToolbar(final Store store, final GridPanel grid) {
        final PagingToolbar pag = new PagingToolbar(store);
        pag.setPageSize(PAGINATION_SIZE);
        pag.setDisplayInfo(true);
        pag.setDisplayMsg(i18n.tWithNT("Displaying results {0} - {1} of {2}", "Respect {} values in translations. "
                + "This will produce: 'Displaying results 1 - 25 of 95465' for instance"));
        pag.setEmptyMsg(i18n.t("No results to display"));
        pag.setAfterPageText(i18n.tWithNT("of {0}", "Used to show multiple results: '1 of 30'"));
        pag.setBeforePageText(i18n.t("Page"));
        pag.setFirstText(i18n.t("First Page"));
        pag.setLastText(i18n.t("Last Page"));
        pag.setNextText(i18n.t("Next Page"));
        pag.setPrevText(i18n.t("Previous Page"));
        pag.setRefreshText(i18n.t("Refresh"));
        grid.setBottomToolbar(pag);
        grid.setLoadMask(true);
        grid.setLoadMask(i18n.t("Searching"));
        grid.setSelectionModel(new RowSelectionModel());
        grid.setBorder(false);
        grid.setFrame(true);
        grid.setStripeRows(true);
    }

    protected Store createStore(final FieldDef[] fieldDefs, final String url, final String id) {
        final JsonReader reader = new JsonReader(new RecordDef(fieldDefs));
        reader.setRoot("list");
        reader.setTotalProperty("size");
        reader.setId(id);
        final HttpProxy proxy = new HttpProxy(url, Connection.POST);
        return new Store(proxy, reader, true);
    }

    protected void query(final Store store, final GridPanel grid, final String query) {
        final UrlParam[] newParams = new UrlParam[] { new UrlParam("query", query) };
        store.setBaseParams(newParams);
        store.load(0, PAGINATION_SIZE);
        // see bind/unbind in:
        // http://groups.google.com/group/gwt-ext/browse_thread/thread/ae0badb8114b30cd?hl=en
    }

}
