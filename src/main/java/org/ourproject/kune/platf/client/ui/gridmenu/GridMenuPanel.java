/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.platf.client.ui.gridmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Listener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
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
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.BoxComponentListenerAdapter;
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

public class GridMenuPanel<T> extends Panel {
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
    private final Map<String, CustomMenu<T>> menuMap;
    private final Map<T, Record> recordMap;
    private RecordDef recordDef;
    private GroupingStore store;
    private GridPanel grid;
    private final boolean grouped;
    private final boolean withCounters;
    private final Event<String> onClick;
    private final Event<String> onDoubleClick;
    private Toolbar topBar;
    private Toolbar bottomBar;
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
        this.onClick = new Event<String>("onClick");
        this.onDoubleClick = new Event<String>("onDoubleClick");
        this.grouped = grouped;
        this.withCounters = withCounters;
        this.withEndIcon = withEndIcon;
        super.setBorder(false);
        super.setLayout(new FitLayout());
        // super.setAutoScroll(true);
        if (withTopBar) {
            topBar = new Toolbar();
            super.setTopToolbar(topBar);
        }
        if (withBottomBar) {
            bottomBar = new Toolbar();
            super.setBottomToolbar(bottomBar);
        }
        menuMap = new HashMap<String, CustomMenu<T>>();
        recordMap = new HashMap<T, Record>();
        super.addListener(new BoxComponentListenerAdapter() {
            @Override
            public void onRender(final Component component) {
                // Log.warn("Render grid -------------------");
            }
        });
        createGrid(emptyText, gridDragConfiguration, gridDropConfiguration);
    }

    public GridMenuPanel(final String emptyText, final GridDropConfiguration gridDropConfiguration) {
        this(emptyText, null, gridDropConfiguration, false, false, false, false, false);
    }

    public GridMenuPanel(final String emptyText, final GridDropConfiguration dropConf, final boolean grouped,
            final boolean withCounters, final boolean withTopBar, final boolean withBottomBar, final boolean withEndIcon) {
        this(emptyText, null, dropConf, grouped, withCounters, withTopBar, withBottomBar, withEndIcon);
    }

    public void addItem(final GridItem<T> gridItem) {
        final String id = gridItem.getId();
        final Record newRecord = recordDef.createRecord(id, new Object[] { gridItem.getGroup().getName(),
                gridItem.getGroup().getTooltipTitle(), gridItem.getGroup().getTooltip(),
                gridItem.getGroup().getEndIconHtml(), id, gridItem.getIconHtml(), gridItem.getTitle(),
                gridItem.getTitleHtml(), gridItem.getEndIconHtml(), gridItem.getTooltipTitle(), gridItem.getTooltip() });
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
                    for (Record record : records) {
                        gridDropConfiguration.fire(record.getAsString(ID));
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

    @Override
    public void doLayout(final boolean shallow) {
        // Grid rendered problems with shallow false
        grid.doLayout(true);
        super.doLayout(true);
    }

    public void doLayoutIfNeeded() {
        if (super.isRendered()) {
            grid.doLayout(true);
            super.doLayout(true);
        }
    }

    public Toolbar getBottomBar() {
        assert bottomBar != null;
        return bottomBar;
    }

    public Toolbar getTopBar() {
        assert topBar != null;
        return topBar;
    }

    public void onClick(final Listener<String> listener) {
        onClick.add(listener);
    }

    public void onDoubleClick(final Listener<String> listener) {
        onDoubleClick.add(listener);
    }

    @Override
    public void removeAll() {
        store.removeAll();
        recordMap.clear();
        menuMap.clear();
    }

    public void removeItem(final GridItem<T> gridItem) {
        final Record record = recordMap.get(gridItem.getItem());
        if (record == null) {
            Log.error("Trying to remove a non existing item: " + gridItem.getId());
        } else {
            menuMap.remove(gridItem.getId());
            store.remove(record);
            recordMap.remove(gridItem.getItem());
            doLayoutIfNeeded();
        }
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);
        doLayoutIfNeeded();
    }

    @Override
    public void setWidth(final int width) {
        grid.setWidth(width - 27);
        // super.setWidth(width);
        doLayoutIfNeeded();
    }

    public void sort() {
        store.sort(GROUP, SortDir.ASC);
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

    private void configureDragImpl(final GridDragConfiguration gridDragConfiguration) {
        // TODO: put this in GDConf
        grid.setEnableDragDrop(true);
        grid.setDdGroup(gridDragConfiguration.getDdGroupId());
        grid.setDragDropText(gridDragConfiguration.getDragMessage());
    }

    private void configureDropImpl(final GridDropConfiguration gridDropConfiguration) {
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
                    for (Record record : records) {
                        gridDropConfiguration.fire(record.getAsString(ID));
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
        grid.setId(Ext.generateId());
        final FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef(GROUP),
                new StringFieldDef(GROUP_TOOLTIP_TITLE), new StringFieldDef(GROUP_TOOLTIP),
                new StringFieldDef(GROUP_ENDICON_HTML), new StringFieldDef(ID), new StringFieldDef(ICON_HTML),
                new StringFieldDef(TITLE), new StringFieldDef(TITLE_HTML), new StringFieldDef(END_ICON_HTML),
                new StringFieldDef(TOOLTIPTITLE), new StringFieldDef(TOOLTIP) };
        recordDef = new RecordDef(fieldDefs);

        final MemoryProxy proxy = new MemoryProxy(new Object[][] {});

        final ArrayReader reader = new ArrayReader(1, recordDef);
        store = new GroupingStore(proxy, reader);
        store.setSortInfo(new SortState(GROUP, SortDir.ASC));
        store.setGroupField(GROUP);
        store.setGroupOnSort(true);
        store.load();
        grid.setStore(store);

        // TODO: Change this with a method that modify the html
        final String commonTootipHtmlRender = "<span ext:qtitle=\"{1}\" ext:qtip=\"{2}\">{0}</span>";

        final Renderer iconHtmlRenderer = new Renderer() {
            public String render(final Object value, final CellMetadata cellMetadata, final Record record,
                    final int rowIndex, final int colNum, final Store store) {
                return Format.format(commonTootipHtmlRender, new String[] { record.getAsString(ICON_HTML),
                        record.getAsString(TOOLTIPTITLE), record.getAsString(TOOLTIP) });
            }
        };

        final Renderer titleHtmlRenderer = new Renderer() {
            public String render(final Object value, final CellMetadata cellMetadata, final Record record,
                    final int rowIndex, final int colNum, final Store store) {
                return Format.format(commonTootipHtmlRender, new String[] { record.getAsString(TITLE_HTML),
                        record.getAsString(TOOLTIPTITLE), record.getAsString(TOOLTIP) });
            }
        };

        final Renderer endIconHtmlRenderer = new Renderer() {
            public String render(final Object value, final CellMetadata cellMetadata, final Record record,
                    final int rowIndex, final int colNum, final Store store) {
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
        final ArrayList<ColumnConfig> columnList = new ArrayList<ColumnConfig>();
        columnList.add(iconColumn);
        columnList.add(titleColumn);
        if (grouped) {
            columnList.add(groupColumn);
        }
        if (withEndIcon) {
            columnList.add(endIconColumn);
        }
        ColumnConfig[] columnsConfigs = {};
        columnsConfigs = columnList.toArray(columnsConfigs);
        columnModel = new ColumnModel(columnsConfigs);
        grid.setColumnModel(columnModel);

        grid.setAutoExpandColumn(TITLE_HTML);
        grid.setSelectionModel(new RowSelectionModel());

        grid.addGridRowListener(new GridRowListener() {
            public void onRowClick(final GridPanel grid, final int rowIndex, final EventObject e) {
                showMenu(rowIndex, e);
                onClickImpl(rowIndex);
            }

            public void onRowContextMenu(final GridPanel grid, final int rowIndex, final EventObject e) {
                showMenu(rowIndex, e);
            }

            public void onRowDblClick(final GridPanel grid, final int rowIndex, final EventObject e) {
                onDoubleClickImpl(rowIndex);
            }

            private void showMenu(final int rowIndex, final EventObject e) {
                final Record record = store.getRecordAt(rowIndex);
                final CustomMenu<T> menu = menuMap.get(record.getAsString(ID));
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
            configureDropImpl(gridDropConfiguration);
        }
        if (gridDragConfiguration != null) {
            configureDragImpl(gridDragConfiguration);
        } else {
            grid.setDraggable(false);
        }
        super.add(grid);
    }

    private void onClickImpl(final int rowIndex) {
        final Record record = store.getRecordAt(rowIndex);
        onClick.fire(record.getAsString(ID));
    }

    private void onDoubleClickImpl(final int rowIndex) {
        final Record record = store.getRecordAt(rowIndex);
        onDoubleClick.fire(record.getAsString(ID));
    }

}
