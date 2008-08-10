/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.utils.PrefetchUtilities;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.signal.Signal0;
import com.calclab.suco.client.signal.Slot0;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationDefault implements Application {
    private final Session session;
    private final Signal0 onApplicationStart;
    private final Signal0 onApplicationStop;

    public ApplicationDefault(final Session session) {
	this.session = session;
	this.onApplicationStart = new Signal0("onApplicationStart");
	this.onApplicationStop = new Signal0("onApplicationStop");
	Window.addWindowCloseListener(new WindowCloseListener() {
	    public void onWindowClosed() {
		stop();
	    }

	    public String onWindowClosing() {
		return null;
	    }
	});
    }

    public void onApplicationStart(final Slot0 slot) {
	onApplicationStart.add(slot);
    }

    public void onApplicationStop(final Slot0 slot) {
	onApplicationStop.add(slot);
    }

    public void start() {
	onApplicationStart.fire();
	PrefetchUtilities.preFetchImpImages();
	getInitData();
	final Timer prefetchTimer = new Timer() {
	    public void run() {
		PrefetchUtilities.doTasksDeferred();
	    }
	};
	prefetchTimer.schedule(20000);
    }

    private void getInitData() {
	final SiteServiceAsync server = SiteService.App.getInstance();
	server.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
	    public void onFailure(final Throwable error) {
		RootPanel.get("kuneinitialcurtain").setVisible(false);
		Site.error("Error fetching initial data");
		Log.debug(error.getMessage());
	    }

	    public void onSuccess(final InitDataDTO initData) {
		session.setInitData(initData);
		session.setCurrentUserInfo(initData.getUserInfo());
		RootPanel.get("kuneinitialcurtain").setVisible(false);
	    }
	});
    }

    private void stop() {
	onApplicationStop.fire();
    }
}
