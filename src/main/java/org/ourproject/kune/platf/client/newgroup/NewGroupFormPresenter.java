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

package org.ourproject.kune.platf.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupFormPresenter implements NewGroupForm {
    private final NewGroupListener listener;
    private NewGroupFormView view;

    public NewGroupFormPresenter(final NewGroupListener listener) {
	this.listener = listener;
    }

    public void init(final NewGroupFormView view) {
	this.view = view;
    }

    public void onFinish() {
	KuneServiceAsync kuneService = KuneService.App.getInstance();
	String shortName = view.getShortName();
	String longName = view.getLongName();
	String publicDesc = view.getPublicDesc();

	// TODO: without license you can't create a group

	GroupDTO group = new GroupDTO(shortName, longName, publicDesc, "by-cc", getTypeOfGroup());
	// FIXME: get User hash
	kuneService.createNewGroup("FIXMEFIXME", group, new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error creating group");
	    }

	    public void onSuccess(final Object arg0) {
		listener.onNewGroupCreated((StateToken) arg0);
		view.hide();
		reset();
	    }
	});
    }

    public View getView() {
	return view;
    }

    private int getTypeOfGroup() {
	if (view.isProject()) {
	    return GroupDTO.TYPE_PROJECT;
	} else if (view.isOrganization()) {
	    return GroupDTO.TYPE_ORGANIZATION;
	} else {
	    return GroupDTO.TYPE_COMNUNITY;
	}
    }

    private void reset() {
	view.clearData();
    }

    public void onBack() {
	view.setEnabledBackButton(false);
	view.setEnabledFinishButton(false);
	view.setEnabledNextButton(true);
	view.showNewGroupInitialDataForm();

    }

    public void onCancel() {
	reset();
	view.hide();
	listener.onNewGroupCancel();
    }

    public void onNext() {
	view.setEnabledBackButton(true);
	view.setEnabledFinishButton(true);
	view.setEnabledNextButton(false);
	view.showLicenseForm();
    }

    public void onCClicenseSelected() {
	// TODO Auto-generated method stub

    }

    public void onNotCClicenseSelected() {
	// TODO Auto-generated method stub

    }

    public void onNotCCselected() {
	view.setCCoptionsVisible(false);
	view.setNonCCoptionsVisible(true);
    }

    public void onCCselected() {
	view.setCCoptionsVisible(true);
	view.setNonCCoptionsVisible(false);
    }
}
