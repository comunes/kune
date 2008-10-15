package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentPanel;
import org.ourproject.kune.docs.client.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.cnt.folder.viewer.ui.FolderViewerPanel;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ctx.DocumentContextPanel;
import org.ourproject.kune.docs.client.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ctx.admin.ui.AdminContextPanel;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.DragDropContentRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPresenter;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class DocumentClientModule extends AbstractModule {

    public DocumentClientModule() {
    }

    @Override
    public void onLoad() {

        register(ToolGroup.class, new Factory<DocumentClientTool>(DocumentClientTool.class) {
            @Override
            public DocumentClientTool create() {
                $(DocumentClientActions.class);
                return new DocumentClientTool($(I18nUITranslationService.class), $(ToolSelector.class),
                        $(WsThemePresenter.class), $(WorkspaceSkeleton.class), $$(DocumentContext.class),
                        $$(ContentServiceAsync.class), $(ContentActionRegistry.class),
                        $(DragDropContentRegistry.class), $(ContentIconsRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<DocumentClientActions>(DocumentClientActions.class) {
            @Override
            public DocumentClientActions create() {
                return new DocumentClientActions($(I18nUITranslationService.class), $(ContextNavigator.class),
                        $(Session.class), $(StateManager.class), $$(ContentServiceAsync.class),
                        $$(GroupServiceAsync.class), $$(FileUploader.class), $(ContentActionRegistry.class),
                        $(ContextActionRegistry.class), $$(FileDownloadUtils.class), $(EntityLogo.class),
                        $$(TextEditor.class), $(KuneErrorHandler.class), $(DocumentContent.class));
            }
        });

        register(ToolGroup.class, new Factory<DocumentContent>(DocumentContent.class) {
            @Override
            public DocumentContent create() {
                final ActionToolbarPanel<StateToken> contentNavigatorToolbar = new ActionToolbarPanel<StateToken>(
                        ActionToolbarPanel.Position.content, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionToolbar<StateToken> toolbar = new ActionToolbarPresenter<StateToken>(
                        contentNavigatorToolbar);

                final DocumentContentPresenter presenter = new DocumentContentPresenter($(StateManager.class),
                        $(Session.class), $$(DocumentReader.class), $$(TextEditor.class), $$(FolderViewer.class),
                        toolbar, $(ContentActionRegistry.class));
                final DocumentContentPanel panel = new DocumentContentPanel($(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<AdminContext>(AdminContext.class) {
            @Override
            public AdminContext create() {
                final AdminContextPresenter presenter = new AdminContextPresenter($(Session.class),
                        $(StateManager.class), $$(TagsSummary.class), $$(ContentServiceAsync.class),
                        $(EntityTitle.class), $(EntitySubTitle.class));
                final AdminContextView view = new AdminContextPanel(presenter, $(I18nUITranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<DocumentContext>(DocumentContext.class) {
            @Override
            public DocumentContext create() {
                final DocumentContextPresenter presenter = new DocumentContextPresenter($(StateManager.class),
                        $$(ContextNavigator.class), $$(AdminContext.class));
                final DocumentContextPanel panel = new DocumentContextPanel($(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<DocumentReader>(DocumentReader.class) {
            @Override
            public DocumentReader create() {
                final DocumentReaderView view = new DocumentReaderPanel($(WorkspaceSkeleton.class));
                final DocumentReaderPresenter presenter = new DocumentReaderPresenter(view, $$(FileDownloadUtils.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<FolderViewer>(FolderViewer.class) {
            @Override
            public FolderViewer create() {
                final FolderViewerView view = new FolderViewerPanel();
                final FolderViewerPresenter presenter = new FolderViewerPresenter(view);
                return presenter;
            }
        });
    }
}
