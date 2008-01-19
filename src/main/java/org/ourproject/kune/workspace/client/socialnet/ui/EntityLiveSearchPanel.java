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

package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchView;
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearchPresenter;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.Template;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StoreLoadConfig;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

import com.gwtext.client.core.UrlParam;

public class EntityLiveSearchPanel implements EntityLiveSearchView {

    private static final int PAGINATION_SIZE = 10;
    private final Form searchForm;
    private final AbstractPresenter presenter;
    private PopupPanel liveSearchPopup;

    public EntityLiveSearchPanel(final AbstractPresenter initPresenter, final int searchType) {
        this.presenter = initPresenter;
        searchForm = createGroupSearchForm(searchType);
    }

    public void reset() {
        searchForm.reset();
    }

    public void show() {
        liveSearchPopup = new PopupPanel(true);

        liveSearchPopup.setVisible(false);
        liveSearchPopup.show();
        liveSearchPopup.setWidget(searchForm);
        liveSearchPopup.center();
        liveSearchPopup.setVisible(true);
        DOM.setStyleAttribute(liveSearchPopup.getElement(), "zIndex", "10000");
    }

    public void hide() {
        liveSearchPopup.hide();
    }

    public void center() {
        liveSearchPopup.center();
    }

    private Form createGroupSearchForm(final int searchType) {
        DataProxy dataProxy = null;
        switch (searchType) {
        case EntityLiveSearchView.SEARCH_GROUPS:
            dataProxy = new HttpProxy("/kune/json/GroupJSONService/search", Connection.POST);
            break;
        case EntityLiveSearchView.SEARCH_USERS:
            dataProxy = new HttpProxy("/kune/json/UserJSONService/search", Connection.POST);
            break;
        default:
            break;
        }

        JsonReader reader = new JsonReader(new JsonReaderConfig() {
            {
                setRoot("list");
                setTotalProperty("size");
                setId("shortName");
            }
        }, new RecordDef(new FieldDef[] { new StringFieldDef("shortName"), new StringFieldDef("longName"),
                new StringFieldDef("link"), new StringFieldDef("iconUrl") }));

        final Store store = new Store(dataProxy, reader);

        store.load(new StoreLoadConfig() {
            {
                setParams(new UrlParam[] { new UrlParam("query", "."), new UrlParam("first", 1),
                        new UrlParam("max", PAGINATION_SIZE) });
            }
        });

        Form form = new Form(new FormConfig() {
            {
                setWidth(410);
                setSurroundWithBox(true);
                setHideLabels(true);
                if (searchType == EntityLiveSearchView.SEARCH_GROUPS) {
                    setHeader(Kune.I18N.t("Search existing users and groups"));
                } else {
                    setHeader(Kune.I18N.t("Search existing users"));
                }
            }
        });

        final Template resultTpl = new Template(
                "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img alt=\"group logo\" src=\"images/group-def-icon.png\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>");
        ComboBox cb = new ComboBox(new ComboBoxConfig() {
            {
                setStore(store);
                setDisplayField("longName");
                setTypeAhead(true);
                setLoadingText(Kune.I18N.t("Searching..."));
                setWidth(370);
                setPageSize(PAGINATION_SIZE);
                // setHideTrigger(true);
                setTpl(resultTpl);
                setMode(ComboBox.REMOTE);
                // setTitle(Kune.I18N.t("User or group"));
                setMinChars(3);

                setComboBoxListener(new ComboBoxListenerAdapter() {
                    public void onSelect(ComboBox comboBox, Record record, int index) {
                        ((GroupLiveSearchPresenter) presenter).fireListener(record.getAsString("shortName"), record
                                .getAsString("longName"));
                    }
                });
            }
        });

        form.add(cb);
        form.render();

        return form;
    }
}
