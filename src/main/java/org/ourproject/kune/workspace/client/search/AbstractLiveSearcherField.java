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
package org.ourproject.kune.workspace.client.search;


import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.Template;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class AbstractLiveSearcherField extends ComboBox {
    private static final String LONG_NAME_FIELD = "longName";
    private static final String SHORT_NAME_FIELD = "shortName";
    private static final String ICON_URL_FIELD = "iconUrl";
    private static final String LINK_FIELD = "link";
    public static final int PAGINATION_SIZE = 10;
    private final Store store;

    public AbstractLiveSearcherField(final I18nTranslationService i18n, final String templateText,
            final String dataProxyUrl, final Listener<LinkDTO> listener) {
        final DataProxy dataProxy = new HttpProxy(dataProxyUrl, Connection.POST);

        final JsonReader reader = new JsonReader(new RecordDef(
                new FieldDef[] { new StringFieldDef(SHORT_NAME_FIELD), new StringFieldDef(LONG_NAME_FIELD),
                        new StringFieldDef(LINK_FIELD), new StringFieldDef(ICON_URL_FIELD) }));
        reader.setRoot("list");
        reader.setTotalProperty("size");
        reader.setId(LINK_FIELD);

        store = new Store(dataProxy, reader);

        store.load(new UrlParam[] { new UrlParam(SearcherConstants.QUERY_PARAM, "."),
                new UrlParam(SearcherConstants.START_PARAM, 0),
                new UrlParam(SearcherConstants.LIMIT_PARAM, PAGINATION_SIZE) });

        final Template resultTpl = new Template(templateText);
        super.setStore(store);
        super.setEmptyText(i18n.t("Write here to search"));
        super.setDisplayField(LONG_NAME_FIELD);
        super.setTypeAhead(false);
        super.setLoadingText(i18n.t("Searching..."));
        super.setWidth(260);
        super.setPageSize(PAGINATION_SIZE);
        super.setTpl(resultTpl);
        super.setMode(ComboBox.REMOTE);
        super.setMinChars(2);
        super.setResizable(true);
        super.setSelectOnFocus(false);
        super.setHideTrigger(true);
        super.setHideLabel(true);
        super.setItemSelector("div.search-item");
        super.setValidationEvent(false);

        super.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    public void execute() {
                        final LinkDTO link = new LinkDTO(record.getAsString(SHORT_NAME_FIELD),
                                record.getAsString(LONG_NAME_FIELD), record.getAsString(ICON_URL_FIELD),
                                record.getAsString(LINK_FIELD));
                        listener.onEvent(link);
                    }
                });
            }
        });

    }

    public void setStoreBaseParams(final UrlParam[] baseParams) {
        store.setBaseParams(baseParams);
    }
}
