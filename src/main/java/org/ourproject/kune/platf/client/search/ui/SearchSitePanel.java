package org.ourproject.kune.platf.client.search.ui;

import org.ourproject.kune.platf.client.search.SearchSitePresenter;
import org.ourproject.kune.platf.client.search.SearchSiteView;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class SearchSitePanel implements SearchSiteView {

    private final LayoutDialog dialog;
    private Grid resultsGrid;

    public SearchSitePanel(final SearchSitePresenter presenter) {

        LayoutRegionConfig north = new LayoutRegionConfig() {
            {
                setSplit(false);
                setInitialSize(80);
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

        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(false);
                setWidth(500);
                setHeight(400);
                setShadow(true);
                setResizable(true);
                setClosable(true);
                setProxyDrag(true);
                setCollapsible(false);
                // i18n
                setTitle("Search results");
            }
        }, north, null, null, null, center);
        final BorderLayout layout = dialog.getLayout();

        layout.beginUpdate();

        ContentPanel searchPanel = createSearchPanel(presenter);

        ContentPanel groupsPanel = new ContentPanel(Ext.generateId(), "Groups");

        // groupsPanel = createUsersPanel();

        ContentPanel usersPanel = new ContentPanel(Ext.generateId(), "Users");

        // usersPanel = createGroupsPanel();

        layout.add(LayoutRegionConfig.NORTH, searchPanel);

        layout.add(LayoutRegionConfig.CENTER, groupsPanel);
        layout.add(LayoutRegionConfig.CENTER, usersPanel);

        layout.endUpdate();

        // i18n
        final Button closeBtn = new Button("Close");
        closeBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.doClose();
            }

        });
        dialog.addButton(closeBtn);

        TabPanel tabPanel = layout.getRegion(LayoutRegionConfig.CENTER).getTabs();

        tabPanel.getTab(0).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) {
                // i18n
                dialog.setTitle("Search groups");
                presenter.doSearchGroups();
                tab.getTextEl().highlight();
            }
        });

        tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(final TabPanelItem tab) { // i18n
                dialog.setTitle("Search users");
                presenter.doSearchUsers();
                tab.getTextEl().highlight();
            }
        });
    }

    private ContentPanel createSearchPanel(final SearchSitePresenter presenter) {

        ContentPanel searchPanel = new ContentPanel(Ext.generateId(), "Search");

        HorizontalPanel hp = new HorizontalPanel();

        Form form = new Form(new FormConfig() {
            {
                setWidth(610);
                setHideLabels(true);
                // i18n
                // setHeader("Type something to search");
            }
        });

        final Store store = new SimpleStore(new String[] { "term" }, getSearchHistory());
        store.load();

        ComboBox cb = new ComboBox(new ComboBoxConfig() {
            {
                setStore(store);
                setDisplayField("term");
                setTypeAhead(false);
                // i18n
                setLoadingText("Searching...");
                setWidth(300);
                setPageSize(10);
                setHideTrigger(true);
                setMode("local");
                setMinChars(2);
                // setTitle("Kune search");

                setComboBoxListener(new ComboBoxListenerAdapter() {
                    public void onSelect(ComboBox comboBox, Record record, int index) {
                        presenter.doSearch(record.getAsString("term"));
                    }

                });
            }
        });

        form.add(cb);
        form.render();
        // i18n
        Button searchBtn = new Button("Search");
        hp.add(form);
        hp.add(searchBtn);
        hp.setSpacing(7);
        hp.addStyleName("kune-Margin-Large-tlbr");
        searchPanel.add(hp);
        return searchPanel;
    }

    private Object[][] getSearchHistory() {
        return new Object[][] { new Object[] { "bla bla" }, new Object[] { "la la" }, new Object[] { "fofo" } };
    }

    private ContentPanel createGroupsPanel() {

        // TODO Auto-generated method stub
        return null;
    }

    private ContentPanel createUsersPanel() {
        // TODO Auto-generated method stub
        return null;
    }

    private void createBottonToolbar() {
        // ExtElement gridFoot = resultsGrid.getView().getFooterPanel(true);

        String gridFoot = "";
        Toolbar toolbar = new Toolbar(gridFoot);
        ToolbarButton first = new ToolbarButton(new ButtonConfig() {
            {
                setTooltip("First Page");
                setCls("x-btn-icon x-grid-page-first");
                setDisabled(true);
                setButtonListener(new ButtonListenerAdapter() {
                    public void onClick(Button button, EventObject e) {
                        // serverRequest("first");
                    }
                });
            }
        });
        toolbar.addButton(first);

        ToolbarButton prev = new ToolbarButton(new ButtonConfig() {
            {
                setTooltip("Previous Page");
                setCls("x-btn-icon x-grid-page-prev");
                setDisabled(true);
                setButtonListener(new ButtonListenerAdapter() {
                    public void onClick(Button button, EventObject e) {
                        // serverRequest("prev");
                    }
                });
            }
        });
        toolbar.addButton(prev);

        toolbar.addSeparator();
        ToolbarTextItem pageInfo = new ToolbarTextItem("Page 1 of 1");
        toolbar.addItem(pageInfo);
        toolbar.addSeparator();
    }

    public void show() {
        dialog.show();
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
    }

}
