package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.FitLayout;

public class WorkspaceSkeleton {
    private final Entity entity;
    private final SiteBar sitebar;
    private final DefaultBorderLayout mainPanel;
    private final Panel container;

    public WorkspaceSkeleton() {
	container = new Panel();
	container.setLayout(new FitLayout());
	container.setBorder(false);
	container.setPaddings(5);
	mainPanel = new DefaultBorderLayout();
	sitebar = new SiteBar();
	entity = new Entity();
	mainPanel.add(sitebar, DefaultBorderLayout.Position.NORTH, DefaultBorderLayout.DEF_TOOLBAR_HEIGHT);
	mainPanel.add(entity.getPanel(), DefaultBorderLayout.Position.CENTER);
	container.add(mainPanel.getPanel());
    }

    public Panel getEntityMainHeader() {
	return entity.getEntityMainHeader();
    }

    public EntitySummary getEntitySummary() {
	return entity.getEntitySummary();
    }

    public EntityWorkspace getEntityWorkspace() {
	return entity.getEntityWorkspace();
    }

    public SiteBar getSiteBar() {
	return sitebar;
    }

    public Toolbar getSiteTraybar() {
	return entity.getEntitySummary().getSiteTraybar();
    }

    public void setTheme(final WsTheme theme) {
	entity.setTheme(theme);
    }

    public void show() {
	new Viewport(container);
    }

}
