package org.ourproject.kune.workspace.client.sitebar.siteoptions;

import org.ourproject.kune.platf.client.actions.ui.AbstractComposedGuiItem;
import org.ourproject.kune.platf.client.actions.ui.AbstractGuiItem;
import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;

public abstract class AbstractSiteOptionsPanel extends AbstractComposedGuiItem {

    protected final PushButton btn;
    private MenuDescriptor menuDescriptor;

    public AbstractSiteOptionsPanel(final GuiBindingsRegister bindings, final String id) {
        super(bindings);
        btn = new PushButton("");
        btn.ensureDebugId(id);
        btn.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                menuDescriptor.show(id);
            }
        });
        btn.setStyleName("k-sitebar-labellink");
    }

    public void setBtnText(final String title) {
        btn.setHTML(title + "<img style=\"vertical-align: middle;\" src=\"images/arrowdown.png\" />");
    }

    public void setMenu(final MenuDescriptor menuDescriptor) {
        this.menuDescriptor = menuDescriptor;
    }

    @Override
    protected void add(final AbstractGuiItem item) { // NOPMD by vjrj on
        // 14/06/09 0:15
        // Do nothing (menu items are attached automatically to its menu
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) { // NOPMD
        // by
        // vjrj
        // on
        // 14/06/09
        // 0:15
        // Do nothing (menu items are attached automatically to its menu
    }
}
