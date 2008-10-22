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
package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePresenter;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderDialog;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderPresenter;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class KunePlatformModule extends AbstractModule {

    @Override
    protected void onInstall() {
        register(Singleton.class, new Factory<ColorWebSafePalette>(ColorWebSafePalette.class) {
            @Override
            public ColorWebSafePalette create() {
                final ColorWebSafePalettePresenter presenter = new ColorWebSafePalettePresenter();
                final ColorWebSafePalettePanel panel = new ColorWebSafePalettePanel(presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<FileUploader>(FileUploader.class) {
            @Override
            public FileUploader create() {
                final FileUploaderPresenter presenter = new FileUploaderPresenter($(Session.class),
                        $$(ContextNavigator.class));
                final FileUploaderDialog panel = new FileUploaderDialog(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

    }
}
