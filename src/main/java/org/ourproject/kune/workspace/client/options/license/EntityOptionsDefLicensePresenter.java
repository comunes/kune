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
package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class EntityOptionsDefLicensePresenter implements EntityOptionsDefLicense {

    private EntityOptionsDefLicenseView view;
    private final EntityOptions entityOptions;
    private final Session session;
    private final Provider<LicenseWizard> licenseWizard;
    private final Provider<LicenseChangeAction> licenseChangeAction;

    public EntityOptionsDefLicensePresenter(EntityOptions entityOptions, StateManager stateManager, Session session,
            Provider<LicenseWizard> licenseWizard, Provider<LicenseChangeAction> licenseChangeAction) {
        this.entityOptions = entityOptions;
        this.session = session;
        this.licenseWizard = licenseWizard;
        this.licenseChangeAction = licenseChangeAction;
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(String group1, String group2) {
                setState();
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(EntityOptionsDefLicenseView view) {
        this.view = view;
        entityOptions.addOptionTab(view);
        setState();
    }

    public void onChange() {
        licenseWizard.get().start(new Listener<LicenseDTO>() {
            public void onEvent(final LicenseDTO license) {
                licenseChangeAction.get().changeLicense(license, new Listener0() {
                    public void onEvent() {
                        setLicense(license);
                    }
                });
            }
        });
    }

    public void onLicenseClick() {
        view.openWindow(getCurrentDefLicense().getUrl());
    }

    private LicenseDTO getCurrentDefLicense() {
        return session.getCurrentState().getGroup().getDefaultLicense();
    }

    private void setLicense(LicenseDTO license) {
        view.setLicense(license);
    }

    private void setState() {
        setLicense(getCurrentDefLicense());
    }
}
