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
package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsLogoPresenter extends EntityOptionsLogoPresenter {

    public UserOptionsLogoPresenter(final Session session, final EntityHeader entityLogo,
            final EntityOptions entityOptions, final StateManager stateManager,
            final Provider<UserServiceAsync> userService, final Provider<ChatEngine> chatEngine) {
        super(session, entityLogo, entityOptions, userService, chatEngine);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    public void init(final EntityOptionsLogoView view) {
        super.init(view);
        view.setPersonalGroupsLabels();
    }

    @Override
    public void onSubmitComplete(final int httpStatus, final String photoBinary) {
        super.onSubmitComplete(httpStatus, photoBinary);
        final GroupDTO group = session.getCurrentState().getGroup();
        if (session.getCurrentUser().getShortName().equals(group.getShortName())) {
            userService.get().getUserAvatarBaser64(session.getUserHash(), group.getStateToken(),
                    new AsyncCallbackSimple<String>() {
                        public void onSuccess(final String photoBinary) {
                            chatEngine.get().setAvatar(photoBinary);
                        }
                    });
        }
    }

    @Override
    protected void setState() {
        view.setUploadParams(session.getUserHash(), session.getCurrentUser().getStateToken().toString());
    }
}
