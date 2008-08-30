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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.HorizontalLine;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.calclab.suco.client.signal.Slot;
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
    private final ContextTopBar topBar;
    private final I18nTranslationService i18n;

    public ContextItemsPanel(final ContextItemsPresenter presenter, final I18nTranslationService i18n,
	    final StateManager stateManager, final WorkspaceSkeleton ws) {
	this.i18n = i18n;
	topBar = new ContextTopBar(presenter, i18n, stateManager);
	addTopBar(topBar);

	items = new ItemsPanel(presenter);
	add(items, DockPanel.NORTH);
	final HTML expand = new HTML("<b></b>");
	add(expand, DockPanel.CENTER);
	controls = new VerticalPanel();
	add(controls, DockPanel.SOUTH);
	controls.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
	final HorizontalLine horizontalLine = new HorizontalLine();
	controls.add(horizontalLine);

	// setHeight("100%");
	expand.setHeight("15px");
	setCellHeight(expand, "15px");
	addStyleName("kune-NavigationBar");
	controls.setWidth("100%");
	controls.setCellWidth(horizontalLine, "100%");
	horizontalLine.setWidth("100%");
    }

    public void addCommand(final String buttonText, final String promptMessage, final Slot<String> slot) {
	final IconLabel iconLabel = new IconLabel(Images.App.getInstance().addGreen(), buttonText);
	iconLabel.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		promptName(promptMessage, slot);
	    }
	});
	iconLabel.addStyleName("kune-ContextItemsPanel-LabelLink");
	controls.add(iconLabel);

    }

    public void addItem(final String name, final String type, final String event, final boolean editable) {
	items.add(name, type, event, editable);
    }

    public void clear() {
	items.clear();
    }

    public void promptName(final String title, final Slot<String> slot) {
	MessageBox.prompt(title, i18n.t("Please enter a name:"), new MessageBox.PromptCallback() {
	    public void execute(final String btnID, final String text) {
		slot.onEvent(text);
	    }
	});
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
	items.registerType(typeName, image);
    }

    public void selectItem(final int index) {
    }

    public void setAbsolutePath(final ContainerSimpleDTO[] absolutePath) {
	topBar.setAbsolutePath(absolutePath);
    }

    public void setControlsVisible(final boolean visible) {
	controls.setVisible(visible);
    }

    public void setCurrentName(final String name) {
	topBar.currentFolder.setText(name);
    }

    public void setParentButtonEnabled(final boolean isEnabled) {
	topBar.btnGoParent.setVisible(isEnabled);
	topBar.btnGoParentDisabled.setVisible(!isEnabled);
    }

    public void setParentTreeVisible(final boolean visible) {
	topBar.firstRow.setVisible(visible);
    }

    private void addTopBar(final Widget widget) {
	add(topBar, DockPanel.NORTH);
    }
}
