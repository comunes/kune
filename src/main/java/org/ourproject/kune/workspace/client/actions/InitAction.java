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

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class InitAction extends WorkspaceAction {
    public void execute(final Object value, final Object extra) {
	GWT.log("Locale: " + Kune.getInstance().t.Locale(), null);
	PrefetchUtilites.preFetchImpImages();
	getInitData();

	int windowWidth = Window.getClientWidth();
	Workspace workspace = Services.get().app.getWorkspace();
	workspace.adjustSize(windowWidth, Window.getClientHeight());
	SiteBarFactory.getSiteMessage().adjustWidth(windowWidth);

	RootPanel.get("kuneinitialcurtain").setVisible(false);
    }

    private void getInitData() {
	KuneServiceAsync server = KuneService.App.getInstance();
	server.getInitData(Services.get().user, new AsyncCallback() {
	    public void onFailure(final Throwable error) {
		// i18n
		Site.error("Error fetching initial data");
	    }

	    public void onSuccess(final Object response) {
		Dispatcher dispatcher = Services.get().dispatcher;
		InitDataDTO initData = (InitDataDTO) response;
		Services.get().session.setCCLicenses(initData.getCCLicenses());
		Services.get().session.setNotCCLicenses(initData.getNotCCLicenses());
		UserDTO currentUser = initData.getCurrentUser();
		dispatcher.fire(WorkspaceEvents.INIT_DATA_RECEIVED, response, null);
		if (currentUser == null) {
		    dispatcher.fire(WorkspaceEvents.USER_LOGGED_OUT, null, null);
		} else {
		    dispatcher.fire(WorkspaceEvents.USER_LOGGED_IN, currentUser, null);
		}
	    }
	});
    }
}
