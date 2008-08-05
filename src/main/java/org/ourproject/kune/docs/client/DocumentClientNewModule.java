package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditorPresenter;
import org.ourproject.kune.docs.client.cnt.folder.ui.FolderEditorPanel;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.cnt.folder.viewer.ui.FolderViewerPanel;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlView;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderControlPanel;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ctx.admin.ui.AdminContextPanel;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.calclab.suco.client.modules.AbstractModule;
import com.calclab.suco.client.provider.Factory;
import com.calclab.suco.client.scopes.SingletonScope;

public class DocumentClientNewModule extends AbstractModule {

    public DocumentClientNewModule() {
	super(DocumentClientNewModule.class);
    }

    @Override
    public void onLoad() {
	final I18nUITranslationService i18n = $(I18nUITranslationService.class);
	final WorkspaceSkeleton ws = $(WorkspaceSkeleton.class);

	register(SingletonScope.class, new Factory<DocumentClientTool>(DocumentClientTool.class) {
	    public DocumentClientTool create() {
		return new DocumentClientTool(i18n, $(ToolSelector.class), $(WsThemePresenter.class),
			$(WorkspaceSkeleton.class), $p(DocumentContent.class), $p(DocumentContext.class));
	    }
	});

	register(SingletonScope.class, new Factory<AdminContext>(AdminContext.class) {
	    public AdminContext create() {
		final AdminContextPresenter presenter = new AdminContextPresenter($(Session.class),
			$p(TagsSummary.class));
		final AdminContextView view = new AdminContextPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<DocumentContent>(DocumentContent.class) {
	    public DocumentContent create() {
		final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
		final DocumentContentPresenter presenter = new DocumentContentPresenter(panel, $(Session.class),
			$(RateIt.class), $p(DocumentReader.class), $p(DocumentReaderControl.class),
			$p(TextEditor.class), $p(FolderViewer.class), $p(FolderEditor.class));
		return presenter;
	    }
	});
	register(SingletonScope.class, new Factory<DocumentContext>(DocumentContext.class) {
	    public DocumentContext create() {
		final WorkspaceDeckPanel view = new WorkspaceDeckPanel();
		final DocumentContextPresenter presenter = new DocumentContextPresenter(view, $p(FolderContext.class),
			$p(AdminContext.class));
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<DocumentReader>(DocumentReader.class) {
	    public DocumentReader create() {
		final DocumentReaderView view = new DocumentReaderPanel(ws);
		final DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<DocumentReaderControl>(DocumentReaderControl.class) {
	    public DocumentReaderControl create() {
		final DocumentReaderControlPresenter presenter = new DocumentReaderControlPresenter();
		final DocumentReaderControlView view = new DocumentReaderControlPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<FolderContext>(FolderContext.class) {
	    public FolderContext create() {
		final FolderContextPresenter presenter = new FolderContextPresenter($(ContextItems.class), i18n);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<FolderEditor>(FolderEditor.class) {
	    public FolderEditor create() {
		final FolderEditorPanel view = new FolderEditorPanel();
		final FolderEditorPresenter presenter = new FolderEditorPresenter(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<FolderViewer>(FolderViewer.class) {
	    public FolderViewer create() {
		final FolderViewerView view = new FolderViewerPanel(ws);
		final FolderViewerPresenter presenter = new FolderViewerPresenter(view);
		return presenter;
	    }
	});

	final KunePlatform platform = $(KunePlatform.class);
	final DocumentClientTool docClientTool = $(DocumentClientTool.class);
	platform.addTool(docClientTool);

	final Session session = $(Session.class);
	final StateManager stateManager = $(StateManager.class);
	platform.install(new DocsClientModule(session, stateManager, i18n));
    }

}
