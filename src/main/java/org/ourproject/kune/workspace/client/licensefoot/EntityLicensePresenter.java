/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener;

public class EntityLicensePresenter {

    private EntityLicenseView view;
    private LicenseDTO license;

    public EntityLicensePresenter(final StateManager stateManager) {
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContainerDTO) {
                    setLicense((StateContainerDTO) state);
                } else {
                    view.detach();
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final EntityLicenseView view) {
        this.view = view;
    }

    public void onLicenseClick() {
        view.openWindow(license.getUrl());
    }

    private void setLicense(final StateContainerDTO state) {
        this.license = state.getLicense();
        view.showLicense(state.getGroup().getLongName(), license);
        view.attach();
    }
}
