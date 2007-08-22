/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.workspace;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

class LogoPanel extends SimplePanel {
    private EntityTextLogo entityTextLogo;

    public LogoPanel() {
    }

    private EntityTextLogo getEntityTextLogo() {
	if (entityTextLogo == null) {
	    this.entityTextLogo = new EntityTextLogo();
	}
	return entityTextLogo;
    }

    public void setLogo(final String groupName) {
	clear();
	add(getEntityTextLogo());
	entityTextLogo.setDefaultText(groupName);
    }

    public void setLogo(final Image image) {
	clear();
	add(image);
    }

    class EntityTextLogo extends VerticalPanel {

	private Label defTextLogoLabel = null;
	private Hyperlink defTextPutYourLogoHL = null;

	public EntityTextLogo() {
	    // Initialize
	    super();
	    defTextLogoLabel = new Label();
	    defTextPutYourLogoHL = new Hyperlink();

	    // Layout
	    add(defTextLogoLabel);
	    add(defTextPutYourLogoHL);

	    // Set properties
	    // defTextPutYourLogoHL.setText(Kune.getInstance().t.PutYourLogoHere());
	    defTextPutYourLogoHL.setText("mirar LogoPanel.java");

	    // TODO: link to configure the logo
	    addStyleName("kune-EntityTextLogo");
	    setDefaultText("");
	}

	public void setDefaultText(final String title) {
	    defTextLogoLabel.setText(title);
	}
    }

}
