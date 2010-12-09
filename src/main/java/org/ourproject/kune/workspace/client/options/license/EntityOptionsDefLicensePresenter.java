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
import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateToken;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public abstract class EntityOptionsDefLicensePresenter {

    private EntityOptionsDefLicenseView view;
    private final EntityOptions entityOptions;
    protected final Session session;
    private final Provider<LicenseWizard> licenseWizard;
    private final Provider<LicenseChangeAction> licChangeAction;

    public EntityOptionsDefLicensePresenter(final EntityOptions entityOptions, final Session session,
            final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
        this.entityOptions = entityOptions;
        this.session = session;
        this.licenseWizard = licenseWizard;
        this.licChangeAction = licChangeAction;

    }

    public View getView() {
        return view;
    }

    public void init(final EntityOptionsDefLicenseView view) {
        this.view = view;
        entityOptions.addTab(view);
        setState();
    }

    public void onChange() {
        licenseWizard.get().start(new Listener<LicenseDTO>() {
            public void onEvent(final LicenseDTO license) {
                licChangeAction.get().changeLicense(getOperationToken(), license, new Listener0() {
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

    protected abstract boolean applicable();

    protected abstract LicenseDTO getCurrentDefLicense();

    protected abstract StateToken getOperationToken();

    protected void setState() {
        if (applicable()) {
            setLicense(getCurrentDefLicense());
        }
    }

    private void setLicense(final LicenseDTO license) {
        view.setLicense(license);
    }
}
