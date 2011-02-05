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
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPanel;

import cc.kune.core.shared.i18n.I18nTranslationService;

public class InsertMediaExtPanel extends InsertMediaAbstractPanel implements InsertMediaExtView {

    public InsertMediaExtPanel(final InsertMediaExtPresenter presenter, final I18nTranslationService i18n,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(i18n.t("External"), presenter);
        String supportedVideos = externalMediaRegistry.getNames();
        setIntro(i18n.t("Provide a link to an external video (supported videos: [%s])", supportedVideos) + "<br/>");
        hrefField.setTitle(i18n.t("Write something like: http://www.youtube.com/watch?v=PxsGyljd6B0"));
    }

}
