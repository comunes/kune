package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.actions.ContentEditAction;
import org.ourproject.kune.docs.client.actions.ContentEditInProgressAction;
import org.ourproject.kune.docs.client.actions.ContentPublishAction;
import org.ourproject.kune.docs.client.actions.ContentRejectAction;
import org.ourproject.kune.docs.client.actions.ContentSubmitForPublishAction;
import org.ourproject.kune.docs.client.actions.ContentTranslationAction;
import org.ourproject.kune.docs.client.actions.ContentTrashAction;
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
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.dialogs.upload.FileUploader;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.editor.TextEditor;
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
	    public DocumentClientTool create() {
		$(DocumentClientActions.class);
		return new DocumentClientTool($(I18nUITranslationService.class), $(ToolSelector.class),
			$(WsThemePresenter.class), $(WorkspaceSkeleton.class), $$(DocumentContext.class),
			$$(ContentServiceAsync.class), $(ContentActionRegistry.class),
			$(DragDropContentRegistry.class), $(ContentIconsRegistry.class));
	    }
	});

	register(Singleton.class, new Factory<ContentPublishAction>(ContentPublishAction.class) {
	    public ContentPublishAction create() {
		return new ContentPublishAction($(Session.class), $$(ContentServiceAsync.class),
			$(I18nUITranslationService.class));
	    }
	}, new Factory<ContentRejectAction>(ContentRejectAction.class) {
	    public ContentRejectAction create() {
		return new ContentRejectAction($(Session.class), $$(ContentServiceAsync.class),
			$(I18nUITranslationService.class));
	    }
	}, new Factory<ContentEditInProgressAction>(ContentEditInProgressAction.class) {
	    public ContentEditInProgressAction create() {
		return new ContentEditInProgressAction($(Session.class), $$(ContentServiceAsync.class),
			$(I18nUITranslationService.class));
	    }
	}, new Factory<ContentSubmitForPublishAction>(ContentSubmitForPublishAction.class) {
	    public ContentSubmitForPublishAction create() {
		return new ContentSubmitForPublishAction($(Session.class), $$(ContentServiceAsync.class),
			$(I18nUITranslationService.class));
	    }
	}, new Factory<ContentTrashAction>(ContentTrashAction.class) {
	    public ContentTrashAction create() {
		return new ContentTrashAction($(Session.class), $$(ContentServiceAsync.class),
			$(I18nUITranslationService.class));
	    }
	});

	register(Singleton.class, new Factory<ContentEditAction>(ContentEditAction.class) {
	    public ContentEditAction create() {
		return new ContentEditAction($(I18nUITranslationService.class));
	    }
	}, new Factory<ContentTranslationAction>(ContentTranslationAction.class) {
	    public ContentTranslationAction create() {
		return new ContentTranslationAction($(I18nUITranslationService.class));
	    }
	});

	register(ToolGroup.class, new Factory<DocumentClientActions>(DocumentClientActions.class) {
	    public DocumentClientActions create() {
		return new DocumentClientActions($(I18nUITranslationService.class), $(ContextNavigator.class),
			$(Session.class), $(StateManager.class), $(WorkspaceSkeleton.class),
			$$(ContentServiceAsync.class), $$(FileUploader.class), $(ContentActionRegistry.class),
			$(ContextActionRegistry.class));
	    }
	});

	register(ToolGroup.class, new Factory<DocumentContent>(DocumentContent.class) {
	    public DocumentContent create() {
		final ActionToolbarPanel contentNavigatorToolbar = new ActionToolbarPanel(
			ActionToolbarPanel.Position.content, $$(ActionManager.class), $(WorkspaceSkeleton.class));
		final ActionToolbar toolbar = new ActionToolbarPresenter(contentNavigatorToolbar,
			$(ContentActionRegistry.class));

		final DocumentContentPresenter presenter = new DocumentContentPresenter($(StateManager.class),
			$(I18nUITranslationService.class), $(KuneErrorHandler.class), $(Session.class),
			$(RateIt.class), $$(DocumentReader.class), $$(TextEditor.class), $$(FolderViewer.class),
			$$(ContentServiceAsync.class), toolbar, $(ContentActionRegistry.class));
		final DocumentContentPanel panel = new DocumentContentPanel($(WorkspaceSkeleton.class));

		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT,
			$(ContentPublishAction.class));
		$(ContentActionRegistry.class)
			.addAction(DocumentClientTool.TYPE_DOCUMENT, $(ContentRejectAction.class));
		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT,
			$(ContentSubmitForPublishAction.class));
		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT,
			$(ContentEditInProgressAction.class));
		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT, $(ContentTrashAction.class));

		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT, $(ContentEditAction.class));
		$(ContentActionRegistry.class).addAction(DocumentClientTool.TYPE_DOCUMENT,
			$(ContentTranslationAction.class));
		presenter.init(panel);
		return presenter;
	    }
	});

	register(Singleton.class, new Factory<AdminContext>(AdminContext.class) {
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
	    public DocumentContext create() {
		final DocumentContextPresenter presenter = new DocumentContextPresenter($(StateManager.class),
			$$(ContextNavigator.class), $$(AdminContext.class));
		final DocumentContextPanel panel = new DocumentContextPanel($(WorkspaceSkeleton.class));
		presenter.init(panel);
		return presenter;
	    }
	});

	register(Singleton.class, new Factory<DocumentReader>(DocumentReader.class) {
	    public DocumentReader create() {
		final DocumentReaderView view = new DocumentReaderPanel($(WorkspaceSkeleton.class));
		final DocumentReaderPresenter presenter = new DocumentReaderPresenter($(Session.class), view,
			$(I18nUITranslationService.class));
		return presenter;
	    }
	});

	register(Singleton.class, new Factory<FolderViewer>(FolderViewer.class) {
	    public FolderViewer create() {
		final FolderViewerView view = new FolderViewerPanel();
		final FolderViewerPresenter presenter = new FolderViewerPresenter(view);
		return presenter;
	    }
	});
    }
}
