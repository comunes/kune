package org.ourproject.kune.platf.client.services;

import java.util.HashMap;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.DefaultApplication;
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
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.emite.client.modular.Container;
import com.calclab.emite.client.modular.Module;
import com.calclab.emite.client.modular.ModuleBuilder;
import com.calclab.emite.client.modular.Provider;
import com.calclab.emite.client.modular.Scopes;
import com.google.gwt.user.client.Cookies;

public class KuneModule implements Module {

    public static Kune getKune(final Container container) {
        return container.getInstance(Kune.class);
    }

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
        }, Scopes.SINGLETON);

        builder.registerProvider(I18nUITranslationService.class, new Provider<I18nUITranslationService>() {
            public I18nUITranslationService get() {
                I18nUITranslationService translationService = new I18nUITranslationService();
                translationService.setCurrentLanguage(initialLang.getCode());
                translationService.setLexicon(lexicon);
                return translationService;
            }
        }, Scopes.SINGLETON);

        builder.registerProvider(Session.class, new Provider<Session>() {
            public Session get() {
                return new SessionImpl(Cookies.getCookie("userHash"), initialLang);
            }
        }, Scopes.SINGLETON);

        builder.registerProvider(KuneErrorHandler.class, new Provider<KuneErrorHandler>() {
            public KuneErrorHandler get() {
                return new KuneErrorHandler(builder.getInstance(Session.class));
            }
        });

        builder.registerProvider(KunePlatform.class, new Provider<KunePlatform>() {
            public KunePlatform get() {
                return new KunePlatform();
            }
        }, Scopes.SINGLETON);

        builder.registerProvider(ColorTheme.class, new Provider<ColorTheme>() {
            public ColorTheme get() {
                return new ColorTheme();
            }
        }, Scopes.SINGLETON);

        builder.registerProvider(ExtensibleWidgetsManager.class, new Provider<ExtensibleWidgetsManager>() {
            public ExtensibleWidgetsManager get() {
                return new ExtensibleWidgetsManager();
            }
        });

        final Session session = builder.getInstance(Session.class);

        builder.registerProvider(Application.class, new Provider<Application>() {
            public Application get() {
                HashMap<String, ClientTool> tools = builder.getInstance(KunePlatform.class).getIndexedTools();
                ExtensibleWidgetsManager extensionPointManager = builder.getInstance(ExtensibleWidgetsManager.class);
                return new DefaultApplication(tools, session, extensionPointManager);
            }
        });

        builder.registerProvider(StateManager.class, new Provider<StateManager>() {
            public StateManager get() {
                ContentProviderImpl provider = new ContentProviderImpl(ContentService.App.getInstance());
                final HistoryWrapper historyWrapper = new HistoryWrapperImpl();
                Application application = builder.getInstance(Application.class);
                return new StateManagerDefault(provider, application, session, historyWrapper);
            }
        });

    }
}
