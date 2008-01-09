package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Kune;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.PagingToolbarConfig;
import com.gwtext.client.widgets.grid.Grid;

public class AbstractSearcherPanel {

    protected static final int PAGINATION_SIZE = 10;

    public AbstractSearcherPanel() {
        super();
    }

    protected void query(final Store store, final String language) {
        UrlParam[] newParams = new UrlParam[] { new UrlParam("query", language), new UrlParam("start", 1),
                new UrlParam("limit", PAGINATION_SIZE) };
        store.setBaseParams(newParams);
        store.reload();
    }

    protected void createPagingToolbar(final Store store, final Grid grid) {
        ExtElement gridFoot = grid.getView().getFooterPanel(true);
        new PagingToolbar(gridFoot, store, new PagingToolbarConfig() {
            {
                setPageSize(PAGINATION_SIZE);
                setDisplayInfo(true);
                setDisplayMsg(Kune.I18N
                        .tWithNT("Displaying results {0} - {1} of {2}",
                                "Respect {} values in translations, these will produce: 'Displaying results 1 - 25 of 95465' for instance"));
                setDisplayMsg(Kune.I18N.t("No results to display"));
            }
        });
    }

    protected Store createStore(final FieldDef[] fieldDefs, final String url, final String id) {
        JsonReader reader = new JsonReader(new JsonReaderConfig() {
            {
                setRoot("list");
                setTotalProperty("size");
                setId(id);
            }
        }, new RecordDef(fieldDefs));
        HttpProxy proxy = new HttpProxy(url, Connection.POST);
        return new Store(proxy, reader);
    }

}