package org.ourproject.kune.platf.client.tool;

import java.util.HashMap;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.calclab.suco.client.signal.Slot2;

public class ToolSelectorPresenter implements ToolSelector {

    private ToolSelectorView view;
    private final HashMap<String, ToolSelectorItem> tools;

    public ToolSelectorPresenter(final StateManager stateManager, final WsThemePresenter wsThemePresenter) {
	tools = new HashMap<String, ToolSelectorItem>();
	stateManager.onGroupChanged(new Slot2<String, String>() {
	    public void onEvent(final String oldGroupName, final String newGroupName) {
		onGroupChanged(newGroupName);
	    }
	});
	stateManager.onToolChanged(new Slot2<String, String>() {
	    public void onEvent(final String oldTool, final String newTool) {
		onToolChanged(oldTool, newTool);
	    }
	});
	wsThemePresenter.onThemeChanged(new Slot2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		view.setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void addTool(final ToolSelectorItem item) {
	final String name = item.getShortName();
	if (name == null) {
	    throw new RuntimeException("You cannot add a tool without a name");
	}
	if (tools.get(name) != null) {
	    throw new RuntimeException("A tool with the same name already added");
	}
	tools.put(name, item);
	item.setSelected(false);
    }

    public View getView() {
	return view;
    }

    public void init(final ToolSelectorView view) {
	this.view = view;
    }

    void onGroupChanged(final String newGroupName) {
	for (final String name : tools.keySet()) {
	    tools.get(name).setGroupShortName(newGroupName);
	}
    }

    void onToolChanged(final String oldTool, final String newTool) {
	if (oldTool != null) {
	    tools.get(oldTool).setSelected(false);
	}
	tools.get(newTool).setSelected(true);
    }

}
