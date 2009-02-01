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
 \*/
package org.ourproject.kune.gallery.client;

import org.ourproject.kune.gallery.client.cnt.GalleryFolderContent;
import org.ourproject.kune.gallery.client.cnt.GalleryFolderContentPanel;
import org.ourproject.kune.gallery.client.cnt.GalleryFolderContentPresenter;
import org.ourproject.kune.gallery.client.cnt.GalleryFolderContentView;
import org.ourproject.kune.gallery.client.cnt.GalleryViewer;
import org.ourproject.kune.gallery.client.cnt.GalleryViewerPanel;
import org.ourproject.kune.gallery.client.cnt.GalleryViewerPresenter;
import org.ourproject.kune.gallery.client.cnt.GalleryViewerView;
import org.ourproject.kune.gallery.client.ctx.GalleryContext;
import org.ourproject.kune.gallery.client.ctx.GalleryContextPresenter;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class GalleryClientModule extends AbstractModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<GalleryClientTool>(GalleryClientTool.class) {
            @Override
            public GalleryClientTool create() {
                $(GalleryClientActions.class);
                return new GalleryClientTool($(I18nUITranslationService.class), $(ToolSelector.class),
                        $(WsThemePresenter.class), $(WorkspaceSkeleton.class), $(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<GalleryClientActions>(GalleryClientActions.class) {
            @Override
            public GalleryClientActions create() {
                return new GalleryClientActions($(I18nUITranslationService.class), $(ContextNavigator.class),
                        $(Session.class), $(StateManager.class), $(DeferredCommandWrapper.class),
                        $$(ContentServiceAsync.class), $$(GroupServiceAsync.class), $$(FileUploader.class),
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(FileDownloadUtils.class),
                        $(EntityHeader.class), $$(TextEditor.class), $(KuneErrorHandler.class), $(GalleryViewer.class),
                        $$(ContextPropEditor.class), $(SitePublicSpaceLink.class));
            }
        });

        register(ToolGroup.class, new Factory<GalleryContext>(GalleryContext.class) {
            @Override
            public GalleryContext create() {
                final GalleryContextPresenter presenter = new GalleryContextPresenter($(StateManager.class),
                        $$(ContextNavigator.class), $$(ContextPropEditor.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<GalleryViewer>(GalleryViewer.class) {
            @Override
            public GalleryViewer create() {
                final GalleryViewerPresenter presenter = new GalleryViewerPresenter($(StateManager.class),
                        $(Session.class), $(I18nUITranslationService.class), $(ActionContentToolbar.class),
                        $(ContentActionRegistry.class), $$(FileDownloadUtils.class));
                final GalleryViewerView view = new GalleryViewerPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<GalleryFolderContent>(GalleryFolderContent.class) {
            @Override
            public GalleryFolderContent create() {
                final GalleryFolderContentPresenter presenter = new GalleryFolderContentPresenter(
                        $(StateManager.class), $(Session.class), $(ActionContentToolbar.class),
                        $(ContentActionRegistry.class), $(I18nTranslationService.class), $$(FileDownloadUtils.class),
                        $$(FileDownloadUtils.class));
                final GalleryFolderContentView view = new GalleryFolderContentPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class), $(StateManager.class), $(Session.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
