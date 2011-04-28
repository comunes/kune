/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.options.license;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class EntityOptionsDefLicensePresenter {

    private final EntityOptions entityOptions;
    // private final Provider<LicenseChangeAction> licChangeAction;
    // private final Provider<LicenseWizard> licenseWizard;
    protected final Session session;
    private EntityOptionsDefLicenseView view;

    public EntityOptionsDefLicensePresenter(final EntityOptions entityOptions, final Session session) {
        // final Provider<LicenseWizard> licenseWizard, final
        // Provider<LicenseChangeAction> licChangeAction) {
        this.entityOptions = entityOptions;
        this.session = session;
        // this.licenseWizard = licenseWizard;
        // this.licChangeAction = licChangeAction;
    }

    protected abstract boolean applicable();

    protected abstract LicenseDTO getCurrentDefLicense();

    protected abstract StateToken getOperationToken();

    public IsWidget getView() {
        return view;
    }

    protected void init(final EntityOptionsDefLicenseView view) {
        this.view = view;
        entityOptions.addTab(view, view.getTabTitle());
        setState();
        view.getChange().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onLicenseClick();
            }
        });
        view.getLicenseImage().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                // onChange();
            }
        });
    }

    // public void onChange() {
    // licenseWizard.get().start(new Listener<LicenseDTO>() {
    // @Override
    // public void onEvent(final LicenseDTO license) {
    // licChangeAction.get().changeLicense(getOperationToken(), license, new
    // Listener0() {
    // @Override
    // public void onEvent() {
    // setLicense(license);
    // }
    // });
    // }
    // });
    // }

    public void onLicenseClick() {
        view.openWindow(getCurrentDefLicense().getUrl());
    }

    private void setLicense(final LicenseDTO license) {
        view.setLicense(license);
    }

    protected void setState() {
        if (applicable()) {
            setLicense(getCurrentDefLicense());
        }
    }
}
