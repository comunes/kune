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
package org.ourproject.kune.blogs.client;

import org.ourproject.kune.blogs.client.cnt.BlogFolderContent;
import org.ourproject.kune.blogs.client.cnt.BlogFolderContentPanel;
import org.ourproject.kune.blogs.client.cnt.BlogFolderContentPresenter;
import org.ourproject.kune.blogs.client.cnt.BlogFolderContentView;
import org.ourproject.kune.blogs.client.cnt.BlogViewer;
import org.ourproject.kune.blogs.client.cnt.BlogViewerPanel;
import org.ourproject.kune.blogs.client.cnt.BlogViewerPresenter;
import org.ourproject.kune.blogs.client.cnt.BlogViewerView;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.blogs.client.ctx.BlogContextPresenter;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.I18nUITranslationService;
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
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class BlogClientModule extends AbstractModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<BlogClientTool>(BlogClientTool.class) {
            @Override
            public BlogClientTool create() {
                $(BlogClientActions.class);
                return new BlogClientTool($(I18nUITranslationService.class), $(ToolSelector.class),
                        $(WsThemePresenter.class), $(WorkspaceSkeleton.class), $(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<BlogClientActions>(BlogClientActions.class) {
            @Override
            public BlogClientActions create() {
                return new BlogClientActions($(I18nUITranslationService.class), $(ContextNavigator.class),
                        $(Session.class), $(StateManager.class), $(DeferredCommandWrapper.class),
                        $$(ContentServiceAsync.class), $$(GroupServiceAsync.class), $$(FileUploader.class),
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(FileDownloadUtils.class),
                        $(EntityHeader.class), $$(TextEditor.class), $(KuneErrorHandler.class), $(BlogViewer.class),
                        $$(ContextPropEditor.class), $(SitePublicSpaceLink.class));
            }
        });

        register(ToolGroup.class, new Factory<BlogContext>(BlogContext.class) {
            @Override
            public BlogContext create() {
                final BlogContextPresenter presenter = new BlogContextPresenter($(StateManager.class),
                        $$(ContextNavigator.class), $$(ContextPropEditor.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<BlogViewer>(BlogViewer.class) {
            @Override
            public BlogViewer create() {
                final BlogViewerPresenter presenter = new BlogViewerPresenter($(StateManager.class), $(Session.class),
                        $(I18nUITranslationService.class), $(ActionContentToolbar.class),
                        $(ContentActionRegistry.class), $$(FileDownloadUtils.class));
                final BlogViewerView view = new BlogViewerPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<BlogFolderContent>(BlogFolderContent.class) {
            @Override
            public BlogFolderContent create() {
                final BlogFolderContentPresenter presenter = new BlogFolderContentPresenter($(StateManager.class),
                        $(Session.class), $(ActionContentToolbar.class), $(ContentActionRegistry.class),
                        $(I18nTranslationService.class), $$(FileDownloadUtils.class));
                final BlogFolderContentView view = new BlogFolderContentPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
