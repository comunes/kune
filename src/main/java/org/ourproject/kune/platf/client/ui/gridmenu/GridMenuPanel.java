package org.ourproject.kune.platf.client.ui.gridmenu;

import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Slot;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.GroupingStore;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SortState;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragSource;
import com.gwtext.client.dd.DropTarget;
import com.gwtext.client.dd.DropTargetConfig;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridDragData;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.GroupingView;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListener;
import com.gwtext.client.widgets.layout.FitLayout;

public class GridMenuPanel<T> extends Composite {
    public static final String GRID_MENU_PANEL_DD = "gridMenuPanelDD";
    public static final int DEFAULT_INITIAL_WIDTH = 150;
    private static final String GROUP = "groupField";
    private static final String GROUP_TOOLTIP_TITLE = "groupTooltipTitleField";
    private static final String GROUP_TOOLTIP = "groupTooltipField";
    private static final String GROUP_ENDICON_HTML = "groupEndIconHtmlField";
    private static final String ID = "idField";
    private static final String ICON_HTML = "iconHtmlField";
    private static final String TITLE = "titleField";
    private static final String TITLE_HTML = "titleHtmlField";
    private static final String END_ICON_HTML = "endIconHtmlField";
    private static final String TOOLTIP = "tooltipField";
    private static final String TOOLTIPTITLE = "tooltipTitleField";
    private final HashMap<String, GridMenu<T>> menuMap;
    private final HashMap<T, Record> recordMap;
    private RecordDef recordDef;
    private GroupingStore store;
    private GridPanel grid;
    private final Panel panel;
    private final boolean grouped;
    private final boolean withCounters;
    private final Signal<String> onClick;
    private final Signal<String> onDoubleClick;
    private Toolbar topBar;
    private Toolbar bottomBar;
    private final String emptyText;
    private final GridDragConfiguration gridDragConfiguration;
    private final GridDropConfiguration gridDropConfiguration;
    private ColumnModel columnModel;
    private final boolean withEndIcon;

    public GridMenuPanel(final String emptyText) {
	this(emptyText, null, null, false, false, false, false, false);
    }

    public GridMenuPanel(final String emptyText, final boolean grouped, final boolean withCounters) {
	this(emptyText, null, null, grouped, withCounters, false, false, false);
    }

    public GridMenuPanel(final String emptyText, final boolean grouped, final boolean withCounters,
	    final boolean withTopBar, final boolean withBottomBar, final boolean withEndIcon) {
	this(emptyText, null, null, grouped, withCounters, withTopBar, withBottomBar, withEndIcon);
    }

    public GridMenuPanel(final String emptyText, final GridDragConfiguration gridDragConfiguration) {
	this(emptyText, gridDragConfiguration, null, false, false, false, false, false);
    }

    public GridMenuPanel(final String emptyText, final GridDragConfiguration dragConf, final boolean grouped,
	    final boolean withCounters, final boolean withTopBar, final boolean withBottomBar, final boolean withEndIcon) {
	this(emptyText, dragConf, null, grouped, withCounters, withTopBar, withBottomBar, withEndIcon);
    }

    public GridMenuPanel(final String emptyText, final GridDragConfiguration gridDragConfiguration,
	    final GridDropConfiguration gridDropConfiguration, final boolean grouped, final boolean withCounters,
	    final boolean withTopBar, final boolean withBottomBar, final boolean withEndIcon) {
	this.emptyText = emptyText;
	this.gridDragConfiguration = gridDragConfiguration;
	this.gridDropConfiguration = gridDropConfiguration;
	this.onClick = new Signal<String>("onClick");
	this.onDoubleClick = new Signal<String>("onDoubleClick");
	this.grouped = grouped;
	this.withCounters = withCounters;
	this.withEndIcon = withEndIcon;
	panel = new Panel();
	panel.setBorder(false);
	panel.setLayout(new FitLayout());
	if (withTopBar) {
	    topBar = new Toolbar();
	    panel.setTopToolbar(topBar);
	}
	if (withBottomBar) {
	    bottomBar = new Toolbar();
	    panel.setBottomToolbar(bottomBar);
	}
	menuMap = new HashMap<String, GridMenu<T>>();
	recordMap = new HashMap<T, Record>();
	initWidget(panel);
    }

    public GridMenuPanel(final String emptyText, final GridDropConfiguration gridDropConfiguration) {
	this(emptyText, null, gridDropConfiguration, false, false, false, false, false);
    }

