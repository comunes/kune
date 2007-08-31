/*
 *
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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.HorizontalLine;
import org.ourproject.kune.platf.client.ui.IconHyperlink;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;

public class ContextItemsPanel extends DockPanel implements ContextItemsView {
    private final VerticalPanel controls;
    private final ItemsPanel items;
    private final TopBar topBar;
    private final ContextItemsPresenter presenter;
    private String currentEventName;
    private String workaroundTypeName;

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
	controls.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
	controls.add(new HorizontalLine());

	// setHeight("100%");
	expand.setHeight("15px");
	setCellHeight(expand, "15px");
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
	IconHyperlink hl = new IconHyperlink(Images.App.getInstance().addGreen(), label, "fixme");
	hl.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		currentEventName = eventName;
		presenter.onNew(type);
	    }
	});
	controls.add(hl);
    }

    public void showCreationField(final String typeName) {
	// Workaround: gwt-ext bug, I cannot use typeName directly
	workaroundTypeName = typeName;
	// i18n
	MessageBox.prompt("Add a new " + typeName, "Please enter name:", new MessageBox.PromptCallback() {
	    public void execute(final String btnID, final String text) {
		presenter.create(workaroundTypeName, text, currentEventName);
	    }
	});
    }
}
