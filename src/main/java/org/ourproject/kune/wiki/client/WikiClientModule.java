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
package org.ourproject.kune.wiki.client;

import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.wiki.client.cnt.WikiFolderContent;
import org.ourproject.kune.wiki.client.cnt.WikiFolderContentPanel;
import org.ourproject.kune.wiki.client.cnt.WikiFolderContentPresenter;
import org.ourproject.kune.wiki.client.cnt.WikiFolderContentView;
import org.ourproject.kune.wiki.client.cnt.WikiViewer;
import org.ourproject.kune.wiki.client.cnt.WikiViewerPanel;
import org.ourproject.kune.wiki.client.cnt.WikiViewerPresenter;
import org.ourproject.kune.wiki.client.cnt.WikiViewerView;
import org.ourproject.kune.wiki.client.ctx.WikiContext;
import org.ourproject.kune.wiki.client.ctx.WikiContextPresenter;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.upload.FileUploader;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class WikiClientModule extends AbstractModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<WikiClientTool>(WikiClientTool.class) {
            @Override
            public WikiClientTool create() {
                $(WikiClientActions.class);
                return new WikiClientTool($(I18nUITranslationService.class), $(ToolSelector.class),
                        $(WsThemePresenter.class), $(WorkspaceSkeleton.class), $(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<WikiClientActions>(WikiClientActions.class) {
            @Override
            public WikiClientActions create() {
                return new WikiClientActions($(I18nUITranslationService.class), $(ContextNavigator.class),
                        $(Session.class), $(StateManager.class), $(DeferredCommandWrapper.class),
                        $$(ContentServiceAsync.class), $$(GroupServiceAsync.class), $$(FileUploader.class),
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(FileDownloadUtils.class),
                        $(EntityHeader.class), $$(TextEditor.class), $(KuneErrorHandler.class), $(WikiViewer.class),
                        $$(ContextPropEditor.class), $(SitePublicSpaceLink.class));
            }
        });

        register(ToolGroup.class, new Factory<WikiContext>(WikiContext.class) {
            @Override
            public WikiContext create() {
                final WikiContextPresenter presenter = new WikiContextPresenter($(StateManager.class),
                        $$(ContextNavigator.class), $$(ContextPropEditor.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<WikiViewer>(WikiViewer.class) {
            @Override
            public WikiViewer create() {
                final WikiViewerPresenter presenter = new WikiViewerPresenter($(StateManager.class), $(Session.class),
                        $(I18nUITranslationService.class), $(ActionContentToolbar.class),
                        $(ContentActionRegistry.class), $$(FileDownloadUtils.class));
                final WikiViewerView view = new WikiViewerPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<WikiFolderContent>(WikiFolderContent.class) {
            @Override
            public WikiFolderContent create() {
                final WikiFolderContentPresenter presenter = new WikiFolderContentPresenter($(StateManager.class),
                        $(Session.class), $(ActionContentToolbar.class), $(ContentActionRegistry.class),
                        $(I18nTranslationService.class), $$(FileDownloadUtils.class));
                final WikiFolderContentView view = new WikiFolderContentPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
