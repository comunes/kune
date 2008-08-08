package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.FitLayout;

public class Entity extends DefaultBorderLayout {

    private final EntityWorkspace entityWorkspace;
    private final EntitySummary entitySummary;
    private final Panel entityMainHeader;

    public Entity() {
	entityMainHeader = new Panel();
	entityMainHeader.setBorder(false);
	entityMainHeader.setLayout(new FitLayout());
	entitySummary = new EntitySummary();
	entityWorkspace = new EntityWorkspace();
	add(entityMainHeader, DefaultBorderLayout.Position.NORTH, 65);
	add(entityWorkspace.getPanel(), DefaultBorderLayout.Position.CENTER);
	add(entitySummary.getPanel(), DefaultBorderLayout.Position.EAST, true, 150);
    }

    public void addToEntityMainHeader(final Widget widget) {
	entityMainHeader.add(widget);
	doLayoutIfNeeded();
    }

    public EntitySummary getEntitySummary() {
	return entitySummary;
    }

    public EntityWorkspace getEntityWorkspace() {
	return entityWorkspace;
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	if (oldTheme != null) {
	    super.removeStyle("k-entity-" + oldTheme);
	}
	super.addStyle("k-entity-" + newTheme);
	entityWorkspace.setTheme(oldTheme, newTheme);
    }
}
