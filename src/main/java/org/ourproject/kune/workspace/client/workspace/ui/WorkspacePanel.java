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

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.platf.client.ui.SplitterListener;
import org.ourproject.kune.platf.client.ui.gwtcustom.CustomHorizontalSplitPanel;
import org.ourproject.kune.workspace.client.licensefoot.ui.LicensePanel;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspaceUIComponents;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanelImages;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private static final int DEF_CONTEXT_WIDTH = 175;
    private static final Images img = Images.App.getInstance();

    private final GroupLogoPanel groupLogoPanel;
    private final HorizontalPanel contentTitleBarHP;
    private final HorizontalPanel contentSubTitleBarHP;
    private final GroupToolsBar groupToolsBar;
    private final CustomHorizontalSplitPanel cntcxtHSP;
    private final VerticalPanel contextVP;
    private final VerticalPanel contentVP;
    private final HorizontalPanel contentBottomBarHP;
    private final VerticalPanel groupDropDownsVP;
    private final RoundedBorderDecorator contentTitleBarBorderDec;
    private final RoundedBorderDecorator bottomBorderDecorator;
    private DropDownPanel groupMembersPanel;
    private DropDownPanel participationPanel;
    private DropDownPanel groupSummaryPanel;
    private final VerticalPanel cntcxtVP;
    private final ScrollPanel groupDropDownsSP;
    private final ColorTheme th;
    private final BottomIconsTrayPanel bottomIconsTrayPanel;
    private ContentTitlePanel contentTitlePanel;
    private ContentSubTitlePanel contentSubTitlePanel;
    private ContentToolBarPanel contentToolBarPanel;
    private final HorizontalPanel contentToolBarHP;
    private final ScrollPanel contentSP;
    private final HorizontalPanel contentBottomToolBarHP;
    private ContentBottomToolBarPanel contentBottomToolBarPanel;
    private LicensePanel bottomPanel;
    private DropDownPanel tagsPanel;

    private int previousRightWidgetWidth;
    private final WorkspacePresenter presenter;

    public WorkspacePanel(final WorkspacePresenter presenter) {
        this.presenter = presenter;
        th = Kune.getInstance().theme;
        // Initialize
        final VerticalPanel generalVP = new VerticalPanel();
        initWidget(generalVP);
        groupLogoPanel = new GroupLogoPanel();
        final HorizontalPanel generalHP = new HorizontalPanel();
        cntcxtVP = new VerticalPanel();
        final VerticalPanel groupNavBarVP = new VerticalPanel();
        groupToolsBar = new GroupToolsBar();
        bottomIconsTrayPanel = new BottomIconsTrayPanel();
        groupDropDownsSP = new ScrollPanel();
        groupDropDownsVP = new VerticalPanel();
        contentTitleBarHP = new HorizontalPanel();
        contentTitleBarBorderDec = new RoundedBorderDecorator(contentTitleBarHP, RoundedBorderDecorator.TOPLEFT);
        contentSubTitleBarHP = new HorizontalPanel();
        contentToolBarHP = new HorizontalPanel();
        cntcxtHSP = new CustomHorizontalSplitPanel(new HorizontalSplitPanelImages() {
            public AbstractImagePrototype horizontalSplitPanelThumb() {
                return img.splitterVertBar();
            }
        });
        cntcxtHSP.addChangeListener(new ChangeListener() {
            public void onChange(final Widget sender) {
                adjustSizeContentSP();
                saveCurrentRightWidgetWidth(cntcxtHSP.getRightWidgetAvailableWidth());
            }
        });
        cntcxtHSP.addSplitterListener(new SplitterListener() {
            public void onStartResizing(final Widget sender) {
                presenter.onSplitterStartResizing(sender);
            }

            public void onStopResizing(final Widget sender) {
                presenter.onSplitterStopResizing(sender);
            }
        });
        contentVP = new VerticalPanel();
        contextVP = new VerticalPanel();
        contentSP = new ScrollPanel();
        contentBottomToolBarHP = new HorizontalPanel();
        contentBottomBarHP = new HorizontalPanel();
        bottomBorderDecorator = new RoundedBorderDecorator(contentBottomBarHP, RoundedBorderDecorator.BOTTOMLEFT);

        // Layout
        generalVP.add(groupLogoPanel);
        generalVP.add(generalHP);
        generalHP.add(cntcxtVP);
        generalHP.add(groupNavBarVP);
        groupNavBarVP.add(groupToolsBar);
        groupNavBarVP.add(groupDropDownsSP);
        groupNavBarVP.add(bottomIconsTrayPanel);
        groupDropDownsSP.add(groupDropDownsVP);
        cntcxtVP.add(contentTitleBarBorderDec);
        cntcxtVP.add(contentSubTitleBarHP);
        cntcxtVP.add(cntcxtHSP);
        cntcxtHSP.setLeftWidget(contentVP);
        cntcxtHSP.setRightWidget(contextVP);
        cntcxtVP.add(bottomBorderDecorator);
        contentVP.add(contentToolBarHP);
        contentVP.add(contentSP);
        contentVP.add(contentBottomToolBarHP);

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
        contentToolBarHP.setWidth("100%");
        cntcxtVP.addStyleName("ContextPanel");
        cntcxtVP.setCellVerticalAlignment(contentTitleBarHP, VerticalPanel.ALIGN_MIDDLE);
        cntcxtVP.setCellVerticalAlignment(contentSubTitleBarHP, VerticalPanel.ALIGN_MIDDLE);
        cntcxtVP.setCellVerticalAlignment(bottomBorderDecorator, VerticalPanel.ALIGN_MIDDLE);
        contentTitleBarHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        contentSubTitleBarHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        contentBottomToolBarHP.setWidth("100%");
        contentBottomBarHP.addStyleName("kune-ContentBottomBar");
        contentBottomBarHP.addStyleName("kune-ft12px");
        groupDropDownsVP.addStyleName("kune-GroupSummaryPanel");
        bottomBorderDecorator.setColor(th.getContentMainBorder());
        bottomIconsTrayPanel.addStyleName("kune-Margin-Medium-l");
        bottomIconsTrayPanel.addStyleName("kune-BottomIconsTrayPanel");
        bottomIconsTrayPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        bottomIconsTrayPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        previousRightWidgetWidth = DEF_CONTEXT_WIDTH;
    }

    public void addBottomIconComponent(final View view) {
        bottomIconsTrayPanel.add((Widget) view);
    }

    public void addTab(final ToolTrigger trigger) {
        groupToolsBar.addItem(trigger);
    }

    public void adjustSize(final int windowWidth, final int windowHeight) {
        final int contentWidth = windowWidth - 184;
        final int contentHeight = windowHeight - 176;

        // FireLog.debug("w1: " + windowWidth + ", h1: " + windowHeight);
        // FireLog.debug("w2: " + contentWidth + ", h2: " + contentHeight);
        // FireLog.debug("sp1: " + previousRightWidgetWidth);
        cntcxtHSP.setSize("" + contentWidth + "px", "" + contentHeight + "px");
        if (contentWidth > previousRightWidgetWidth) {
            cntcxtHSP.setSplitPosition("" + (contentWidth - previousRightWidgetWidth - 6) + "px");
            saveCurrentRightWidgetWidth(previousRightWidgetWidth);
        } else {
            setDefaultSplitterPosition();
        }
        adjustSizeContentSP();

        final int bottomMarginForTray = 35;
        final int distanceFromTop = 89;
        final int groupDropDownsHeight = windowHeight - groupToolsBar.getOffsetHeight() - distanceFromTop
                - bottomMarginForTray;
        groupDropDownsSP.setHeight("" + groupDropDownsHeight + "px");
    }

    public void registerUIExtensionPoints() {
        presenter.registerUIExtensionPoints(contentToolBarPanel.getExtensionPoints());
        presenter.registerUIExtensionPoint(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, bottomIconsTrayPanel);
    }

    public void setBottom(final View view) {
        bottomPanel = (LicensePanel) view;
        contentBottomBarHP.add(bottomPanel);
        contentBottomBarHP.setCellVerticalAlignment(bottomPanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setComponents(final WorkspaceUIComponents components) {
        setContentTitle(components.getContentTitleComponent().getView());
        setContentSubTitle(components.getContentSubTitleComponent().getView());
        setContentToolBar(components.getContentToolBarComponent().getView());
        setContentBottomToolBar(components.getContentBottomToolBarComponent().getView());
        setBottom(components.getLicenseComponent().getView());
        setGroupMembers(components.getGroupMembersComponent().getView());
        setParticipation(components.getParticipationComponent().getView());
        setTags(components.getTagsComponent().getView());
        setSummary(components.getGroupSummaryComponent().getView());
        addBottomIconComponent(components.getThemeMenuComponent().getView());
    }

    public void setContent(final View content) {
        contentSP.clear();
        final Widget widget = (Widget) content;
        contentSP.add(widget);
    }

    public void setContentBottomToolBar(final View view) {
        contentBottomToolBarPanel = (ContentBottomToolBarPanel) view;
        contentBottomToolBarHP.add(contentBottomToolBarPanel);
        contentBottomToolBarHP.setCellVerticalAlignment(contentBottomToolBarPanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentSubTitle(final View view) {
        contentSubTitlePanel = (ContentSubTitlePanel) view;
        contentSubTitleBarHP.add(contentSubTitlePanel);
        contentSubTitleBarHP.setCellVerticalAlignment(contentSubTitlePanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentTitle(final View view) {
        contentTitlePanel = (ContentTitlePanel) view;
        contentTitleBarHP.add(contentTitlePanel);
        contentTitleBarHP.setCellVerticalAlignment(contentTitlePanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentToolBar(final View view) {
        contentToolBarPanel = (ContentToolBarPanel) view;
        contentToolBarHP.add(contentToolBarPanel);
        contentToolBarHP.setCellVerticalAlignment(contentToolBarPanel, VerticalPanel.ALIGN_MIDDLE);
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

    public void setGroupLogo(final Image image) {
        groupLogoPanel.setLogo(image);
    }

    public void setGroupLogo(final String groupName) {
        groupLogoPanel.setLogo(groupName);
    }

    public void setGroupMembers(final View view) {
        groupMembersPanel = (DropDownPanel) view;
        AddDropDown(groupMembersPanel, th.getGroupMembersDD());
    }

    public void setParticipation(final View view) {
        participationPanel = (DropDownPanel) view;
        AddDropDown(participationPanel, th.getParticipationDD());
    }

    public void setPutYourLogoVisible(final boolean visible) {
        groupLogoPanel.setPutYourLogoVisible(visible);
    }

    public void setSummary(final View view) {
        groupSummaryPanel = (DropDownPanel) view;
        AddDropDown(groupSummaryPanel, th.getSummaryDD());
    }

    public void setTags(final View view) {
        tagsPanel = (DropDownPanel) view;
        AddDropDown(tagsPanel, th.getTagsDD());
    }

    public void setTheme(final String theme) {
        th.setTheme(theme);
        final String mainColor = th.getContentMainBorder();
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
        groupSummaryPanel.setColor(th.getSummaryDD());
        tagsPanel.setColor(th.getTagsDD());
        groupToolsBar.setTabsColors(th.getToolSelected(), th.getToolUnselected());
    }

    public void setTool(final String toolName) {
        groupToolsBar.selectItem(toolName);
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

    private void adjustSizeContentSP() {
        final int width = cntcxtHSP.getLeftWidgetAvailableWidth() - 2;
        final int height = cntcxtHSP.getOffsetHeight() - 29 - contentToolBarHP.getOffsetHeight();
        if (width > 0 && height > 0) {
            contentSP.setSize("" + width + "px", "" + height + "px");
        } else {
            Log.debug("Can't resize ScrollPanel now");
        }
    }

    private void saveCurrentRightWidgetWidth(final int newRightWidgetWidth) {
        previousRightWidgetWidth = newRightWidgetWidth;
    }

    private void setDefaultSplitterPosition() {
        final int newLefttWidgetWidth = (int) (cntcxtHSP.getOffsetWidth() * 0.7);
        cntcxtHSP.setSplitPosition(newLefttWidgetWidth + "px");
        saveCurrentRightWidgetWidth(cntcxtHSP.getLeftWidgetAvailableWidth());
    }

    // private void logSplitter(final String count) {
    // FireLog.debug("" + count + " cntcxtHSP.getRightWidgetAvailableWidth: "
    // + cntcxtHSP.getRightWidgetAvailableWidth());
    // FireLog
    // .debug("" + count + " cntcxtHSP.getLeftWidgetAvailableWidth: "
    // + cntcxtHSP.getLeftWidgetAvailableWidth());
    // }

}
