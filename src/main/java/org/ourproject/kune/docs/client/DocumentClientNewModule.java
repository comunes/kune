package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;

import com.calclab.suco.client.modules.AbstractModule;
import com.calclab.suco.client.provider.Factory;
import com.calclab.suco.client.scopes.SingletonScope;

public class DocumentClientNewModule extends AbstractModule {

    public DocumentClientNewModule() {
	super(DocumentClientNewModule.class);
    }

    @Override
    public void onLoad() {
	register(SingletonScope.class, new Factory<DocumentFactory>(DocumentFactory.class) {
	    public DocumentFactory create() {
		return new DocumentFactory($(I18nUITranslationService.class), $(Session.class), $p(TagsSummary.class),
			$(WorkspaceSkeleton.class), $(RateIt.class));
	    }
	});

	register(SingletonScope.class, new Factory<DocumentClientTool>(DocumentClientTool.class) {
	    public DocumentClientTool create() {
		final DocumentFactory factory = $(DocumentFactory.class);
		return new DocumentClientTool(factory, $(I18nUITranslationService.class), $(ToolSelector.class),
			$(WsThemePresenter.class), $(WorkspaceSkeleton.class));
	    }
	});

	final KunePlatform platform = $(KunePlatform.class);
	final DocumentClientTool docClientTool = $(DocumentClientTool.class);
	platform.addTool(docClientTool);

	final Session session = $(Session.class);
	final StateManager stateManager = $(StateManager.class);
	final Application application = $(Application.class);
	final I18nUITranslationService i18n = $(I18nUITranslationService.class);
	platform.install(new DocsClientModule(session, stateManager, application.getWorkspace(), i18n));
    }

}
