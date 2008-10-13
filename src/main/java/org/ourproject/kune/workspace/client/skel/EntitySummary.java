package org.ourproject.kune.workspace.client.skel;

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
        entitySummary.setBorder(false);
        accordionLayout = new AccordionLayout(true);
        accordionLayout.setTitleCollapse(true);
        // accordionLayout.setHideCollapseTool(true);
        // accordionLayout.setAnimate(true);
        // accordionLayout.setActiveOnTop(false);
        accordionLayout.setFill(true);
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
        anchorLayoutPanel.add(entitySummary, new AnchorLayoutData("100% -50"));
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
            mainFitPanel.doLayout(false);
            entitySummary.doLayout(false);
        }
    }

    public Panel getPanel() {
        return mainFitPanel;
    }

    public Toolbar getSiteTraybar() {
        return trayBar;
    }

}
