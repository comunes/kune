package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.UIExtensionPointManager;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

public class Services {
    public final Application app;
    public final StateManager stateManager;
    public final Dispatcher dispatcher;
    public final Session session;
    public final UIExtensionPointManager extensionPointManager;
    public final I18nTranslationService i18n;

    public Services(final Application application, final StateManager stateManager, final Dispatcher dispatcher,
            final Session session, final UIExtensionPointManager extensionPointManager,
            final I18nTranslationService i18n) {
        this.app = application;
        this.stateManager = stateManager;
        this.dispatcher = dispatcher;
        this.session = session;
        this.extensionPointManager = extensionPointManager;
        this.i18n = i18n;
    }

}
