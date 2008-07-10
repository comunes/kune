package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.gwtext.client.widgets.Panel;

public class Entity extends DefaultBorderLayout {

    private final EntityWorkspace entityWorkspace;
    private final EntitySummary entitySummary;
    private final Panel entityMainHeader;

    public Entity() {
	entityMainHeader = new Panel();
	entityMainHeader.setBorder(false);
	entitySummary = new EntitySummary();
	entityWorkspace = new EntityWorkspace();
	add(entityMainHeader, DefaultBorderLayout.Position.NORTH, 65);
	add(entityWorkspace.getPanel(), DefaultBorderLayout.Position.CENTER);
	add(entitySummary.getPanel(), DefaultBorderLayout.Position.EAST, true, 150);
    }

    public Panel getEntityMainHeader() {
	return entityMainHeader;
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
