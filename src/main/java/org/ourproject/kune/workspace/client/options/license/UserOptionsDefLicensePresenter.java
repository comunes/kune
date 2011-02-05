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
package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsDefLicensePresenter extends EntityOptionsDefLicensePresenter implements UserOptionsDefLicense {

    public UserOptionsDefLicensePresenter(final EntityOptions entityOptions, final Session session,
            final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
        super(entityOptions, session, licenseWizard, licChangeAction);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    protected boolean applicable() {
        return session.isLogged();
    }

    @Override
    protected LicenseDTO getCurrentDefLicense() {
        return session.getCurrentState().getGroup().getDefaultLicense();
    }

    @Override
    protected StateToken getOperationToken() {
        return session.getCurrentUser().getStateToken();
    }
}
