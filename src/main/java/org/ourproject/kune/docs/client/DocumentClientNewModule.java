package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;

import com.calclab.suco.client.container.Container;
import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;

public class DocumentClientNewModule implements Module {
    public static DocumentClientTool getDocumentClientTool(final Container components) {
	return components.getInstance(DocumentClientTool.class);
    }

    public Class<? extends Module> getType() {
	return DocumentClientNewModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	builder.registerProvider(DocumentFactory.class, new Provider<DocumentFactory>() {
	    public DocumentFactory get() {
		return new DocumentFactory(builder.getInstance(I18nUITranslationService.class), builder
			.getInstance(Session.class), builder.getProvider(TagsSummary.class), builder
			.getInstance(WorkspaceSkeleton.class), builder.getInstance(RateIt.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(DocumentClientTool.class, new Provider<DocumentClientTool>() {
	    public DocumentClientTool get() {
		final DocumentFactory factory = builder.getInstance(DocumentFactory.class);
		return new DocumentClientTool(factory, builder.getInstance(I18nUITranslationService.class));
	    }
	}, SingletonScope.class);

	final KunePlatform platform = builder.getInstance(KunePlatform.class);
	final DocumentClientTool docClientTool = getDocumentClientTool(builder);
	platform.addTool(docClientTool);

	final Session session = builder.getInstance(Session.class);
	final StateManager stateManager = builder.getInstance(StateManager.class);
	final Application application = builder.getInstance(Application.class);
	final I18nUITranslationService i18n = builder.getInstance(I18nUITranslationService.class);
	platform.install(new DocsClientModule(session, stateManager, application.getWorkspace(), i18n));
    }

}
