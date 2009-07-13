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
package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.cnt.DocFolderContent;
import org.ourproject.kune.docs.client.cnt.DocFolderContentPanel;
import org.ourproject.kune.docs.client.cnt.DocFolderContentPresenter;
import org.ourproject.kune.docs.client.cnt.DocFolderContentView;
import org.ourproject.kune.docs.client.cnt.DocumentViewer;
import org.ourproject.kune.docs.client.cnt.DocumentViewerPanel;
import org.ourproject.kune.docs.client.cnt.DocumentViewerPresenter;
import org.ourproject.kune.docs.client.cnt.DocumentViewerView;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ctx.DocumentContextPresenter;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.platf.client.services.ErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbar;
import org.ourproject.kune.workspace.client.cnt.ContentActionRegistry;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsBackManager;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.upload.FileUploader;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;

public class DocumentClientModule extends AbstractExtendedModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<DocumentClientTool>(DocumentClientTool.class) {
            @Override
            public DocumentClientTool create() {
                i(DocumentClientActions.class);
                return new DocumentClientTool(i(I18nUITranslationService.class), i(ToolSelector.class),
                        i(WsThemeManager.class), i(WorkspaceSkeleton.class), i(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<DocumentClientActions>(DocumentClientActions.class) {
            @Override
            public DocumentClientActions create() {
                return new DocumentClientActions(i(I18nUITranslationService.class), i(ContextNavigator.class),
                        i(Session.class), i(StateManager.class), i(DeferredCommandWrapper.class),
                        p(ContentServiceAsync.class), p(GroupServiceAsync.class), p(FileUploader.class),
                        i(ContentActionRegistry.class), i(ContextActionRegistry.class), p(FileDownloadUtils.class),
                        i(EntityHeader.class), p(ContentEditor.class), i(ErrorHandler.class), i(DocumentViewer.class),
                        p(ContextPropEditor.class), i(SitePublicSpaceLink.class), i(WsBackManager.class));
            }
        });

        register(ToolGroup.class, new Factory<DocumentContext>(DocumentContext.class) {
            @Override
            public DocumentContext create() {
                final DocumentContextPresenter presenter = new DocumentContextPresenter(i(StateManager.class),
                        p(ContextNavigator.class), p(ContextPropEditor.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<DocumentViewer>(DocumentViewer.class) {
            @Override
            public DocumentViewer create() {
                final DocumentViewerPresenter presenter = new DocumentViewerPresenter(i(StateManager.class),
                        i(Session.class), i(I18nUITranslationService.class), i(ActionContentToolbar.class),
                        i(ContentActionRegistry.class), p(FileDownloadUtils.class), p(MediaUtils.class));
                final DocumentViewerView view = new DocumentViewerPanel(i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<DocFolderContent>(DocFolderContent.class) {
            @Override
            public DocFolderContent create() {
                final DocFolderContentPresenter presenter = new DocFolderContentPresenter(i(StateManager.class),
                        i(Session.class), i(ActionContentToolbar.class), i(ContentActionRegistry.class),
                        p(FileDownloadUtils.class), i(I18nTranslationService.class), p(MediaUtils.class));
                final DocFolderContentView view = new DocFolderContentPanel(i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
