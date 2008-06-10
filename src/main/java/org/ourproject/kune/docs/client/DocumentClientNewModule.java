package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.modular.client.container.Container;
import com.calclab.modular.client.container.Provider;
import com.calclab.modular.client.modules.Module;
import com.calclab.modular.client.modules.ModuleBuilder;
import com.calclab.modular.client.scopes.SingletonScope;

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
		return new DocumentFactory(builder.getInstance(I18nUITranslationService.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(DocumentClientTool.class, new Provider<DocumentClientTool>() {
	    public DocumentClientTool get() {
		DocumentFactory factory = builder.getInstance(DocumentFactory.class);
		return new DocumentClientTool(factory, builder.getInstance(I18nUITranslationService.class));
	    }
	}, SingletonScope.class);

	KunePlatform platform = builder.getInstance(KunePlatform.class);
	DocumentClientTool docClientTool = getDocumentClientTool(builder);
	platform.addTool(docClientTool);

	Session session = builder.getInstance(Session.class);
	StateManager stateManager = builder.getInstance(StateManager.class);
	Application application = builder.getInstance(Application.class);
	I18nUITranslationService i18n = builder.getInstance(I18nUITranslationService.class);
	platform.install(new DocsClientModule(session, stateManager, application.getWorkspace(), i18n));
    }

}
