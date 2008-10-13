package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class WorkspaceSkeleton {
    public static final int DEF_TOOLBAR_HEIGHT = 26;
    private final Entity entity;
    private final SimpleToolbar sitebar;
    private final Panel mainPanel;
    private final Panel container;
    private final ExtElement extRootBody;

    public WorkspaceSkeleton() {
        extRootBody = new ExtElement(RootPanel.getBodyElement());

        container = new Panel();
        container.setLayout(new FitLayout());
        container.setBorder(false);
        container.setPaddings(5);

        mainPanel = new Panel();
        mainPanel.setLayout(new AnchorLayout());
        mainPanel.setBorder(false);

        sitebar = new SimpleToolbar();
        sitebar.setStyleName("k-sitebar");
        sitebar.setHeight("" + DEF_TOOLBAR_HEIGHT);

        entity = new Entity();

        mainPanel.add(sitebar, new AnchorLayoutData("100%"));
        mainPanel.add(entity.getPanel(), new AnchorLayoutData("100% -" + DEF_TOOLBAR_HEIGHT));
        container.add(mainPanel);
        new Viewport(container);
    }

    public void addInSummary(Panel panel) {
        entity.addInSummary(panel);
    }

    public void addInTools(final Widget widget) {
        entity.addInTools(widget);
    }

    public void addListenerInEntitySummary(ContainerListenerAdapter listener) {
        entity.addListenerInEntitySummary(listener);
    }

    public void addToEntityMainHeader(final Widget widget) {
        entity.addToEntityMainHeader(widget);
    }

    public void askConfirmation(final String title, final String message, final Listener0 onConfirmed,
            final Listener0 onCancel) {
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

    public EntityWorkspace getEntityWorkspace() {
        return entity.getEntityWorkspace();
    }

    public SimpleToolbar getSiteBar() {
        return sitebar;
    }

    public Toolbar getSiteTraybar() {
        return entity.getSiteTraybar();
    }

    public void mask() {
        extRootBody.mask();
    }

    public void mask(final String message) {
        extRootBody.mask(message, "x-mask-loading");
    }

    public void promptMessage(final String title, final String message, final Listener0 onEnter) {
        MessageBox.prompt(title, message, new MessageBox.PromptCallback() {
            public void execute(final String btnID, final String text) {
                // FIXME: use btnID
                onEnter.onEvent();
            }
        });
    }

    public void refreshSummary() {
        entity.refreshSummary();
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
