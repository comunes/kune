package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.FitLayout;

public class WorkspaceSkeleton {
    private final Entity entity;
    private final SiteBar sitebar;
    private final DefaultBorderLayout mainPanel;
    private final Panel container;
    private final ExtElement extRootBody;

    public WorkspaceSkeleton() {
	extRootBody = new ExtElement(RootPanel.getBodyElement());
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
	getEntityWorkspace().getContentTopBar().add(new Label("Sorry, kune workspace under heavy refactorization"));
	new Viewport(container);
    }

    public void addToEntityMainHeader(final Widget widget) {
	entity.addToEntityMainHeader(widget);
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

    public void mask() {
	extRootBody.mask();
    }

    public void mask(final String message) {
	extRootBody.mask(message, "x-mask-loading");
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	entity.setTheme(oldTheme, newTheme);
    }

    public void showAlertMessage(final String title, final String message) {
	MessageBox.alert(title, message, new MessageBox.AlertCallback() {
	    public void execute() {
	    }
	});
    }

    public void unMask() {
	extRootBody.unmask();
    }

}
