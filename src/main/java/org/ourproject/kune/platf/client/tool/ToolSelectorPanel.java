package org.ourproject.kune.platf.client.tool;

import java.util.HashMap;
import java.util.Iterator;

import org.ourproject.kune.platf.client.tool.ToolTrigger.TriggerListener;
import org.ourproject.kune.platf.client.ui.HasColor;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolSelectorPanel extends VerticalPanel implements ToolSelectorView {

    private static final String ITEM_SELECTED = "kune-GroupToolsBar-itemSelected";
    private static final String ITEM_NOT_SELECTED = "kune-GroupToolsBar-itemNotSelected";
    private Widget currentTab;
    private final HashMap<String, Widget> tabs;

    public ToolSelectorPanel(final ToolSelectorPresenter presenter, final WorkspaceSkeleton ws) {
	tabs = new HashMap<String, Widget>();
	currentTab = null;
	addStyleName("kune-GroupToolsBar");
	ws.getEntitySummary().addInTools(this);
    }

    public void addItem(final ToolTrigger trigger) {
	final int nextIndex = this.getWidgetCount();
	final Widget menuItem = createItem(nextIndex, trigger);
	setTabSelected(menuItem, false);
	tabs.put(trigger.getName(), menuItem);
	this.add(menuItem);
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

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	// TODO Auto-generated method stub

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

    private Widget getWidget(final String toolName) {
	return tabs.get(toolName);
    }

    private void setTabSelected(final Widget tab, final boolean isSelected) {
	if (isSelected) {
	    tab.removeStyleName(ITEM_NOT_SELECTED);
	    tab.addStyleName(ITEM_SELECTED);
	} else {
	    tab.removeStyleName(ITEM_SELECTED);
	    tab.addStyleName(ITEM_NOT_SELECTED);
	}
	// tring color = isSelected ? colorTheme.getToolSelected() :
	// colorTheme.getToolUnselected();
	// ((HasColor) tab).setColor(color);
    }

}