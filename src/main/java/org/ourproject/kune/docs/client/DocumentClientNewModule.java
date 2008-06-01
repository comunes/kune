package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.emite.client.modular.Container;
import com.calclab.emite.client.modular.Module;
import com.calclab.emite.client.modular.ModuleBuilder;
import com.calclab.emite.client.modular.Provider;
import com.calclab.emite.client.modular.Scopes;

public class DocumentClientNewModule implements Module {
    public static DocumentClientTool getDocumentClientTool(final Container components) {
        return components.getInstance(DocumentClientTool.class);
    }

    public Class<? extends Module> getType() {
        return DocumentClientNewModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
        builder.registerProvider(DocumentClientTool.class, new Provider<DocumentClientTool>() {
            public DocumentClientTool get() {
                return new DocumentClientTool(builder.getInstance(I18nUITranslationService.class));
            }
        }, Scopes.SINGLETON_EAGER);
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