    public GridMenuPanel(final String emptyText, final GridDropConfiguration dropConf, final boolean grouped,
	    final boolean withCounters, final boolean withTopBar, final boolean withBottomBar, final boolean withEndIcon) {
	this(emptyText, null, dropConf, grouped, withCounters, withTopBar, withBottomBar, withEndIcon);
    }

    public void addItem(final GridItem<T> gridItem) {
	createGridIfNeeded();
	final String id = gridItem.getId();
	final Record newRecord = recordDef
		.createRecord(id, new Object[] { gridItem.getGroup().getName(), gridItem.getGroup().getTooltipTitle(),
			gridItem.getGroup().getTooltip(), gridItem.getGroup().getEndIconHtml(), id,
			gridItem.getIconHtml(), gridItem.getTitle(), gridItem.getTitleHtml(),
			gridItem.getEndIconHtml(), gridItem.getTooltipTitle(), gridItem.getTooltip() });
	recordMap.put(gridItem.getItem(), newRecord);
	store.addSorted(newRecord);
	menuMap.put(id, gridItem.getMenu());
	sort();
	doLayoutIfNeeded();
    }

    public void confDropInPanel(final Panel panel, final GridDropConfiguration gridDropConfiguration) {
	// FIXME: This doesn't works :-/
	// http://extjs.com/forum/showthread.php?t=18105
	final DropTargetConfig dCfg = new DropTargetConfig();
	dCfg.setTarget(true);
	dCfg.setdDdGroup(gridDropConfiguration.getDdGroupId());
	new DropTarget(panel, dCfg) {
	    @Override
	    public boolean notifyDrop(final DragSource src, final EventObject e, final DragData dragData) {
		if (dragData instanceof GridDragData) {
		    final GridDragData gridDragData = (GridDragData) dragData;
		    final Record[] records = gridDragData.getSelections();
		    for (int i = 0; i < records.length; i++) {
			gridDropConfiguration.fire(records[i].getAsString(ID));
		    }
		}
		return true;
	    }

	    @Override
	    public String notifyEnter(final DragSource src, final EventObject e, final DragData data) {
		return "x-tree-drop-ok-append";
	    }

	    @Override
	    public String notifyOver(final DragSource src, final EventObject e, final DragData data) {
		return "x-tree-drop-ok-append";
	    }
	};
    }

    public Toolbar getBottomBar() {
	assert bottomBar != null;
	return bottomBar;
    }

    public Toolbar getTopBar() {
	assert topBar != null;
	return topBar;
    }

    public void onClick(final Slot<String> slot) {
	onClick.add(slot);
    }

    public void onDoubleClick(final Slot<String> slot) {
	onDoubleClick.add(slot);
    }

    public void removeAll() {
	if (grid != null) {
	    store.removeAll();
	}
	recordMap.clear();
	menuMap.clear();
    }

    public void removeItem(final GridItem<T> gridItem) {
	final Record record = recordMap.get(gridItem.getItem());
	if (record != null) {
	    menuMap.remove(record);
	    store.remove(record);
	    recordMap.remove(gridItem.getItem());
	    doLayoutIfNeeded();
	} else {
	    Log.error("Trying to remove a non existing item: " + gridItem.getId());
	}
    }

    public void setWidth(final int width) {
	if (grid != null) {
	    grid.setWidth(width - 27);
	    doLayoutIfNeeded();
	}
    }

    public void sort() {
	store.sort(GROUP);
    }

    public void updateItem(final GridItem<T> gridItem) {
	final String id = gridItem.getId();
	final Record record = recordMap.get(gridItem.getItem());
	if (record != null) {
	    record.set(GROUP, gridItem.getGroup().getName());
	    record.set(GROUP_TOOLTIP_TITLE, gridItem.getGroup().getTooltipTitle());
	    record.set(GROUP_TOOLTIP, gridItem.getGroup().getTooltip());
	    record.set(GROUP_ENDICON_HTML, gridItem.getGroup().getEndIconHtml());
	    record.set(ICON_HTML, gridItem.getIconHtml());
	    record.set(TITLE, gridItem.getTitle());
	    record.set(TITLE_HTML, gridItem.getTitleHtml());
	    record.set(END_ICON_HTML, gridItem.getEndIconHtml());
	    record.set(TOOLTIPTITLE, gridItem.getTooltipTitle());
	    record.set(TOOLTIP, gridItem.getTooltip());
	    store.commitChanges();
	    menuMap.put(id, gridItem.getMenu());
	    sort();
	    doLayoutIfNeeded();
	} else {
	    Log.error("Trying to update a non existing item: " + id);
	}
    }

