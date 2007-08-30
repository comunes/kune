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

package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.sitebar.client.SiteBarFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class InitAction extends WorkspaceAction {
    public void execute(final Object value, final Object extra) {
	GWT.log("Locale: " + Kune.getInstance().t.Locale(), null);
	PrefetchUtilites.preFetchImpImages();
	PrefetchUtilites.preFetchLicenses(session);

	String token = History.getToken();
	stateManager.onHistoryChanged(token);
	int windowWidth = Window.getClientWidth();
	workspace.adjustSize(windowWidth, Window.getClientHeight());
	SiteBarFactory.getSiteMessage().adjustWidth(windowWidth);

	RootPanel.get("kuneinitialcurtain").setVisible(false);
    }
}
