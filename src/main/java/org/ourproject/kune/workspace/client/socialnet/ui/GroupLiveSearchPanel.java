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
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearchPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearchView;

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

public class GroupLiveSearchPanel implements GroupLiveSearchView {

    private static final int PAGINATION_SIZE = 10;
    private final Form groupSearchForm;
    private final AbstractPresenter presenter;
    private PopupPanel groupLiveSearchPopup;

    public GroupLiveSearchPanel(final AbstractPresenter initPresenter) {
        this.presenter = initPresenter;
        groupSearchForm = createGroupSearchForm();
    }

    public void reset() {
        groupSearchForm.reset();
    }

    public void show() {
        groupLiveSearchPopup = new PopupPanel(true);
        groupLiveSearchPopup.setVisible(false);
        groupLiveSearchPopup.show();
        groupLiveSearchPopup.setWidget(groupSearchForm);
        groupLiveSearchPopup.center();
        groupLiveSearchPopup.setVisible(true);
    }

    public void hide() {
        groupLiveSearchPopup.hide();
    }

    public void center() {
        groupLiveSearchPopup.center();
    }

    private Form createGroupSearchForm() {
        DataProxy dataProxy = new HttpProxy("/kune/json/GroupJSONService/search", Connection.POST);

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
                setWidth(610);
                setSurroundWithBox(true);
                setHideLabels(true);
                setHeader(Kune.I18N.t("Search existing users and groups and add as members"));
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
                setWidth(570);
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