    private void configureDrag(final GridDragConfiguration gridDragConfiguration) {
	// TODO: put this in GDConf
	grid.setEnableDragDrop(true);
	grid.setDdGroup(gridDragConfiguration.getDdGroupId());
	grid.setDragDropText(gridDragConfiguration.getDragMessage());
    }

    private void configureDrop(final GridDropConfiguration gridDropConfiguration) {
	// TODO: put this in GDConf
	grid.setEnableDragDrop(true);
	grid.setDdGroup(gridDropConfiguration.getDdGroupId());
	final DropTargetConfig dCfg = new DropTargetConfig();
	dCfg.setTarget(true);
	dCfg.setdDdGroup(gridDropConfiguration.getDdGroupId());
	new DropTarget(grid, dCfg) {
	    @Override
	    public boolean notifyDrop(final DragSource src, final EventObject e, final DragData dragData) {
		if (dragData instanceof GridDragData) {
		    final GridDragData gridDragData = (GridDragData) dragData;
		    final Record[] records = gridDragData.getSelections();
		    for (int i = 0; i < records.length; i++) {
			gridDropConfiguration.fire(records[i].getAsString(ID));
		    }
		}
		return true;
	    }

	    @Override
	    public String notifyEnter(final DragSource src, final EventObject e, final DragData data) {
		return "x-tree-drop-ok-append";
	    }

	    @Override
	    public String notifyOver(final DragSource src, final EventObject e, final DragData data) {
		return "x-tree-drop-ok-append";
	    }
	};
    }

    private void createGrid(final String emptyText, final GridDragConfiguration gridDragConfiguration,
	    final GridDropConfiguration gridDropConfiguration) {
	grid = new GridPanel();
	final FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef(GROUP),
		new StringFieldDef(GROUP_TOOLTIP_TITLE), new StringFieldDef(GROUP_TOOLTIP),
		new StringFieldDef(GROUP_ENDICON_HTML), new StringFieldDef(ID), new StringFieldDef(ICON_HTML),
		new StringFieldDef(TITLE), new StringFieldDef(TITLE_HTML), new StringFieldDef(END_ICON_HTML),
		new StringFieldDef(TOOLTIPTITLE), new StringFieldDef(TOOLTIP) };
	recordDef = new RecordDef(fieldDefs);

	final MemoryProxy proxy = new MemoryProxy(new Object[][] {});

	final ArrayReader reader = new ArrayReader(1, recordDef);
	store = new GroupingStore(proxy, reader);
	store.setSortInfo(new SortState(GROUP, SortDir.DESC));
	store.setGroupField(GROUP);
	store.setGroupOnSort(true);
	store.load();
	grid.setStore(store);

	// TODO: Change this with a method that modify the html
	final String commonTootipHtmlRender = "<span ext:qtitle=\"{1}\" ext:qtip=\"{2}\">{0}</span>";

