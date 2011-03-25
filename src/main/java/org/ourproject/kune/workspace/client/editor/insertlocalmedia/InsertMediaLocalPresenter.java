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
package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

import cc.kune.core.client.services.MediaUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;

import com.calclab.suco.client.ioc.Provider;

public class InsertMediaLocalPresenter extends InsertMediaAbstractPresenter implements InsertMediaLocal {

    private final Session session;
    private final Provider<MediaUtils> mediaUtils;

    public InsertMediaLocalPresenter(final InsertMediaDialog insertMediaDialog, final Session session,
            final Provider<MediaUtils> mediaUtils) {
        super(insertMediaDialog);
        this.session = session;
        this.mediaUtils = mediaUtils;
    }

    public String getCurrentGroupName() {
        return session.getCurrentGroupShortName();
    }

    public void init(final InsertMediaLocalView view) {
        super.init(view);
    }

    @Override
    protected String updateMediaInfo() {
        // FIXME (avi, mp3, ...)
        return view.setPosition(mediaUtils.get().getFlvEmbed(new StateToken(view.getSrc())));
    }
}
