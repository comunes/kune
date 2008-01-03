/*
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

package org.ourproject.kune.workspace.client.actions.sn;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;

public class RequestJoinGroupAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onRequestJoinGroup(services);
    }

    private void onRequestJoinGroup(final Services services) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.requestJoinGroup(services.session.getUserHash(),
                services.session.getCurrentState().getGroup().getShortName(), new AsyncCallbackSimple() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        final String resultType = (String) result;
                        if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
                            Site.info(Kune.I18N.t("You are now member of this group"));
                            services.stateManager.reload();
                        }
                        if (resultType == SocialNetworkDTO.REQ_JOIN_DENIED) {
                            Site.important(Kune.I18N.t("Sorry this is a closed group"));
                        }
                        if (resultType == SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION) {
                            Site.info(Kune.I18N.t("Requested. Waiting for admins decision"));
                        }
                    }
                });
    }

}
