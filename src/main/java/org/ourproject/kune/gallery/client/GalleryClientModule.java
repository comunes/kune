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
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbar;
import org.ourproject.kune.workspace.client.cnt.ContentActionRegistry;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsBackManager;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.upload.FileUploader;

import cc.kune.common.client.utils.SchedulerManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;

public class GalleryClientModule extends AbstractExtendedModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<GalleryClientTool>(GalleryClientTool.class) {
            @Override
            public GalleryClientTool create() {
                i(GalleryClientActions.class);
                return new GalleryClientTool(i(I18nUITranslationService.class), i(ToolSelector.class),
                        i(WsThemeManager.class), i(WorkspaceSkeleton.class), i(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<GalleryClientActions>(GalleryClientActions.class) {
            @Override
            public GalleryClientActions create() {
                return new GalleryClientActions(i(I18nUITranslationService.class), i(ContextNavigator.class),
                        i(Session.class), i(StateManager.class), i(SchedulerManager.class),
                        p(ContentServiceAsync.class), p(GroupServiceAsync.class), p(FileUploader.class),
                        i(ContentActionRegistry.class), i(ContextActionRegistry.class), p(FileDownloadUtils.class),
                        i(EntityHeader.class), p(ContentEditor.class), i(ErrorHandler.class), i(GalleryViewer.class),
                        p(ContextPropEditor.class), i(SitePublicSpaceLink.class), i(WsBackManager.class));
            }
        });

        register(ToolGroup.class, new Factory<GalleryContext>(GalleryContext.class) {
            @Override
            public GalleryContext create() {
                final GalleryContextPresenter presenter = new GalleryContextPresenter(i(StateManager.class),
                        p(ContextNavigator.class), p(ContextPropEditor.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<GalleryViewer>(GalleryViewer.class) {
            @Override
            public GalleryViewer create() {
                final GalleryViewerPresenter presenter = new GalleryViewerPresenter(i(StateManager.class),
                        i(Session.class), i(I18nUITranslationService.class), i(ActionContentToolbar.class),
                        i(ContentActionRegistry.class), p(FileDownloadUtils.class), p(MediaUtils.class));
                final GalleryViewerView view = new GalleryViewerPanel(i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<GalleryFolderContent>(GalleryFolderContent.class) {
            @Override
            public GalleryFolderContent create() {
                final GalleryFolderContentPresenter presenter = new GalleryFolderContentPresenter(
                        i(StateManager.class), i(Session.class), i(ActionContentToolbar.class),
                        i(ContentActionRegistry.class), i(I18nTranslationService.class), p(FileDownloadUtils.class),
                        p(FileDownloadUtils.class), p(MediaUtils.class));
                final GalleryFolderContentView view = new GalleryFolderContentPanel(i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class), i(StateManager.class), i(Session.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
