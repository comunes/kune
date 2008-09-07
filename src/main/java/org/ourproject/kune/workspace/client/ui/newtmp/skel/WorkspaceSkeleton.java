package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.calclab.suco.client.signal.Slot0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
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
	new Viewport(container);
    }

    public void addToEntityMainHeader(final Widget widget) {
	entity.addToEntityMainHeader(widget);
    }

    public void askConfirmation(final String title, final String message, final Slot0 onConfirmed, final Slot0 onCancel) {
	MessageBox.confirm(title, message, new MessageBox.ConfirmCallback() {
	    public void execute(final String btnID) {
		if (btnID.equals("yes")) {
		    DeferredCommand.addCommand(new Command() {
			public void execute() {
			    onConfirmed.onEvent();
			}
		    });
		} else {
		    onCancel.onEvent();
		}
	    }
	});
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

    public void promptMessage(final String title, final String message, final Slot0 onEnter) {
	MessageBox.prompt(title, message, new MessageBox.PromptCallback() {
	    public void execute(final String btnID, final String text) {
		// FIXME: use btnID
		onEnter.onEvent();
	    }
	});
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
