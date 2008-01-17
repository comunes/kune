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
import org.ourproject.kune.platf.client.ui.AbstractSearcherPanel;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.workspace.client.workspace.ui.BottomTrayIcon;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.LoadMaskConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.Grid;
import com.gwtext.client.widgets.grid.GridConfig;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class SearchSitePanel extends AbstractSearcherPanel implements SearchSiteView {

    private final LayoutDialog dialog;
    private final SearchSitePresenter presenter;
    private Store groupStore;

    private ComboBox searchCombo;

    private Store historyStore;

    private Store contentStore;

    private BottomTrayIcon bottomIcon;
    private Grid groupsGrid;
    private Grid contentGrid;

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
                setResizable(false);
                setClosable(true);
                setProxyDrag(true);
                setCollapsible(true);
                // setTitle(Kune.I18N.t("Search results"));
            }
        }, north, null, null, null, center);
        final BorderLayout layout = dialog.getLayout();

        layout.beginUpdate();

        ContentPanel searchPanel = createSearchForm(presenter);

        ContentPanel groupsPanel = new ContentPanel(Ext.generateId(), "Groups & Users");
        ContentPanel contentPanel = new ContentPanel(Ext.generateId(), "Content");

        groupsGrid = createSearchPanel(GROUP_USER_SEARCH);
        contentGrid = createSearchPanel(CONTENT_SEARCH);
        groupsPanel.add(groupsGrid);
        ;
        contentPanel.add(contentGrid);
        layout.add(LayoutRegionConfig.NORTH, searchPanel);
        layout.add(LayoutRegionConfig.CENTER, groupsPanel);
        layout.add(LayoutRegionConfig.CENTER, contentPanel);

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
                dialog.setTitle(Kune.I18N.t("Search users & groups"));
                presenter.doSearch(GROUP_USER_SEARCH);
                tab.getTextEl().highlight();
            }
        });

        tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                dialog.setTitle(Kune.I18N.t("Search contents"));
                presenter.doSearch(CONTENT_SEARCH);
                tab.getTextEl().highlight();
            }
        });

        LayoutRegion centerRegion = layout.getRegion(LayoutRegionConfig.CENTER);
        String panelId = groupsPanel.getId();
        centerRegion.showPanel(panelId);

        dialog.addDialogListener(new DialogListenerAdapter() {
            public void onResize(final LayoutDialog dialog, final int width, final int height) {
                if (height < 40) {
                    dialog.hide();
                }
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

        historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());
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
                        historyStore.load();
                    }
                });
            }
        });
        searchCombo.addFieldListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                // Maybe we use...
            }

            public void onSpecialKey(final Field field, final EventObject e) {
                switch (e.getKey()) {
                case KeyboardListener.KEY_ENTER:
                    presenter.doSearch(getComboTextToSearch());
                    historyStore.load();
                    break;
                }
                e.stopEvent();
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

    private Grid createSearchPanel(final int type) {
        final String id = "shortName";
        FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef(id), new StringFieldDef("longName"),
                new StringFieldDef("link"), new StringFieldDef("iconUrl") };
        Store store;

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
                // setRender();
            }
        } });

        // columnModel.setDefaultSortable(true);

        String gridName = type == GROUP_USER_SEARCH ? "group-search" : "content-search";

        Grid grid = new Grid(gridName, "474px", "250px", store, columnModel, new GridConfig() {
            {
                setLoadMask(true);
                setLoadMask(new LoadMaskConfig(Kune.I18N.t("Searching")));
            }
        });

        grid.addGridCellListener(new GridCellListenerAdapter() {

            public void onCellClick(final Grid grid, final int rowIndex, final int colIndex, final EventObject e) {
                Record record = groupStore.getRecordAt(rowIndex);
                String groupShortName = record.getAsString(id);
                presenter.doGoto(groupShortName);
            }
        });

        grid.render();

        // createPagingToolbar(store, grid);

        return grid;
    }
}
