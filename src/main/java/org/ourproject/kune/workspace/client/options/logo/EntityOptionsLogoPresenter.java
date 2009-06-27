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
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.ioc.Provider;

public abstract class EntityOptionsLogoPresenter implements GroupOptionsLogo, UserOptionsLogo {

    protected EntityOptionsLogoView view;
    protected final Session session;
    private final EntityHeader entityLogo;
    private final EntityOptions entityOptions;
    protected final Provider<UserServiceAsync> userService;
    protected final Provider<ChatEngine> chatEngine;

    public EntityOptionsLogoPresenter(final Session session, final EntityHeader entityLogo,
            final EntityOptions entityOptions, final Provider<UserServiceAsync> userService,
            final Provider<ChatEngine> chatEngine) {
        this.session = session;
        this.entityLogo = entityLogo;
        this.entityOptions = entityOptions;
        this.userService = userService;
        this.chatEngine = chatEngine;
    }

    public View getView() {
        return view;
    }

    public void init(final EntityOptionsLogoView view) {
        this.view = view;
        entityOptions.addTab(view);
        setState();
    }

    public void onSubmitComplete(final int httpStatus, final String photoBinary) {
        entityLogo.reloadGroupLogoImage();
    }

    public void onSubmitFailed(final int httpStatus, final String responseText) {
        NotifyUser.error("Error setting the logo: " + responseText);
    }

    protected abstract void setState();

}
