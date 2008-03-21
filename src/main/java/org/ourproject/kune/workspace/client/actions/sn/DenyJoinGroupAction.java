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
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class DenyJoinGroupAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onDenyJoinGroup(services, (String) value);
    }

    private void onDenyJoinGroup(final Services services, final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.denyJoinGroup(services.session.getUserHash(), services.session.getCurrentState().getGroup()
                .getShortName(), groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
            public void onSuccess(final SocialNetworkResultDTO result) {
                Site.hideProgress();
                Site.info(Kune.I18N.t("Member rejected"));
                services.stateManager.setSocialNetwork(result);
            }
        });
    }
}
