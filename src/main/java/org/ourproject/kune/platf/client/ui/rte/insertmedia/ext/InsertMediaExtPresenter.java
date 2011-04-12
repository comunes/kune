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
package org.ourproject.kune.platf.client.ui.rte.insertmedia.ext;

import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.core.shared.dto.ExtMediaDescripDTO;

public class InsertMediaExtPresenter extends InsertMediaAbstractPresenter implements InsertMediaExt {

    private final ExternalMediaRegistry externalMediaRegistry;
    private ExtMediaDescripDTO mediaDescriptor;
    private InsertMediaExtView view;

    public InsertMediaExtPresenter(final InsertMediaDialog insertMediaDialog,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(insertMediaDialog);
        this.externalMediaRegistry = externalMediaRegistry;
    }

    public void init(final InsertMediaExtView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    public boolean isValid() {
        final String url = view.getSrc();
        mediaDescriptor = externalMediaRegistry.get(url);
        if (mediaDescriptor.equals(ExternalMediaRegistry.NO_MEDIA)) {
            insertMediaDialog.setErrorMessage("We cannot process this video link", NotifyLevel.error);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected String updateMediaInfo() {
        return view.setPosition(mediaDescriptor.getEmbed(view.getSrc()));
    }
}
