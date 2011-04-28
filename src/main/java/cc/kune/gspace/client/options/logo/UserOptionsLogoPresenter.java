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
package cc.kune.gspace.client.options.logo;

import cc.kune.chat.client.ChatClient;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.options.UserOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserOptionsLogoPresenter extends EntityOptionsLogoPresenter {

    @Inject
    public UserOptionsLogoPresenter(final EventBus eventBus, final Session session, final EntityHeader entityLogo,
            final UserOptions entityOptions, final StateManager stateManager,
            final Provider<UserServiceAsync> userService, final Provider<ChatClient> chatEngine,
            final UserOptionsLogoView view) {
        super(eventBus, session, entityLogo, entityOptions, userService, chatEngine);
        init(view);
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                setState();
            }
        });
    }

    private void init(final UserOptionsLogoView view) {
        super.init(view);
        view.setPersonalGroupsLabels();
    }

    @Override
    public void onSubmitComplete() {
        super.onSubmitComplete();
        final GroupDTO group = session.getCurrentState().getGroup();
        if (session.getCurrentUser().getShortName().equals(group.getShortName())) {
            userService.get().getUserAvatarBaser64(session.getUserHash(), group.getStateToken(),
                    new AsyncCallbackSimple<String>() {
                        @Override
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
