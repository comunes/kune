package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class Entity {

    private final Panel entityFitPanel;
    private final EntityWorkspace entityWorkspace;
    private final EntitySummary entitySummary;
    private final Panel entityMainHeader;

    public Entity() {
        entityFitPanel = new Panel();
        entityFitPanel.setLayout(new FitLayout());
        entityFitPanel.setBorder(false);

        Panel entityAnchorLayout = new Panel();
        entityAnchorLayout.setBorder(false);
        entityAnchorLayout.setLayout(new AnchorLayout());

        Panel entityBorderLayout = new Panel();
        entityBorderLayout.setLayout(new BorderLayout());
        entityBorderLayout.setBorder(false);

        entityMainHeader = new Panel();
        entityMainHeader.setBorder(false);
        entityMainHeader.setHeight(65);

        entityWorkspace = new EntityWorkspace();

        BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);
        eastData.setMinSize(50);
        eastData.setSplit(true);
        eastData.setCollapseModeMini(true);
        eastData.setUseSplitTips(true);

        entitySummary = new EntitySummary();
        entityBorderLayout.add(entityWorkspace.getPanel(), new BorderLayoutData(RegionPosition.CENTER));
        entityBorderLayout.add(entitySummary.getPanel(), eastData);

        entityAnchorLayout.add(entityMainHeader, new AnchorLayoutData("100%"));
        entityAnchorLayout.add(entityBorderLayout, new AnchorLayoutData("100% -65"));
        entityFitPanel.add(entityAnchorLayout);
    }

    public void addInSummary(Widget widget) {
        entitySummary.addInSummary(widget);
        doLayoutIfNeeded();
    }

    public void addInTools(final Widget widget) {
        entitySummary.addInTools(widget);
        doLayoutIfNeeded();
    }

    public void addListenerInEntitySummary(ContainerListenerAdapter listener) {
        entitySummary.addListener(listener);
    }

    public void addToEntityMainHeader(final Widget widget) {
        entityMainHeader.clear();
        entityMainHeader.add(widget);
        doLayoutIfNeeded();
    }

    public EntityWorkspace getEntityWorkspace() {
        return entityWorkspace;
    }

    public Panel getPanel() {
        return entityFitPanel;
    }

    public Toolbar getSiteTraybar() {
        return entitySummary.getSiteTraybar();
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        if (oldTheme != null) {
            entityFitPanel.removeClass("k-entity-" + oldTheme);
        }
        entityFitPanel.addClass("k-entity-" + newTheme);
        entityWorkspace.setTheme(oldTheme, newTheme);
    }

    private void doLayoutIfNeeded() {
        if (entityFitPanel.isRendered()) {
            entityFitPanel.doLayout();
        }
    }
}
