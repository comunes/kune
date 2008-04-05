/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.search.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.AbstractSearcherPanel;
import org.ourproject.kune.platf.client.ui.BottomTrayIcon;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.workspace.client.search.SearchSitePresenter;
import org.ourproject.kune.workspace.client.search.SearchSiteView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class SearchSitePanel extends AbstractSearcherPanel implements SearchSiteView {

    private final Window dialog;
    private final SearchSitePresenter presenter;
    private Store groupStore;

    private ComboBox searchCombo;

    private Store historyStore;

    private Store contentStore;

    private BottomTrayIcon bottomIcon;

    private GridPanel groupsGrid;

    private GridPanel contentGrid;

    public SearchSitePanel(final SearchSitePresenter initPresenter) {
        this.presenter = initPresenter;
        dialog = createDialog();
    }

    public void search(final String text, final int type) {
        searchCombo.setValue(text);
        switch (type) {
        case SearchSiteView.GROUP_USER_SEARCH:
            query(groupStore, groupsGrid, text);
            break;
        case SearchSiteView.CONTENT_SEARCH:
            query(contentStore, contentGrid, text);
            break;
        default:
            break;
        }
    }

    public void show() {
        dialog.show();
        dialog.expand();
        dialog.center();
        if (bottomIcon == null) {
            bottomIcon = new BottomTrayIcon(Kune.I18N.t("Show/hide searcher"));
            bottomIcon.addMainButton(Images.App.getInstance().kuneSearchIcoPush(), new Command() {
                public void execute() {
                    if (dialog.isVisible()) {
                        dialog.hide();
                    } else {
                        dialog.show();
                    }
                }
            });
            presenter.attachIconToBottomBar(bottomIcon);
        }
    }

    public void hide() {
        dialog.hide();
    }

    public String getComboTextToSearch() {
        return searchCombo.getValue();
    }

    private Window createDialog() {
        Panel north = new Panel();
        north.setHeight(50);
        north.setBorder(false);

        TabPanel centerPanel = new TabPanel();
        centerPanel.setActiveTab(0);
        centerPanel.setAutoScroll(true);
        centerPanel.setClosable(false);
        centerPanel.setBorder(false);

        final BasicDialog dialog = new BasicDialog(Kune.I18N.t("Search"), false, false, 500, 400);
        // dialog.setResizable(false);
        dialog.setIconCls("search-icon");
        Button closeButton = new Button(Kune.I18N.tWithNT("Close", "used in button"));
        closeButton.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doClose();
            }
        });
        dialog.addButton(closeButton);

        Panel searchPanel = createSearchForm(presenter);
        final Panel groupsPanel = new Panel("Groups & Users");
        final Panel contentPanel = new Panel("Content");
        groupsPanel.setLayout(new FitLayout());
        contentPanel.setLayout(new FitLayout());

        groupsGrid = createSearchPanel(GROUP_USER_SEARCH);
        contentGrid = createSearchPanel(CONTENT_SEARCH);
        groupsPanel.add(groupsGrid);

        contentPanel.add(contentGrid);
        dialog.add(searchPanel, new BorderLayoutData(RegionPosition.NORTH));
        dialog.add(centerPanel, new BorderLayoutData(RegionPosition.CENTER));
        centerPanel.add(groupsPanel);
        centerPanel.add(contentPanel);

        centerPanel.activate(0);

        centerPanel.addListener(new TabPanelListenerAdapter() {
            public void onActivate(final Panel panel) {
                if (panel.getId().equals(groupsPanel.getId())) {
                    dialog.setTitle(Kune.I18N.t("Search users & groups"));
                    presenter.doSearch(GROUP_USER_SEARCH);
                } else if (panel.getId().equals(contentPanel.getId())) {
                    dialog.setTitle(Kune.I18N.t("Search contents"));
                    presenter.doSearch(CONTENT_SEARCH);
                    // tab.getTextEl().highlight();
                }
            }
        });

        String panelId = groupsPanel.getId();
        centerPanel.setActiveItemID(panelId);

        dialog.setCloseAction(Window.HIDE);

        dialog.addListener(new WindowListenerAdapter() {
            public void onCollapse(final Panel panel) {
                // dialog.hide();
            }
        });

        return dialog;
    }

    private Panel createSearchForm(final SearchSitePresenter presenter) {
        Panel searchPanel = new Panel();
        searchPanel.setBorder(false);

        HorizontalPanel hp = new HorizontalPanel();

        FormPanel form = new FormPanel();
        form.setBorder(false);
        form.setWidth(330);
        form.setHideLabels(true);

        historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());

        searchCombo = new ComboBox();
        searchCombo.setStore(historyStore);
        searchCombo.setDisplayField("term");
        searchCombo.setTypeAhead(false);
        searchCombo.setLoadingText(Kune.I18N.t("Searching..."));
        searchCombo.setWidth(300);
        searchCombo.setPageSize(10);
        searchCombo.setMode(ComboBox.LOCAL);
        searchCombo.setMinChars(1);
        searchCombo.setValueField("term");
        searchCombo.setForceSelection(false);
        historyStore.load();
        searchCombo.addListener(new ComboBoxListenerAdapter() {
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                presenter.doSearch(getComboTextToSearch());
                historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());
                searchCombo.setStore(historyStore);
                historyStore.load();
            }
        });
        searchCombo.addListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                // Maybe we use...
            }

            public void onSpecialKey(final Field field, final EventObject e) {
                switch (e.getKey()) {
                case KeyboardListener.KEY_ENTER:
                    Log.debug("Enter pressed");
                    Log.debug("field: " + field.getValueAsString());
                    Log.debug("field2: " + getComboTextToSearch());
                    presenter.doSearch(field.getValueAsString());
                    historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());
                    historyStore.load();
                    searchCombo.setStore(historyStore);
                    break;
                }
                e.stopEvent();
            }

        });
        form.add(searchCombo);

        Button searchBtn = new Button(Kune.I18N.tWithNT("Search", "used in button"));
        searchBtn.addListener(new ButtonListenerAdapter() {
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

    private GridPanel createSearchPanel(final int type) {
        final String id = "shortName";
        FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef(id), new StringFieldDef("longName"),
                new StringFieldDef("link"), new StringFieldDef("iconUrl") };
        final Store store;

        switch (type) {
        case GROUP_USER_SEARCH:
            store = groupStore = createStore(fieldDefs, "/kune/json/GroupJSONService/search", id);
            break;
        case CONTENT_SEARCH:
            store = contentStore = createStore(fieldDefs, "/kune/json/ContentJSONService/search", id);
            break;
        default:
            throw new RuntimeException("Unknown type of search");
        }

        ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { new ColumnConfig() {
            {
                // setHeader(Kune.I18N.t("Shortname"));
                setDataIndex(id);
                setWidth(100);
                // setTooltip(Kune.I18N.t("Click to go to the group homepage"));
            }
        }, new ColumnConfig() {
            {
                // setHeader(Kune.I18N.t("Longname"));
                setDataIndex("longName");
                setWidth(350);
                // setTooltip(Kune.I18N.t("Click to go to the group homepage"));
            }
        } });

        // columnModel.setDefaultSortable(true);

        String gridName = type == GROUP_USER_SEARCH ? "group-search" : "content-search";

        GridPanel grid = new GridPanel(gridName, 474, 250, store, columnModel);
        grid.setLoadMask(true);
        grid.setLoadMask(Kune.I18N.t("Searching"));
        grid.setSelectionModel(new RowSelectionModel());
        grid.setFrame(true);
        grid.setStripeRows(true);

        grid.addGridCellListener(new GridCellListenerAdapter() {
            public void onCellClick(final GridPanel grid, final int rowIndex, final int colindex, final EventObject e) {
                Record record = store.getRecordAt(rowIndex);
                String groupShortName = record.getAsString(id);
                presenter.doGoto(groupShortName);
            }
        });

        createPagingToolbar(store, grid);
        // grid.render(gridName);
        // grid.getTopToolbar().addSpacer();
        // grid.doLayout();
        return grid;
    }
}