	final Renderer iconHtmlRenderer = new Renderer() {
	    public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
		    Store store) {
		return Format.format(commonTootipHtmlRender, new String[] { record.getAsString(ICON_HTML),
			record.getAsString(TOOLTIPTITLE), record.getAsString(TOOLTIP) });
	    }
	};

	final Renderer titleHtmlRenderer = new Renderer() {
	    public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
		    Store store) {
		return Format.format(commonTootipHtmlRender, new String[] { record.getAsString(TITLE_HTML),
			record.getAsString(TOOLTIPTITLE), record.getAsString(TOOLTIP) });
	    }
	};

	final Renderer endIconHtmlRenderer = new Renderer() {
	    public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
		    Store store) {
		return Format.format(commonTootipHtmlRender, new String[] { record.getAsString(END_ICON_HTML),
			record.getAsString(TOOLTIPTITLE), record.getAsString(TOOLTIP) });
	    }
	};

	final ColumnConfig iconColumn = new ColumnConfig("Icon", ICON_HTML, 24, false, iconHtmlRenderer, ICON_HTML);
	final ColumnConfig titleColumn = new ColumnConfig("Title", TITLE_HTML, 100, true, titleHtmlRenderer, TITLE_HTML);
	final ColumnConfig endIconColumn = new ColumnConfig("EndIcon", END_ICON_HTML, 24, false, endIconHtmlRenderer,
		END_ICON_HTML);
	final ColumnConfig groupColumn = new ColumnConfig(GROUP, GROUP, 0);
	// Fixed widths?
	iconColumn.setFixed(true);
	titleColumn.setFixed(false);
	endIconColumn.setFixed(true);
	final ColumnConfig[] columnsConfigs = withEndIcon ? new ColumnConfig[] { iconColumn, titleColumn,
		endIconColumn, groupColumn } : new ColumnConfig[] { iconColumn, titleColumn, groupColumn };
	columnModel = new ColumnModel(columnsConfigs);
	grid.setColumnModel(columnModel);

	grid.setAutoExpandColumn(TITLE_HTML);
	grid.setSelectionModel(new RowSelectionModel());

	grid.addGridRowListener(new GridRowListener() {
	    public void onRowClick(final GridPanel grid, final int rowIndex, final EventObject e) {
		showMenu(rowIndex, e);
		onClick(rowIndex);
	    }

	    public void onRowContextMenu(final GridPanel grid, final int rowIndex, final EventObject e) {
		showMenu(rowIndex, e);
	    }

	    public void onRowDblClick(final GridPanel grid, final int rowIndex, final EventObject e) {
		onDoubleClick(rowIndex);
	    }

	    private void showMenu(final int rowIndex, final EventObject e) {
		final Record record = store.getRecordAt(rowIndex);
		final GridMenu<T> menu = menuMap.get(record.getAsString(ID));
		if (menu != null) {
		    menu.showMenu(e);
		}
	    }
	});
	grid.stripeRows(true);
	grid.setHideColumnHeader(true);
	// Not sure if when doLayoutIfNeeded there are problems with size and
	// column header
	grid.setBorder(false);
	grid.setAutoScroll(true);

	if (grouped) {
	    final GroupingView groupView = new GroupingView();
	    groupView.setForceFit(true);
	    final String groupEndIconHtmlTpl = "{[values.rs[0].data[\"" + GROUP_ENDICON_HTML + "\"]]}";
	    // http://www.gwt-ext.com/forum/viewtopic.php?f=7&t=1388&p=6789&hilit=setGroupTextTpl#p6789
	    if (withCounters) {
		groupView.setGroupTextTpl("<span style=\"overflow: hidden;\" ext:qtip=\"{[values.rs[0].data[\""
			+ GROUP_TOOLTIP + "\"]]}\">{[values.rs[0].data[\"" + GROUP
			+ "\"]]} ({[values.rs.length]})</span>" + groupEndIconHtmlTpl);
	    } else {
		groupView.setGroupTextTpl("<span style=\"overflow: hidden;\" ext:qtip=\"{[values.rs[0].data[\""
			+ GROUP_TOOLTIP + "\"]]}\">{[values.rs[0].data[\"" + GROUP + "\"]]}</span>"
			+ groupEndIconHtmlTpl);
	    }
	    // Other sample with condition:
	    // view.setGroupTextTpl("{text} ({[values.rs.length]}
	    // {[values.rs.length
	    // > 1 ? \"Items\" : \"Item\"]})");
	    groupView.setHideGroupedColumn(true);
	    groupView.setEmptyGroupText(emptyText);
	    groupView.setShowGroupsText(true);
	    groupView.setEnableNoGroups(true);
	    groupView.setEmptyText(emptyText);
	    groupView.setGroupByText(GROUP);
	    groupView.setIgnoreAdd(true);
	    groupView.setEnableGrouping(true);
	    grid.setView(groupView);
	} else {
	    final GridView view = new GridView();
	    view.setForceFit(true);
	    grid.setView(view);
	}

	grid.setAutoWidth(true);
	grid.setAutoHeight(true);

	if (gridDropConfiguration != null) {
	    configureDrop(gridDropConfiguration);
	}
	if (gridDragConfiguration != null) {
	    configureDrag(gridDragConfiguration);
	} else {
	    grid.setDraggable(false);
	}
	panel.add(grid);
    }

    private void createGridIfNeeded() {
	if (grid == null) {
	    createGrid(emptyText, gridDragConfiguration, gridDropConfiguration);
	}
    }

    private void doLayoutIfNeeded() {
	if (panel.isRendered()) {
	    panel.doLayout();
	}
    }

    private void onClick(final int rowIndex) {
	final Record record = store.getRecordAt(rowIndex);
	onClick.fire(record.getAsString(ID));
    }

    private void onDoubleClick(final int rowIndex) {
	final Record record = store.getRecordAt(rowIndex);
	onDoubleClick.fire(record.getAsString(ID));
    }
}
