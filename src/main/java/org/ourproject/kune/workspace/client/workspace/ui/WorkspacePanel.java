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

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private final LogoPanel logoPanel;
    private final HorizontalPanel contentTitleBar;
    private final HorizontalPanel contentSubTitleBar;
    private final GroupToolsBar groupToolsBar;
    private final HorizontalSplitPanel cntcxtHSP;
    private final VerticalPanel contextVP;
    private final VerticalPanel contentVP;
    private final HorizontalPanel contentBottomBar;
    private final VerticalPanel generalDropDownsPanel;

    public WorkspacePanel() {
	final VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	logoPanel = new LogoPanel();
	final HorizontalPanel generalHP = new HorizontalPanel();

	generalVP.add(logoPanel);
	generalVP.add(generalHP);

	final VerticalPanel groupAreaVP = new VerticalPanel();
	final VerticalPanel groupNavBarVP = new VerticalPanel();
	generalHP.add(groupAreaVP);
	generalHP.add(groupNavBarVP);

	groupToolsBar = new GroupToolsBar();
	generalDropDownsPanel = new VerticalPanel();
	groupNavBarVP.add(groupToolsBar);
	groupNavBarVP.add(generalDropDownsPanel);

	contentTitleBar = new HorizontalPanel();
	contentSubTitleBar = new HorizontalPanel();
	cntcxtHSP = new HorizontalSplitPanel();
	contentVP = new VerticalPanel();
	contextVP = new VerticalPanel();
	cntcxtHSP.setLeftWidget(contentVP);
	cntcxtHSP.setRightWidget(contextVP);
	final String mainBorderColor = Kune.getInstance().c.getMainBorder();
	contentBottomBar = new HorizontalPanel();
	final RoundedBorderDecorator contentToolBarBorderDec = new RoundedBorderDecorator(contentTitleBar,
		RoundedBorderDecorator.TOPLEFT);
	groupAreaVP.add(contentToolBarBorderDec);
	contentToolBarBorderDec.setColor(mainBorderColor);
	groupAreaVP.add(contentSubTitleBar);
	groupAreaVP.add(cntcxtHSP);
	final RoundedBorderDecorator bottomBorderDecorator = new RoundedBorderDecorator(contentBottomBar,
		RoundedBorderDecorator.BOTTOMLEFT);
	groupAreaVP.add(bottomBorderDecorator);
	bottomBorderDecorator.setColor(mainBorderColor);
	contentVP.addStyleName("kune-WorkspacePanel-Content");
	contextVP.addStyleName("kune-WorkspacePanel-Context");
	contentVP.setWidth("100%");
	contextVP.setWidth("100%");
	contextVP.setHeight("100%");

	// Set properties
	addStyleName("kune-WorkspacePanel");
	groupAreaVP.addStyleName("ContextPanel");
	generalHP.addStyleName("GeneralHP");
	contentTitleBar.setWidth("100%");
	contentSubTitleBar.setWidth("100%");
	contentBottomBar.addStyleName("kune-ContentBottomBar");
	groupAreaVP.setCellVerticalAlignment(contentTitleBar, VerticalPanel.ALIGN_MIDDLE);
	groupAreaVP.setCellVerticalAlignment(contentSubTitleBar, VerticalPanel.ALIGN_MIDDLE);
	groupAreaVP.setCellVerticalAlignment(bottomBorderDecorator, VerticalPanel.ALIGN_MIDDLE);
	contentTitleBar.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
	contentSubTitleBar.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
	generalDropDownsPanel.addStyleName("kune-GroupSummaryPanel");
	setLogo("");
    }

    public void addTab(final ToolTrigger trigger) {
	groupToolsBar.addItem(trigger);
    }

    public void setLogo(final String groupName) {
	logoPanel.setLogo(groupName);
    }

    public void setLogo(final Image image) {
	logoPanel.setLogo(image);
    }

    public void setTool(final String toolName) {
	groupToolsBar.selectItem(toolName);
    }

    public void setContent(final View content) {
	contentVP.clear();
	final Widget widget = (Widget) content;
	contentVP.add(widget);
	widget.setWidth("100%");
	contentVP.setCellWidth(widget, "100%");
    }

    public void setContext(final View contextMenu) {
	contextVP.clear();
	final Widget widget = (Widget) contextMenu;
	contextVP.add(widget);
	widget.setHeight("100%");
	widget.setWidth("100%");
	contextVP.setCellWidth(widget, "100%");
	contextVP.setCellHeight(widget, "100%");
    }

    public void adjustSize(final int windowWidth, final int windowHeight) {
	final int contentWidth = windowWidth - 163;
	final int contentHeight = windowHeight - 175;

	cntcxtHSP.setSize("" + contentWidth + "px", "" + contentHeight + "px");
	cntcxtHSP.setSplitPosition("" + (contentWidth - 175) + "px");
    }

    public void setContentTitle(final View view) {
	final Widget widget = (Widget) view;
	contentTitleBar.add((Widget) view);
	contentTitleBar.setCellVerticalAlignment(widget, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentSubTitle(final View view) {
	final Widget widget = (Widget) view;
	contentSubTitleBar.add((Widget) view);
	contentSubTitleBar.setCellVerticalAlignment(widget, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setBottom(final View view) {
	final Widget widget = (Widget) view;
	contentBottomBar.add(widget);
	contentBottomBar.setCellVerticalAlignment(widget, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setSocialNetwork(final View view) {
	AddDropDown(view, "00D4AA");
    }

    public void setBuddiesPresence(final View view) {
	AddDropDown(view, "87DECD");
    }

    private void AddDropDown(final View view, final String color) {
	final DropDownPanel panel = (DropDownPanel) view;
	generalDropDownsPanel.add(panel);
	panel.setWidth("145px");
	panel.setVisible(true);
	panel.setColor(color);
    }

}
