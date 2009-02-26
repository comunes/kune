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
 \*/
package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.options.EntityOptions;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class EntityOptionsLogoPresenter implements EntityOptionsLogo {

    private EntityOptionsLogoView view;
    private final Session session;
    private final EntityHeader entityLogo;
    private final EntityOptions entityOptions;
    private final Provider<UserServiceAsync> userService;
    private final Provider<ChatEngine> chatEngine;

    public EntityOptionsLogoPresenter(Session session, EntityHeader entityLogo, EntityOptions entityOptions,
            StateManager stateManager, Provider<UserServiceAsync> userService, Provider<ChatEngine> chatEngine) {
        this.session = session;
        this.entityLogo = entityLogo;
        this.entityOptions = entityOptions;
        this.userService = userService;
        this.chatEngine = chatEngine;
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(String group1, String group2) {
                setState();
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(EntityOptionsLogoView view) {
        this.view = view;
        entityOptions.addOptionTab(view);
        setState();
    }

    public void onSubmitComplete(int httpStatus, String photoBinary) {
        entityLogo.reloadGroupLogoImage();
        GroupDTO group = session.getCurrentState().getGroup();
        if (session.getCurrentUser().getShortName().equals(group.getShortName())) {
            userService.get().getUserAvatarBaser64(session.getUserHash(), group.getStateToken(),
                    new AsyncCallbackSimple<String>() {
                        public void onSuccess(String photoBinary) {
                            chatEngine.get().setAvatar(photoBinary);
                        }
                    });
        }
    }

    public void onSubmitFailed(int httpStatus, String responseText) {
        Site.error("Error setting the group logo: " + responseText);
    }

    private void setState() {
        view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
        if (session.getCurrentState().getGroup().isPersonal()) {
            view.setPersonalGroupsLabels();
        } else {
            view.setNormalGroupsLabels();
        }
    }
}
