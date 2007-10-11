/*
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

package org.ourproject.kune.workspace.client.license.ui;

import org.ourproject.kune.workspace.client.license.LicensePresenter;
import org.ourproject.kune.workspace.client.license.LicenseView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LicensePanel extends HorizontalPanel implements LicenseView {
    private final Label copyright;
    private final Image image;

    public LicensePanel(final LicensePresenter presenter) {
	copyright = new Label();
	image = new Image();
	this.add(copyright);
	this.add(image);
	ClickListener clickListener = new ClickListener() {
	    public void onClick(Widget arg0) {
		presenter.onLicenseClick();
	    }
	};
	copyright.addClickListener(clickListener);
	image.addClickListener(clickListener);
	copyright.setVisible(false);
	image.setVisible(false);
    }

    public void showImage(final String imageUrl) {
	image.setUrl(imageUrl);
	image.setVisible(true);
    }

    public void showName(final String groupName, final String licenseName) {
	copyright.setText("© " + groupName + ", under license " + licenseName);
	copyright.setVisible(true);
    }
}
