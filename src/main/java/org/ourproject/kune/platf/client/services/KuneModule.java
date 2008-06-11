package org.ourproject.kune.platf.client.services;

import java.util.HashMap;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperImpl;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionImpl;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;

public class KuneModule implements Module {
    private final I18nLanguageDTO initialLang;
    private final HashMap<String, String> lexicon;

    public KuneModule(final I18nLanguageDTO initialLang, final HashMap<String, String> lexicon) {
	this.initialLang = initialLang;
	this.lexicon = lexicon;
    }

    public Class<? extends Module> getType() {
	return KuneModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	builder.registerProvider(Kune.class, new Provider<Kune>() {
	    public Kune get() {
		return new Kune(builder);
	    }
	}, SingletonScope.class);

	builder.registerProvider(I18nUITranslationService.class, new Provider<I18nUITranslationService>() {
	    public I18nUITranslationService get() {
		final I18nUITranslationService translationService = new I18nUITranslationService();
		translationService.setCurrentLanguage(initialLang.getCode());
		translationService.setLexicon(lexicon);
		return translationService;
	    }
	}, SingletonScope.class);

	Site.init(builder.getInstance(I18nUITranslationService.class));

	builder.registerProvider(Session.class, new Provider<Session>() {
	    public Session get() {
		return new SessionImpl(Cookies.getCookie("userHash"), initialLang);
	    }
	}, SingletonScope.class);

	builder.registerProvider(KuneErrorHandler.class, new Provider<KuneErrorHandler>() {
	    public KuneErrorHandler get() {
		return new KuneErrorHandler(builder.getInstance(Session.class), builder
			.getInstance(I18nUITranslationService.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(ColorTheme.class, new Provider<ColorTheme>() {
	    public ColorTheme get() {
		return new ColorTheme();
	    }
	}, SingletonScope.class);

	builder.registerProvider(KunePlatform.class, new Provider<KunePlatform>() {
	    public KunePlatform get() {
		return new KunePlatform();
	    }
	}, SingletonScope.class);

	builder.registerProvider(ExtensibleWidgetsManager.class, new Provider<ExtensibleWidgetsManager>() {
	    public ExtensibleWidgetsManager get() {
		return new ExtensibleWidgetsManager();
	    }
	}, SingletonScope.class);

	builder.registerProvider(Application.class, new Provider<Application>() {
	    public Application get() {
		final Session session = builder.getInstance(Session.class);
		final ExtensibleWidgetsManager extensionPointManager = builder
			.getInstance(ExtensibleWidgetsManager.class);
		return new ApplicationDefault(session, extensionPointManager, builder
			.getInstance(I18nUITranslationService.class), builder.getInstance(ColorTheme.class), builder
			.getInstance(KuneErrorHandler.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(StateManager.class, new Provider<StateManager>() {
	    public StateManager get() {
		final Session session = builder.getInstance(Session.class);
		final ContentProviderImpl provider = new ContentProviderImpl(ContentService.App.getInstance());
		final HistoryWrapper historyWrapper = new HistoryWrapperImpl();
		final Application application = builder.getInstance(Application.class);
		final StateManagerDefault stateManager = new StateManagerDefault(provider, application, session,
			historyWrapper);
		History.addHistoryListener(stateManager);
		return stateManager;
	    }
	}, SingletonScope.class);

    }
}
