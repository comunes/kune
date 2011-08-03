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
 */
package org.ourproject.kune.platf.client.ui;

import java.util.Map;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.HttpProxy;
import com.extjs.gxt.ui.client.data.JsonLoadResultReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreFilter;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar.PagingToolBarMessages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;

public class AbstractSearcherPanel {

  protected static final int PAGINATION_SIZE = 10;
  protected final I18nTranslationService i18n;

  public AbstractSearcherPanel(final I18nTranslationService i18n) {
    this.i18n = i18n;
  }

  protected void createPagingToolbar(final Store store, final Grid grid) {
    final PagingToolBar pag = new PagingToolBar(PAGINATION_SIZE);
    final PagingToolBarMessages msgs = new PagingToolBarMessages();

    msgs.setDisplayMsg(i18n.tWithNT("Displaying results {0} - {1} of {2}",
        "Respect {} values in translations. "
            + "This will produce: 'Displaying results 1 - 25 of 95465' for instance"));
    msgs.setEmptyMsg(i18n.t("No results to display"));
    msgs.setFirstText(i18n.t("First Page"));
    msgs.setAfterPageText(i18n.tWithNT("of {0}", "Used to show multiple results: '1 of 30'"));
    msgs.setBeforePageText(i18n.t("Page"));
    msgs.setLastText(i18n.t("Last Page"));
    msgs.setNextText(i18n.t("Next Page"));
    msgs.setPrevText(i18n.t("Previous Page"));
    msgs.setRefreshText(i18n.t("Refresh"));
    pag.setMessages(msgs);
    // pag.setStore(store);
    // pag.setDisplayInfo(true);
    // grid.setBottomToolbar(pag);
    grid.setLoadMask(true);
    // grid.setLoadMask(i18n.t("Searching"));
    grid.setSelectionModel(new GridSelectionModel());
    grid.setBorders(false);
    // grid.setFrame(true);
    grid.setStripeRows(true);
  }

  protected Store createStore(final ModelType type, final String url, final String id, final Grid grid) {
    final String path = GWT.getHostPageBaseURL() + url;

    // use a http proxy to get the data
    final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
    final HttpProxy<String> proxy = new HttpProxy<String>(builder);

    // need a loader, proxy, and reader
    final JsonLoadResultReader<ListLoadResult<ModelData>> reader = new JsonLoadResultReader<ListLoadResult<ModelData>>(
        type);

    final BaseListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(
        proxy, reader);
    loader.setRemoteSort(true);

    final ListStore<ModelData> store = new ListStore<ModelData>(loader);
    grid.addListener(Events.Attach, new Listener<GridEvent<ModelData>>() {
      @Override
      public void handleEvent(final GridEvent<ModelData> be) {
        final PagingLoadConfig config = new BasePagingLoadConfig();
        config.setOffset(0);
        config.setLimit(PAGINATION_SIZE);

        final Map<String, Object> state = grid.getState();
        if (state.containsKey("offset")) {
          final int offset = (Integer) state.get("offset");
          final int limit = (Integer) state.get("limit");
          config.setOffset(offset);
          config.setLimit(limit);
        }
        // if (state.containsKey("sortField")) {
        // config.setSortField((String) state.get("sortField"));
        // config.setSortDir(SortDir.valueOf((String) state.get("sortDir")));
        // }
        loader.load(config);
      }
    });

    // this goes to Model...
    // reader.setRoot("list");
    // reader.setTotalProperty("size");
    // reader.setId(id);
    // final HttpProxy proxy = new HttpProxy(url, Connection.POST);
    // return new Store(proxy, reader, true);

    return store;
  }

  protected void query(final Store store, final Grid grid, final String query) {
    // final UrlParam[] newParams = new UrlParam[] { new UrlParam("query",
    // query) };
    // store.setBaseParams(newParams);
    // store.load(0, PAGINATION_SIZE);
    store.clearFilters();
    store.addFilter(new StoreFilter<ModelData>() {
      @Override
      public boolean select(final Store<ModelData> store, final ModelData parent, final ModelData item,
          final String property) {
        // TODO Auto-generated method stub
        return false;
      }
    });
  }

}
