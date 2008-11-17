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
 */
package org.ourproject.kune.docs.client.ctx.admin;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.cxt.AbstractContextPropEditorPanel;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.Provider;

public class DocContextPropEditorPanel extends AbstractContextPropEditorPanel implements DocContextPropEditorView {

    public DocContextPropEditorPanel(final DocContextPropEditorPresenter presenter, final I18nTranslationService i18n,
            WorkspaceSkeleton ws, Provider<LanguageSelector> langSelectorProv, Images images) {
        super(presenter, i18n, ws, langSelectorProv, images);
    }

}
