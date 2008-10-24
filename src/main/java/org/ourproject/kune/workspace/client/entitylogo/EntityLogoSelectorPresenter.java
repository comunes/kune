/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.site.Site;

public class EntityLogoSelectorPresenter implements EntityLogoSelector {

    private EntityLogoSelectorView view;
    private final Session session;
    private final EntityLogo entityLogo;

    public EntityLogoSelectorPresenter(Session session, EntityLogo entityLogo) {
        this.session = session;
        this.entityLogo = entityLogo;
    }

    public View getView() {
        return view;
    }

    public void init(EntityLogoSelectorView view) {
        this.view = view;
    }

    public void onCancel() {
        view.hide();
    }

    public void onSubmitComplete(int httpStatus, String responseText) {
        view.hide();
        entityLogo.reloadGroupLogo();
    }

    public void onSubmitFailed(int httpStatus, String responseText) {
        Site.error("Error setting the group logo");
    }

    public void show() {
        view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
        view.show();
    }
}
