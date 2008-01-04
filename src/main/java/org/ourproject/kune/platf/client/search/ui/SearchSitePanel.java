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

package org.ourproject.kune.platf.client.search.ui;

import org.ourproject.kune.platf.client.search.SearchSitePresenter;
import org.ourproject.kune.platf.client.search.SearchSiteView;
import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StoreLoadConfig;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.Grid;
import com.gwtext.client.widgets.grid.GridConfig;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class SearchSitePanel implements SearchSiteView {
    private static final int PAGINATION_SIZE = 10;

    private final LayoutDialog dialog;
    private final SearchSitePresenter presenter;
    private Store groupStore;

    private ComboBox searchCombo;

    public SearchSitePanel(final SearchSitePresenter initPresenter) {
        this.presenter = initPresenter;
        dialog = createDialog();
    }

    public void searchGroups(final String text) {
        dialog.setTitle(Kune.I18N.t("Searching for: [%s]", text));
        searchCombo.setValue(text);

        groupStore.load(new StoreLoadConfig() {
            {
                setParams(new UrlParam[] { new UrlParam("query", text), new UrlParam("first", 1),
                        new UrlParam("max", PAGINATION_SIZE) });
            }
        });
    }

    public void show() {
        dialog.center();
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public String getComboTextToSearch() {
        return searchCombo.getValue();
    }

    private LayoutDialog createDialog() {
        LayoutRegionConfig north = new LayoutRegionConfig() {
            {
                setSplit(false);
                setInitialSize(50);
            }
        };

        LayoutRegionConfig center = new LayoutRegionConfig() {
            {
                setAutoScroll(true);
                setTabPosition("top");
                setCloseOnTab(false);
                setAlwaysShowTabs(true);
            }
        };

        final LayoutDialog dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(false);
                setWidth(500);
                setHeight(400);
                setShadow(true);
                setResizable(true);
                setClosable(true);
                setProxyDrag(true);
                setCollapsible(false);
                setTitle(Kune.I18N.t("Search results"));
            }
        }, north, null, null, null, center);
        final BorderLayout layout = dialog.getLayout();

        layout.beginUpdate();

        ContentPanel searchPanel = createSearchForm(presenter);

        ContentPanel groupsPanel = new ContentPanel(Ext.generateId(), "Groups");
        Grid groupsGrid = createGroupsPanel();
        groupsPanel.add(groupsGrid);

        ContentPanel usersPanel = new ContentPanel(Ext.generateId(), "Users");
        usersPanel.add(new Label("In development"));
        // usersPanel = createGroupsPanel();

        layout.add(LayoutRegionConfig.NORTH, searchPanel);

        layout.add(LayoutRegionConfig.CENTER, groupsPanel);
        layout.add(LayoutRegionConfig.CENTER, usersPanel);

        layout.endUpdate();

        final Button closeBtn = new Button(Kune.I18N.tWithNT("Close", "used in button"));
        closeBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doClose();
            }

        });
        dialog.addButton(closeBtn);

        TabPanel tabPanel = layout.getRegion(LayoutRegionConfig.CENTER).getTabs();

        tabPanel.getTab(0).activate();

        tabPanel.getTab(0).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                dialog.setTitle(Kune.I18N.t("Search groups"));
                presenter.doSearchGroups();
                tab.getTextEl().highlight();
            }
        });

        tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                dialog.setTitle(Kune.I18N.t("Search users"));
                presenter.doSearchUsers();
                tab.getTextEl().highlight();
            }
        });

        return dialog;
    }

    private ContentPanel createSearchForm(final SearchSitePresenter presenter) {

        ContentPanel searchPanel = new ContentPanel(Ext.generateId(), "Search");

        HorizontalPanel hp = new HorizontalPanel();

        Form form = new Form(new FormConfig() {
            {
                setWidth(330);
                setHideLabels(true);
                // setHeader(Kune.I18N.t("Type something to search"));
            }
        });

        final Store historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());
        historyStore.load();

        searchCombo = new ComboBox(new ComboBoxConfig() {
            {
                setStore(historyStore);
                setDisplayField("term");
                setTypeAhead(false);
                setLoadingText(Kune.I18N.t("Searching..."));
                setWidth(300);
                setPageSize(10);
                setHideTrigger(true);
                setMode(ComboBox.LOCAL);
                setMinChars(2);
                // setTitle("Kune search");

                setComboBoxListener(new ComboBoxListenerAdapter() {
                    public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                        presenter.doSearch(getComboTextToSearch());
                    }

                });
            }
        });
        form.add(searchCombo);
        form.render();
        Button searchBtn = new Button(Kune.I18N.tWithNT("Search", "used in button"));
        searchBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doSearch(getComboTextToSearch());
            }
        });
        hp.add(form);
        hp.add(searchBtn);
        hp.setSpacing(7);
        hp.addStyleName("kune-Margin-Large-trbl");
        searchPanel.add(hp);
        return searchPanel;
    }

    private Grid createGroupsPanel() {
        DataProxy dataProxy = new HttpProxy("/kune/json/GroupJSONService/search", Connection.POST);

        JsonReader reader = new JsonReader(new JsonReaderConfig() {
            {
                setRoot("list");
                setTotalProperty("size");
                setId("shortName");
            }
        }, new RecordDef(new FieldDef[] { new StringFieldDef("shortName"), new StringFieldDef("longName"),
                new StringFieldDef("link"), new StringFieldDef("iconUrl") }));

        groupStore = new Store(dataProxy, reader);

        groupStore.load(new StoreLoadConfig() {
            {
                setParams(new UrlParam[] { new UrlParam("query", "."), new UrlParam("first", 1),
                        new UrlParam("max", PAGINATION_SIZE) });
            }
        });

        ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { new ColumnConfig() {
            {
                // setHeader(Kune.I18N.t("Shortname"));
                setDataIndex("shortName");
                setWidth(100);
            }
        }, new ColumnConfig() {
            {
                // setHeader(Kune.I18N.t("Longname"));
                setDataIndex("longName");
                setWidth(300);
                // setRender();
            }
        } });

        // columnModel.setDefaultSortable(true);

        Grid grid = new Grid("grid-search", "478px", "300px", groupStore, columnModel, new GridConfig() {
            {
                setAutoExpandColumn(1);
                setAutoHeight(true);
            }
        });

        grid.render();

        return grid;
    }
}
