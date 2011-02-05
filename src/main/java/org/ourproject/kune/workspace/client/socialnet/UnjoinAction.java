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
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class UnjoinAction extends RolAction {
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private String groupName;

    public UnjoinAction(final Session session, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final StateManager stateManager, final AccessRightsClientManager rightsManager,
            final I18nTranslationService i18n, final IconResources imgResources) {
        super(session, stateManager, rightsManager, i18n, AccessRolDTO.Editor, i18n.t("Leave this group"),
                i18n.t("Do not participate anymore in this group"), imgResources.delGreen());
        this.snServiceProvider = snServiceProvider;
        super.setVisible(true, false);
        super.setMustBeAuthenticated(true);
    }

    public void actionPerformed(final ActionEvent event) {
        NotifyUser.askConfirmation(i18n.t("Leave this group"), i18n.t("Are you sure?"), new Listener0() {
            public void onEvent() {
                NotifyUser.showProgressProcessing();
                snServiceProvider.get().unJoinGroup(session.getUserHash(), new StateToken(getGroupName()),
                        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                            public void onSuccess(final SocialNetworkDataDTO result) {
                                NotifyUser.hideProgress();
                                NotifyUser.info(i18n.t("Removed as member"));
                                stateManager.reload();
                                // in the future with user info:
                                // stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                                // result);
                            }
                        });
            }
        });
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
}
