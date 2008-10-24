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
 */package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.platf.client.ui.RoundedPanel;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ContainerListener;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class EntityWorkspace {
    private static final String ENTITY_TITLE = "k-entity-title";
    private static final String ENTITY_SUBTITLE = "k-entity-subtitle";
    private static final String ENTITY_BOTTOM = "k-entity-bottom";
    private static final String ENTITY_CONTENT = "k-entity-content";
    private static final String ENTITY_CONTEXT = "k-entity-context";
    private final Panel content;
    private final Panel context;
    private final SimpleToolbar title;
    private final SimpleToolbar subTitle;
    private final SimpleToolbar bottom;
    private final Toolbar contentTopBar;
    private final Toolbar contentBottomBar;
    private final Toolbar contextTopBar;
    private final Toolbar contextBottomBar;
    private final RoundedPanel roundedTitle;
    private final RoundedPanel roundedBottom;
    private final Panel cntCtxBorderLayout;
    private final Panel mainFitPanel;

    public EntityWorkspace() {
        mainFitPanel = new Panel();
        mainFitPanel.setLayout(new FitLayout());
        mainFitPanel.setBorder(false);

        Panel mainAnchorLayout = new Panel();
        mainAnchorLayout.setLayout(new AnchorLayout());
        mainAnchorLayout.setBorder(false);

        cntCtxBorderLayout = new Panel();
        cntCtxBorderLayout.setLayout(new BorderLayout());
        cntCtxBorderLayout.setBorder(false);
        final Panel titles = new Panel();
        titles.setBorder(false);
        titles.setLayout(new AnchorLayout());
        final Panel bottomPanel = new Panel();
        bottomPanel.setBorder(false);
        bottomPanel.setLayout(new AnchorLayout());

        title = new SimpleToolbar();
        title.setHeight("" + (WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT - 2));
        title.setStylePrimaryName(ENTITY_TITLE);
        // title.ensureDebugId(ENTITY_TITLE);
        subTitle = new SimpleToolbar();
        subTitle.setStylePrimaryName(ENTITY_SUBTITLE);
        // subTitle.ensureDebugId(ENTITY_SUBTITLE);
        bottom = new SimpleToolbar();
        bottom.setHeight("" + (WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT - 2));
        bottom.setStylePrimaryName(ENTITY_BOTTOM);
        bottom.ensureDebugId(ENTITY_BOTTOM);

        roundedTitle = new RoundedPanel(title, RoundedPanel.TOPLEFT, 2);
        roundedBottom = new RoundedPanel(bottom, RoundedPanel.BOTTOMLEFT, 2);

        titles.add(roundedTitle, new AnchorLayoutData("100% -" + WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT));
        titles.add(subTitle, new AnchorLayoutData("100% -" + WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT));
        bottomPanel.add(roundedBottom, new AnchorLayoutData("100% -" + WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT));
        titles.setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT * 2);
        bottomPanel.setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT + 2);

        final Panel contentWrap = new Panel();
        final Panel contextWrap = new Panel();
        contentWrap.setLayout(new BorderLayout());
        contextWrap.setLayout(new BorderLayout());
        contentWrap.setBorder(true);
        contextWrap.setBorder(true);
        content = new Panel();
        context = new Panel();
        content.setCls(ENTITY_CONTENT);
        context.setCls(ENTITY_CONTEXT);
        context.setLayout(new FitLayout());
        content.setBorder(false);
        context.setBorder(false);
        context.setCollapsible(true);
        content.setAutoScroll(true);

        contentTopBar = new Toolbar();
        contentBottomBar = new Toolbar();
        contextTopBar = new Toolbar();
        contextBottomBar = new Toolbar();
        contentTopBar.getPanel().setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        contextTopBar.getPanel().setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        contentBottomBar.getPanel().setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        contentBottomBar.getPanel().setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        contentTopBar.addStyleName("k-toolbar-bottom-line");
        contentBottomBar.addStyleName("k-toolbar-top-line");
        contextTopBar.addStyleName("k-toolbar-bottom-line");
        contextBottomBar.addStyleName("k-toolbar-top-line");

        contentWrap.add(contentTopBar.getPanel(), new BorderLayoutData(RegionPosition.NORTH));
        contextWrap.add(contextTopBar.getPanel(), new BorderLayoutData(RegionPosition.NORTH));
        contentWrap.add(content, new BorderLayoutData(RegionPosition.CENTER));
        contextWrap.add(context, new BorderLayoutData(RegionPosition.CENTER));
        contentWrap.add(contentBottomBar.getPanel(), new BorderLayoutData(RegionPosition.SOUTH));
        contextWrap.add(contextBottomBar.getPanel(), new BorderLayoutData(RegionPosition.SOUTH));

        BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);
        contextWrap.setWidth(175);
        eastData.setUseSplitTips(true);
        eastData.setCollapseModeMini(true);
        cntCtxBorderLayout.add(contentWrap, new BorderLayoutData(RegionPosition.CENTER));
        cntCtxBorderLayout.add(contextWrap, eastData);

        mainAnchorLayout.add(titles, new AnchorLayoutData("100%"));
        mainAnchorLayout.add(cntCtxBorderLayout, new AnchorLayoutData("100% -"
                + ((WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT * 3) + 2)));
        mainAnchorLayout.add(bottomPanel, new AnchorLayoutData("100%"));
        mainFitPanel.add(mainAnchorLayout);
    }

    public void addContentListener(final ContainerListener listener) {
        content.addListener(listener);
    }

    public void addContextListener(final ContainerListener listener) {
        context.addListener(listener);
    }

    public SimpleToolbar getBottomTitle() {
        return bottom;
    }

    public Toolbar getContentBottomBar() {
        return contentBottomBar;
    }

    public Toolbar getContentTopBar() {
        return contentTopBar;
    }

    public Toolbar getContextBottomBar() {
        return contextBottomBar;
    }

    public Toolbar getContextTopBar() {
        return contextTopBar;
    }

    public Panel getPanel() {
        return mainFitPanel;
    }

    public SimpleToolbar getSubTitle() {
        return subTitle;
    }

    public SimpleToolbar getTitleComponent() {
        return title;
    }

    public void setContent(final Widget widget) {
        setPanel(content, widget);
    }

    public void setContext(final Widget widget) {
        setPanel(context, widget);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        final String themeS = newTheme.toString();
        if (oldTheme != null) {
            final String previousThemeS = oldTheme.toString();
            title.removeStyleDependentName(previousThemeS);
            subTitle.removeStyleDependentName(previousThemeS);
            bottom.removeStyleDependentName(previousThemeS);
            cntCtxBorderLayout.removeClass("k-entityworkspace-" + previousThemeS);
            context.removeStyleName("k-entity-context-" + previousThemeS);
        }
        cntCtxBorderLayout.addClass("k-entityworkspace-" + newTheme);
        roundedTitle.setCornerStyleName("k-entity-title-rd-" + newTheme);
        roundedBottom.setCornerStyleName("k-entity-bottom-rd-" + newTheme);
        title.addStyleDependentName(themeS);
        subTitle.addStyleDependentName(themeS);
        bottom.addStyleDependentName(themeS);
        context.addStyleName("k-entity-context-" + newTheme);
    }

    private void setPanel(Panel panel, Widget widget) {
        panel.clear();
        panel.add(widget);
        if (panel.isRendered()) {
            panel.doLayout(false);
        }
    }
}
