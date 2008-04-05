package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.tool.ToolTrigger.TriggerListener;
import org.ourproject.kune.platf.client.ui.HasColor;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class GroupToolsBar extends VerticalPanel {
    private static final String ITEM_SELECTED = "kune-GroupToolsBar-itemSelected";
    private static final String ITEM_NOT_SELECTED = "kune-GroupToolsBar-itemNotSelected";
    private Widget currentTab;
    private final HashMap<String, Widget> tabs;

    public GroupToolsBar() {
        tabs = new HashMap<String, Widget>();
        currentTab = null;
        addStyleName("kune-GroupToolsBar");
    }

    public void addItem(final ToolTrigger trigger) {
        final int nextIndex = this.getWidgetCount();
        final Widget menuItem = createItem(nextIndex, trigger);
        setTabSelected(menuItem, false);
        tabs.put(trigger.getName(), menuItem);
        this.add(menuItem);
    }

    private Widget createItem(final int index, final ToolTrigger trigger) {
        final SimplePanel menuItem = new SimplePanel();
        addStyleName("Tab");
        final Hyperlink hl = new Hyperlink(trigger.getLabel(), "");
        trigger.setListener(new TriggerListener() {
            public void onStateChanged(final String encoded) {
                hl.setTargetHistoryToken(encoded);
            }
        });
        menuItem.add(hl);
        return new RoundedBorderDecorator(menuItem, RoundedBorderDecorator.RIGHT);
    }

    public void selectItem(final String toolName) {
        if (currentTab != null) {
            setTabSelected(currentTab, false);
        }
        currentTab = getWidget(toolName);
        setTabSelected(currentTab, true);
    }

    public void setTabsColors(final String selectedColor, final String unSelectedColor) {
        final Iterator<Widget> iter = tabs.values().iterator();
        while (iter.hasNext()) {
            final Widget w = iter.next();
            if (w == currentTab) {
                ((HasColor) w).setColor(selectedColor);
            } else {
                ((HasColor) w).setColor(unSelectedColor);
            }
        }
    }

    private Widget getWidget(final String toolName) {
        return tabs.get(toolName);
    }

    private void setTabSelected(final Widget tab, final boolean isSelected) {
        ColorTheme theme = Kune.getInstance().theme;
        if (isSelected) {
            tab.removeStyleName(ITEM_NOT_SELECTED);
            tab.addStyleName(ITEM_SELECTED);
        } else {
            tab.removeStyleName(ITEM_SELECTED);
            tab.addStyleName(ITEM_NOT_SELECTED);
        }
        String color = isSelected ? theme.getToolSelected() : theme.getToolUnselected();
        ((HasColor) tab).setColor(color);
    }

}