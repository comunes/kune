package org.ourproject.kune.workspace.client.ui.ctx.items;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextItemsPanel extends DockPanel implements ContextItemsView {
    private final VerticalPanel controls;
    private final ItemsPanel items;
    private final TopBar topBar;
    private final ContextItemsPresenter presenter;
    private String currentEventName;

    public ContextItemsPanel(final ContextItemsPresenter presenter) {
	this.presenter = presenter;
	topBar = new TopBar(presenter);
	addTopBar(topBar);

	items = new ItemsPanel();
	add(items, DockPanel.NORTH);
	HTML expand = new HTML("<b></b>");
	add(expand, DockPanel.CENTER);
	controls = new VerticalPanel();
	add(controls, DockPanel.SOUTH);

	// FIXME: Test of width
	// setWidth("100%");
	// setHeight("100%");
	// setCellWidth(topBar, "100%");

	// expand.setWidth("100%");
	expand.setHeight("100%");
	// setCellWidth(expand, "100%");
	setCellHeight(expand, "100%");
	addStyleName("kune-NavigationBar");
    }

    private void addTopBar(final Widget widget) {
	add(topBar, DockPanel.NORTH);
    }

    public void addItem(final String name, final String type, final String event) {
	items.add(name, type, event);
    }

    public void selectItem(final int index) {
    }

    public void clear() {
	items.clear();
    }

    public void setCurrentName(final String name) {
	topBar.currentFolder.setText(name);
    }

    public void setParentButtonEnabled(final boolean isEnabled) {
	topBar.btnGoParent.setEnabled(isEnabled);
    }

    public void setParentTreeVisible(final boolean visible) {
	topBar.firstRow.setVisible(visible);
    }

    public void setControlsVisible(final boolean visible) {
	GWT.log("controls visible : " + visible, null);
	controls.setVisible(visible);
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
	items.registerType(typeName, image);
    }

    public void addCommand(final String typeName, final String label, final String eventName) {
	final String type = typeName;
	Button button = new Button(label, new ClickListener() {
	    public void onClick(final Widget sender) {
		currentEventName = eventName;
		presenter.onNew(type);
	    }
	});
	controls.add(button);
    }

    public void showCreationField(final String typeName) {
	String value = Window.prompt("Crear " + typeName + "con nombre: ", "");
	presenter.create(typeName, value, currentEventName);
    }
}
