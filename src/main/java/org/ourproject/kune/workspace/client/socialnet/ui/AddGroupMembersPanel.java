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
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.AddGroupMembersView;

import com.gwtext.client.core.Template;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.ScriptTagProxy;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class AddGroupMembersPanel implements AddGroupMembersView {

    private Form createGroupSearchForm;

    public AddGroupMembersPanel(final AbstractPresenter presenter) {
        createGroupSearchForm();
    }

    private Form createGroupSearchForm() {
        // http://localhost:8080/kune/json/GroupJSONService/search?query=site
        DataProxy dataProxy = new ScriptTagProxy("/kune/json/GroupJSONService/search?query=site");

        JsonReader reader = new JsonReader(new JsonReaderConfig() {
            {
                setRoot("groups");
                setTotalProperty("totalCount");
                setId("groupId");
            }
        }, new RecordDef(new FieldDef[] { new StringFieldDef("shortName", "shortName"),
                new StringFieldDef("longName", "longName"), new StringFieldDef("link", "link"),
                new StringFieldDef("iconUrl", "iconUrl") }));

        final Store store = new Store(dataProxy, reader);
        store.load();

        Form form = new Form(new FormConfig() {
            {
                setWidth(610);
                setSurroundWithBox(true);
                setHideLabels(true);
                setHeader("Search for existing users/groups");
            }
        });

        final Template resultTpl = new Template(
                "<div class=\"search-item\"><h3><span>{shortName}</span></h3>{longName}</div>");
        ComboBox cb = new ComboBox(new ComboBoxConfig() {
            {
                setStore(store);
                setDisplayField("longName");
                setTypeAhead(false);
                setLoadingText("Searching...");
                setWidth(570);
                setPageSize(10);
                setHideTrigger(true);
                setTpl(resultTpl);
                setMode(ComboBox.REMOTE);
                setTitle("Groups");

                setComboBoxListener(new ComboBoxListenerAdapter() {
                    public void onSelect(ComboBox comboBox, Record record, int index) {
                        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_COLLAB_MEMBER,
                                record.getAsString("shortName"), null);
                        // presenter.close ?
                    }
                });
            }
        });

        form.add(cb);
        form.render();

        return form;
    }
}
