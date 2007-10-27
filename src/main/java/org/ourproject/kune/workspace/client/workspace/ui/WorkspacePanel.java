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
import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.workspace.client.license.ui.LicensePanel;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanelImages;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private static final Images img = Images.App.getInstance();
    private final GroupLogoPanel groupLogoPanel;
    private final HorizontalPanel contentTitleBarHP;
    private final HorizontalPanel contentSubTitleBarHP;
    private final GroupToolsBar groupToolsBar;
    private final HorizontalSplitPanel cntcxtHSP;
    private final VerticalPanel contextVP;
    private final VerticalPanel contentVP;
    private final HorizontalPanel contentBottomBarHP;
    private final VerticalPanel groupDropDownsVP;
    private final RoundedBorderDecorator contentTitleBarBorderDec;
    private final RoundedBorderDecorator bottomBorderDecorator;
    private DropDownPanel groupMembersPanel;
    private DropDownPanel participationPanel;
    private DropDownPanel buddiesPresencePanel;
    private final VerticalPanel cntcxtVP;
    private final ScrollPanel groupDropDownsSP;
    private final ColorTheme th;
    private final HorizontalPanel temporalSpaceForGroupOptions;
    private ContentTitlePanel contentTitlePanel;
    private ContentSubTitlePanel contentSubTitlePanel;
    private LicensePanel bottomPanel;
    private DropDownPanel tagsPanel;

    public WorkspacePanel() {
        th = Kune.getInstance().theme;
        // Initialize
        final VerticalPanel generalVP = new VerticalPanel();
        initWidget(generalVP);
        groupLogoPanel = new GroupLogoPanel();
        final HorizontalPanel generalHP = new HorizontalPanel();
        cntcxtVP = new VerticalPanel();
        final VerticalPanel groupNavBarVP = new VerticalPanel();
        groupToolsBar = new GroupToolsBar();
        temporalSpaceForGroupOptions = new HorizontalPanel();
        groupDropDownsSP = new ScrollPanel();
        groupDropDownsVP = new VerticalPanel();
        contentTitleBarHP = new HorizontalPanel();
        contentTitleBarBorderDec = new RoundedBorderDecorator(contentTitleBarHP, RoundedBorderDecorator.TOPLEFT);
        contentSubTitleBarHP = new HorizontalPanel();
        cntcxtHSP = new HorizontalSplitPanel(new HorizontalSplitPanelImages() {
            public AbstractImagePrototype horizontalSplitPanelThumb() {
                return img.splitterVertBar();
            }
        });
        contentVP = new VerticalPanel();
        contextVP = new VerticalPanel();
        contentBottomBarHP = new HorizontalPanel();
        bottomBorderDecorator = new RoundedBorderDecorator(contentBottomBarHP, RoundedBorderDecorator.BOTTOMLEFT);

        // Layout
        generalVP.add(groupLogoPanel);
        generalVP.add(generalHP);
        generalHP.add(cntcxtVP);
        generalHP.add(groupNavBarVP);
        groupNavBarVP.add(groupToolsBar);
        groupNavBarVP.add(temporalSpaceForGroupOptions);
        groupNavBarVP.add(groupDropDownsSP);
        groupDropDownsSP.add(groupDropDownsVP);
        cntcxtVP.add(contentTitleBarBorderDec);
        cntcxtVP.add(contentSubTitleBarHP);
        cntcxtVP.add(cntcxtHSP);
        cntcxtHSP.setLeftWidget(contentVP);
        cntcxtHSP.setRightWidget(contextVP);
        cntcxtVP.add(bottomBorderDecorator);

        contentVP.addStyleName("kune-WorkspacePanel-Content");
        contextVP.addStyleName("kune-WorkspacePanel-Context");
        contentVP.setWidth("100%");
        contextVP.setWidth("100%");
        contextVP.setHeight("100%");

        // Set properties
        addStyleName("kune-WorkspacePanel");
        setGroupLogo("");
        generalHP.addStyleName("GeneralHP");
        contentTitleBarBorderDec.setColor(th.getContentMainBorder());
        contentTitleBarHP.setWidth("100%");
        contentSubTitleBarHP.setWidth("100%");
        contentTitleBarHP.addStyleName("kune-ContentTitleBar");
        contentSubTitleBarHP.addStyleName("kune-ContentSubTitleBar");
        contentBottomBarHP.addStyleName("kune-ContentBottomBar");
        contentBottomBarHP.addStyleName("kune-ft12px");
        cntcxtVP.addStyleName("ContextPanel");
        cntcxtVP.setCellVerticalAlignment(contentTitleBarHP, VerticalPanel.ALIGN_MIDDLE);
        cntcxtVP.setCellVerticalAlignment(contentSubTitleBarHP, VerticalPanel.ALIGN_MIDDLE);
        cntcxtVP.setCellVerticalAlignment(bottomBorderDecorator, VerticalPanel.ALIGN_MIDDLE);
        contentTitleBarHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        contentSubTitleBarHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        groupDropDownsVP.addStyleName("kune-GroupSummaryPanel");
        bottomBorderDecorator.setColor(th.getContentMainBorder());
        temporalSpaceForGroupOptions.addStyleName("kune-Margin-Medium-l");
    }

    public void addTab(final ToolTrigger trigger) {
        groupToolsBar.addItem(trigger);
    }

    public void setGroupLogo(final String groupName) {
        groupLogoPanel.setLogo(groupName);
    }

    public void setGroupLogo(final Image image) {
        groupLogoPanel.setLogo(image);
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
        final int contentWidth = windowWidth - 163 - 21;
        final int contentHeight = windowHeight - 175;

        cntcxtHSP.setSize("" + contentWidth + "px", "" + contentHeight + "px");
        cntcxtHSP.setSplitPosition("" + (contentWidth - 175) + "px");
        groupDropDownsSP.setHeight("" + contentHeight + "px");
        // FireLog.debug("wsp.adjustSize w:" + contentWidth + "h:" +
        // contentHeight);
    }

    public void setContentTitle(final View view) {
        contentTitlePanel = (ContentTitlePanel) view;
        contentTitleBarHP.add(contentTitlePanel);
        contentTitleBarHP.setCellVerticalAlignment(contentTitlePanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentSubTitle(final View view) {
        contentSubTitlePanel = (ContentSubTitlePanel) view;
        contentSubTitleBarHP.add(contentSubTitlePanel);
        contentSubTitleBarHP.setCellVerticalAlignment(contentSubTitlePanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setBottom(final View view) {
        bottomPanel = (LicensePanel) view;
        contentBottomBarHP.add(bottomPanel);
        contentBottomBarHP.setCellVerticalAlignment(bottomPanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setGroupMembers(final View view) {
        groupMembersPanel = (DropDownPanel) view;
        AddDropDown(groupMembersPanel, th.getGroupMembersDD());
    }

    public void setParticipation(final View view) {
        participationPanel = (DropDownPanel) view;
        AddDropDown(participationPanel, th.getParticipationDD());
    }

    public void setBuddiesPresence(final View view) {
        buddiesPresencePanel = (DropDownPanel) view;
        AddDropDown(buddiesPresencePanel, th.getBuddiesPresenceDD());
    }

    public void setTags(final View view) {
        tagsPanel = (DropDownPanel) view;
        AddDropDown(tagsPanel, th.getTagsDD());
    }

    public void setThemeMenuComponent(final View view) {
        temporalSpaceForGroupOptions.add((Widget) view);
    }

    public void setTheme(final String theme) {
        th.setTheme(theme);
        String mainColor = th.getContentMainBorder();
        groupLogoPanel.setTextColor(th.getContentTitleText());
        contentTitleBarBorderDec.setColor(mainColor);
        bottomBorderDecorator.setColor(mainColor);
        DOM.setStyleAttribute(cntcxtVP.getElement(), "borderRightColor", mainColor);
        contentTitlePanel.setColors(th.getContentTitle(), th.getContentTitleText());
        contentSubTitlePanel.setColors(mainColor, th.getContentSubTitleText());
        DOM.setStyleAttribute(contentTitleBarHP.getElement(), "borderLeftColor", mainColor);
        DOM.setStyleAttribute(contentTitleBarHP.getElement(), "backgroundColor", th.getContentTitle());
        DOM.setStyleAttribute(contentSubTitleBarHP.getElement(), "backgroundColor", mainColor);
        DOM.setStyleAttribute(contentBottomBarHP.getElement(), "backgroundColor", mainColor);
        DOM.setStyleAttribute(cntcxtHSP.getRightWidget().getElement(), "backgroundColor", th.getContext());
        DOM.setStyleAttribute(DOM.getChild(DOM.getChild(cntcxtHSP.getElement(), 0), 1), "backgroundColor", th
                .getSplitter());
        DOM.setStyleAttribute(contentBottomBarHP.getWidget(0).getElement(), "color", th.getContentBottomText());
        groupMembersPanel.setColor(th.getGroupMembersDD());
        participationPanel.setColor(th.getParticipationDD());
        buddiesPresencePanel.setColor(th.getBuddiesPresenceDD());
        tagsPanel.setColor(th.getTagsDD());
        groupToolsBar.setTabsColors(th.getToolSelected(), th.getToolUnselected());
    }

    public void setVisible(final boolean visible) {
        // false: Used when the app stops
        super.setVisible(visible);
    }

    private void AddDropDown(final DropDownPanel panel, final String color) {
        groupDropDownsVP.add(panel);
        panel.setWidth("145px");
        panel.setColor(color);
    }

}
