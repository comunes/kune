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
package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.AbstractSearcherPanel;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
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
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class SiteSearcherPanel extends AbstractSearcherPanel implements SiteSearcherView {

    private final Window dialog;
    private final SiteSearcherPresenter presenter;
    private Store groupStore;
    private ComboBox searchCombo;
    private Store historyStore;
    private Store contentStore;
    private GridPanel groupsGrid;
    private GridPanel contentGrid;
    private final WorkspaceSkeleton ws;
    private ToolbarButton traybarButton;

    public SiteSearcherPanel(final SiteSearcherPresenter initPresenter, final I18nTranslationService i18n,
	    final WorkspaceSkeleton ws) {
	super(i18n);
	this.presenter = initPresenter;
	this.ws = ws;
	dialog = createDialog();
    }

    public String getComboTextToSearch() {
	return searchCombo.getValue();
    }

    public void hide() {
	dialog.hide();
    }

    public void search(final String text, final SiteSearcherType type) {
	searchCombo.setValue(text);
	switch (type) {
	case group_user:
	    query(groupStore, groupsGrid, text);
	    break;
	case content:
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
	if (traybarButton == null) {
	    traybarButton = new ToolbarButton();
	    traybarButton.setTooltip(i18n.t("Show/hide searcher"));
	    traybarButton.setIcon("images/kune-search-ico-push.gif");
	    traybarButton.addListener(new ButtonListenerAdapter() {
		@Override
		public void onClick(final Button button, final EventObject e) {
		    if (dialog.isVisible()) {
			dialog.hide();
		    } else {
			dialog.show();
		    }
		}

	    });
	    ws.getSiteTraybar().addButton(traybarButton);
	}
    }

    private Window createDialog() {
	final Panel north = new Panel();
	north.setHeight(50);
	north.setBorder(false);

	final TabPanel centerPanel = new TabPanel();
	centerPanel.setActiveTab(0);
	centerPanel.setAutoScroll(true);
	centerPanel.setClosable(false);
	centerPanel.setBorder(false);

	final BasicDialog dialog = new BasicDialog(i18n.t("Search"), false, false, 500, 400);
	// dialog.setResizable(false);
	dialog.setIconCls("search-icon");
	final Button closeButton = new Button(i18n.tWithNT("Close", "used in button"));
	closeButton.addListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.doClose();
	    }
	});
	dialog.addButton(closeButton);

	final Panel searchPanel = createSearchForm(presenter);
	final Panel groupsPanel = new Panel("Groups & Users");
	final Panel contentPanel = new Panel("Content");
	groupsPanel.setLayout(new FitLayout());
	contentPanel.setLayout(new FitLayout());

	groupsGrid = createSearchPanel(SiteSearcherType.group_user);
	contentGrid = createSearchPanel(SiteSearcherType.content);
	groupsPanel.add(groupsGrid);
	contentPanel.add(contentGrid);
	centerPanel.add(groupsPanel);
	centerPanel.add(contentPanel);
	dialog.add(searchPanel, new BorderLayoutData(RegionPosition.NORTH));
	dialog.add(centerPanel, new BorderLayoutData(RegionPosition.CENTER));

	groupsPanel.addListener(new PanelListenerAdapter() {
	    public void onActivate(final Panel panel) {
		dialog.setTitle(i18n.t("Search users & groups"));
		presenter.doSearch(SiteSearcherType.group_user);
	    }
	});

	contentPanel.addListener(new PanelListenerAdapter() {
	    public void onActivate(final Panel panel) {
		dialog.setTitle(i18n.t("Search contents"));
		presenter.doSearch(SiteSearcherType.content);
	    }
	});

	final String panelId = groupsPanel.getId();
	centerPanel.setActiveItemID(panelId);

	dialog.setCloseAction(Window.HIDE);

	dialog.addListener(new WindowListenerAdapter() {
	    public void onCollapse(final Panel panel) {
		// dialog.hide();
	    }
	});

	return dialog;
    }

    private Panel createSearchForm(final SiteSearcherPresenter presenter) {
	final Panel searchPanel = new Panel();
	searchPanel.setBorder(false);

	final HorizontalPanel hp = new HorizontalPanel();

	final FormPanel form = new FormPanel();
	form.setBorder(false);
	form.setWidth(330);
	form.setHideLabels(true);

	historyStore = new SimpleStore(new String[] { "term" }, presenter.getSearchHistory());

	searchCombo = new ComboBox();
	searchCombo.setStore(historyStore);
	searchCombo.setDisplayField("term");
	searchCombo.setTypeAhead(false);
	searchCombo.setLoadingText(i18n.t("Searching..."));
	searchCombo.setWidth(300);
	searchCombo.setPageSize(10);
	searchCombo.setMode(ComboBox.LOCAL);
	searchCombo.setMinChars(1);
	searchCombo.setValueField("term");
	searchCombo.setForceSelection(false);
	searchCombo.setEditable(true);
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

	final Button searchBtn = new Button(i18n.tWithNT("Search", "used in button"));
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

    private GridPanel createSearchPanel(final SiteSearcherType type) {
	final String id = "shortName";
	final FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef(id), new StringFieldDef("longName"),
		new StringFieldDef("link"), new StringFieldDef("iconUrl") };
	final Store store;

	switch (type) {
	case group_user:
	    store = groupStore = createStore(fieldDefs, "/kune/json/GroupJSONService/search", id);
	    break;
	case content:
	    store = contentStore = createStore(fieldDefs, "/kune/json/ContentJSONService/search", id);
	    break;
	default:
	    throw new RuntimeException("Unknown type of search");
	}

	final ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { new ColumnConfig() {
	    {
		setDataIndex(id);
		setWidth(100);
	    }
	}, new ColumnConfig() {
	    {
		setDataIndex("longName");
		setWidth(350);
	    }
	} });

	// columnModel.setDefaultSortable(true);

	final String gridName = type == SiteSearcherType.group_user ? "group-search" : "content-search";

	final GridPanel grid = new GridPanel(gridName, 474, 250, store, columnModel);
	createPagingToolbar(store, grid);
	grid.setHideColumnHeader(true);
	// final GridView view = new GridView();
	// view.setForceFit(true);
	// // view.setEnableRowBody(true);
	// grid.setView(view);

	grid.addListener(new PanelListenerAdapter() {
	    public void onRender(final Component component) {
		Log.debug("Loading store");
		store.load(0, PAGINATION_SIZE);
	    }
	});

	grid.addGridCellListener(new GridCellListenerAdapter() {
	    public void onCellClick(final GridPanel grid, final int rowIndex, final int colindex, final EventObject e) {
		final Record record = store.getRecordAt(rowIndex);
		final String groupShortName = record.getAsString(id);
		presenter.doGoto(groupShortName);
	    }
	});

	return grid;
    }
}
