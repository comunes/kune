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
package org.ourproject.kune.gallery.client.cnt;

import cc.kune.core.client.cnt.FoldableContentPanel;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;
import cc.kune.wave.client.WaveClientManager;

import com.google.inject.Inject;

public class GalleryViewerPanel extends FoldableContentPanel implements GalleryViewerView {

    @Inject
    public GalleryViewerPanel(final WsArmor ws, final I18nTranslationService i18n, final CoreResources res,
            final WaveClientManager waveClientManager) {
        super(ws, i18n, res, waveClientManager);
    }
}
