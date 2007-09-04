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
import org.ourproject.kune.platf.client.ui.BorderDecorator;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private final LogoPanel logoPanel;
    private final ContentTitleBar contentTitleBar;
    private final GroupToolsBar groupToolsBar;
    private final HorizontalSplitPanel cntcxtHSP;
    private final VerticalPanel contextVP;
    private final VerticalPanel contentVP;
    private ContentBottomBar contentBottomBar;

    public WorkspacePanel() {
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	logoPanel = new LogoPanel();
	HorizontalPanel generalHP = new HorizontalPanel();

	generalVP.add(logoPanel);
	generalVP.add(generalHP);

	VerticalPanel groupAreaVP = new VerticalPanel();
	VerticalPanel groupNavBarVP = new VerticalPanel();
	generalHP.add(groupAreaVP);
	generalHP.add(groupNavBarVP);

	groupToolsBar = new GroupToolsBar();
	SummaryPanel summaryPanel = new SummaryPanel();
	groupNavBarVP.add(groupToolsBar);
	groupNavBarVP.add(summaryPanel);

	summaryExample(summaryPanel);

	ContentToolBar contentToolBar = new ContentToolBar();
	contentTitleBar = new ContentTitleBar();
	cntcxtHSP = new HorizontalSplitPanel();
	contentVP = new VerticalPanel();
	contextVP = new VerticalPanel();
	cntcxtHSP.setLeftWidget(contentVP);
	cntcxtHSP.setRightWidget(contextVP);
	String mainBorderColor = Kune.getInstance().c.getMainBorder();
	contentBottomBar = new ContentBottomBar();
	BorderDecorator contentToolBarBorderDec = new BorderDecorator(contentToolBar, BorderDecorator.TOPLEFT);
	groupAreaVP.add(contentToolBarBorderDec);
	contentToolBarBorderDec.setColor(mainBorderColor);
	groupAreaVP.add(contentTitleBar);
	groupAreaVP.add(cntcxtHSP);
	BorderDecorator borderDecorator = new BorderDecorator(contentBottomBar, BorderDecorator.BOTTOMLEFT);
	groupAreaVP.add(borderDecorator);
	borderDecorator.setColor(mainBorderColor);
	contentVP.addStyleName("kune-WorkspacePanel-Content");
	contextVP.addStyleName("kune-WorkspacePanel-Context");
	contentVP.setWidth("100%");
	contextVP.setWidth("100%");
	contextVP.setHeight("100%");

	// Set properties
	addStyleName("kune-WorkspacePanel");
	groupAreaVP.addStyleName("ContextPanel");
	generalHP.addStyleName("GeneralHP");
	setLogo("");
    }

    private void summaryExample(final SummaryPanel summaryPanel) {
	DropDownPanel presencePanel = new DropDownPanel();
	summaryPanel.add(presencePanel);
	presencePanel.setWidth("100%");
	presencePanel.setContent(new Label(""));
	presencePanel.setContentVisible(true);
	presencePanel.setTitle("Buddies");
	presencePanel.setColor("green");
	presencePanel.setContent(new Label(""));
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

    public void setContentTitle(final String title) {
	contentTitleBar.setTitleLabel(title);
    }

    public void setTool(final String toolName) {
	groupToolsBar.selectItem(toolName);
    }

    public void setContent(final View content) {
	contentVP.clear();
	Widget widget = (Widget) content;
	contentVP.add(widget);
	widget.setWidth("100%");
	contentVP.setCellWidth(widget, "100%");
    }

    public void setContext(final View contextMenu) {
	contextVP.clear();
	Widget widget = (Widget) contextMenu;
	contextVP.add(widget);
	widget.setHeight("100%");
	widget.setWidth("100%");
	contextVP.setCellWidth(widget, "100%");
	contextVP.setCellHeight(widget, "100%");
    }

    public void adjustSize(final int windowWidth, final int windowHeight) {
	int contentWidth = windowWidth - 163; // 150
	int contentHeight = windowHeight - 175;

	cntcxtHSP.setSize("" + contentWidth + "px", "" + contentHeight + "px");
	cntcxtHSP.setSplitPosition("" + (contentWidth - 175) + "px");
    }

    public void setBottom(View view) {
	contentBottomBar.add((Widget) view);
    }

}
