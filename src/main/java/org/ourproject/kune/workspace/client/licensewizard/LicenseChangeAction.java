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
package org.ourproject.kune.workspace.client.licensewizard;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LicenseChangeAction {
    private final StateManager stateManager;
    private final Provider<GroupServiceAsync> groupService;
    private final Session session;
    private final I18nTranslationService i18n;

    public LicenseChangeAction(final Provider<GroupServiceAsync> groupService, final Session session,
            final I18nTranslationService i18n, final StateManager stateManager) {
        this.groupService = groupService;
        this.session = session;
        this.i18n = i18n;
        this.stateManager = stateManager;
    }

    public void changeLicense(final StateToken token, final LicenseDTO license, final Listener0 onSuccess) {
        NotifyUser.showProgressProcessing();
        groupService.get().changeDefLicense(session.getUserHash(), token, license, new AsyncCallback<Object>() {
            public void onFailure(final Throwable caught) {
                NotifyUser.hideProgress();
                NotifyUser.error(i18n.t("Error changing default group license"));
            }

            public void onSuccess(final Object result) {
                stateManager.reload();
                onSuccess.onEvent();
            }
        });
    }
}
