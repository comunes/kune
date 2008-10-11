package org.ourproject.kune.workspace.client.skel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ContainerListener;
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

    public EntitySummary() {
        mainFitPanel = new Panel("");
        mainFitPanel.setLayout(new FitLayout());
        mainFitPanel.setBorder(false);
        mainFitPanel.setWidth(150);

        Panel anchorLayoutPanel = new Panel();
        anchorLayoutPanel.setLayout(new AnchorLayout());
        anchorLayoutPanel.setBorder(false);
        anchorLayoutPanel.setWidth(150);
        entityTools = new Panel();
        entityTools.setLayout(new AnchorLayout());
        entityTools.setId(ENTITY_TOOLS);
        entityTools.setAutoHeight(true);
        entitySummary = new Panel();
        entityTools.setBorder(false);
        entitySummary.setBorder(false);
        entityTools.setAutoScroll(false);
        entitySummary.setAutoScroll(true);
        entitySummary.setCls(ENTITY_SUMMARY);
        entitySummary.setId(ENTITY_SUMMARY);

        trayBar = new Toolbar();
        trayBar.setHeight(WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT);
        trayBar.setId(SITE_TRAYBAR);
        trayBar.setCls(SITE_TRAYBAR);
        trayBar.addFill();
        entitySummary.setBottomToolbar(trayBar);
        anchorLayoutPanel.add(entityTools, new AnchorLayoutData("100%"));
        anchorLayoutPanel.add(entitySummary, new AnchorLayoutData("100% -50"));
        mainFitPanel.add(anchorLayoutPanel);
    }

    public void addInSummary(final Widget widget) {
        entitySummary.add(widget);
        doLayoutIfNeeded();
    }

    public void addInTools(final Widget widget) {
        entityTools.add(widget);
        doLayoutIfNeeded();
    }

    public void addListener(final ContainerListener listener) {
        entitySummary.addListener(listener);
    }

    public Panel getPanel() {
        return mainFitPanel;
    }

    public Toolbar getSiteTraybar() {
        return trayBar;
    }

    private Panel createAccordionPanel() {
        Panel accordionPanel = new Panel();
        accordionPanel.setBorder(false);
        accordionPanel.setLayout(new AccordionLayout(true));

        Panel panelOne = new Panel("Panel 1", "<p>Panel1 content!</p>");
        panelOne.setIconCls("settings-icon");
        accordionPanel.add(panelOne);

        Panel panelTwo = new Panel("Panel 2", "<p>Panel2 content!</p>");
        panelTwo.setIconCls("folder-icon");
        accordionPanel.add(panelTwo);

        Panel panelThree = new Panel("Panel 3", "<p>Panel3 content!</p>");
        panelThree.setIconCls("user-add-icon");
        accordionPanel.add(panelThree);

        // accordionPanel.setCls(ENTITY_SUMMARY);

        return accordionPanel;
    }

    private void doLayoutIfNeeded() {
        if (mainFitPanel.isRendered()) {
            mainFitPanel.doLayout();
        }
    }

}
