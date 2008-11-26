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

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ContainerListener;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class EntitySummary {
    private static final String SITE_TRAYBAR = "k-site-traybar";
    private static final String ENTITY_SUMMARY = "k-entity-summary";
    private static final String ENTITY_TOOLS = "k-entity-tools";
    private final Panel entityTools;
    private final Panel entitySummary;
    private final Toolbar trayBar;
    private final Panel mainFitPanel;
    private final AccordionLayout accordionLayout;

    public EntitySummary() {
        mainFitPanel = new Panel();
        mainFitPanel.setLayout(new FitLayout());
        mainFitPanel.setBorder(false);
        mainFitPanel.setWidth(150);

        Panel anchorLayoutPanel = new Panel();
        anchorLayoutPanel.setLayout(new AnchorLayout());
        anchorLayoutPanel.setBorder(false);

        entityTools = new Panel();
        entityTools.setLayout(new AnchorLayout());
        entityTools.setBorder(false);
        entityTools.setAutoScroll(false);
        entityTools.setId(ENTITY_TOOLS);

        entitySummary = new Panel();
        entitySummary.setBorder(true);
        accordionLayout = new AccordionLayout(true);
        accordionLayout.setTitleCollapse(true);
        accordionLayout.setHideCollapseTool(true);
        // accordionLayout.setHideCollapseTool(true);
        // accordionLayout.setAnimate(true);
        // accordionLayout.setActiveOnTop(false);
        accordionLayout.setFill(true);
        accordionLayout.setRenderHidden(true);
        // entitySummary.setAutoScroll(true);
        entitySummary.setLayout(accordionLayout);
        entitySummary.setCls(ENTITY_SUMMARY);
        entitySummary.setId(ENTITY_SUMMARY);

        trayBar = new Toolbar();
        trayBar.setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        trayBar.setId(SITE_TRAYBAR);
        trayBar.setCls(SITE_TRAYBAR);
        trayBar.addFill();
        mainFitPanel.setBottomToolbar(trayBar);

        anchorLayoutPanel.add(entityTools, new AnchorLayoutData("100%"));
        // height: is 25 * number of tools :-( finding a way to make this
        // automatic
        anchorLayoutPanel.add(entitySummary, new AnchorLayoutData("100% -" + 5 * 25));
        mainFitPanel.add(anchorLayoutPanel);
    }

    public void addInSummary(final Panel panel) {
        // panel.addTool(new Tool(new Tool.ToolType("kmenu"), new Function() {
        // public void execute() {
        // Site.info("lalalal");
        // }
        // }, "Bla, bla, bla"));
        entitySummary.add(panel);
        if (entitySummary.isRendered()) {
            panel.doLayout(false);
            entitySummary.doLayout(false);
        }
        panel.addListener(new ContainerListenerAdapter() {
            @Override
            public void onHide(Component component) {
                super.onHide(component);
                openFirst();
            }

            @Override
            public void onShow(Component component) {
                super.onShow(component);
                openFirst();
            }

            private void openFirst() {
                if (accordionLayout.getActiveItem() != null && accordionLayout.getActiveItem().isHidden()) {
                    Panel firstComponent = null;
                    for (Component compo : entitySummary.getComponents()) {
                        if (compo.isVisible() && firstComponent == null) {
                            firstComponent = (Panel) compo;
                            firstComponent.expand(true);
                        }
                    }
                }
            }
        });
        doLayoutIfNeeded();
    }

    public void addInTools(final Widget widget) {
        entityTools.add(widget);
        if (entityTools.isRendered()) {
            entityTools.doLayout(false);
        }
        doLayoutIfNeeded();
    }

    public void addListener(final ContainerListener listener) {
        entitySummary.addListener(listener);
    }

    public void doLayoutIfNeeded() {
        if (mainFitPanel.isRendered()) {
            mainFitPanel.doLayout();
            entityTools.doLayout();
            entitySummary.syncSize();
            entitySummary.doLayout();
        }
    }

    public Panel getPanel() {
        return mainFitPanel;
    }

    public Toolbar getSiteTraybar() {
        return trayBar;
    }

}
