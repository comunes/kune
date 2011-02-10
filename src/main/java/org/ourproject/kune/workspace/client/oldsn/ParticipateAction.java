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
package org.ourproject.kune.workspace.client.oldsn;

import org.ourproject.kune.platf.client.actions.ActionEvent;

import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Provider;

public class ParticipateAction extends RolAction {
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;

    public ParticipateAction(final Session session, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final StateManager stateManager, final AccessRightsClientManager rightsManager,
            final I18nTranslationService i18n, final IconResources imgResources) {
        super(session, stateManager, rightsManager, i18n, AccessRolDTO.Viewer, i18n.t("Participate"),
                i18n.t("Request to participate in this group"), imgResources.addGreen());
        this.snServiceProvider = snServiceProvider;
        super.setVisible(false, true);
        super.setMustBeAuthenticated(false);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().requestJoinGroup(session.getUserHash(), session.getCurrentState().getStateToken(),
                new AsyncCallbackSimple<SocialNetworkRequestResult>() {
                    @Override
                    public void onSuccess(final SocialNetworkRequestResult result) {
                        NotifyUser.hideProgress();
                        switch ((result)) {
                        case accepted:
                            NotifyUser.info(i18n.t("You are now member of this group"));
                            stateManager.reload();
                            break;
                        case denied:
                            NotifyUser.important(i18n.t("Sorry this is a closed group"));
                            break;
                        case moderated:
                            NotifyUser.info(i18n.t("Membership requested. Waiting for admins decision"));
                            break;
                        default:
                            NotifyUser.info(i18n.t("Programatic error in ParticipateAction"));
                        }
                    }
                });
    }
}
