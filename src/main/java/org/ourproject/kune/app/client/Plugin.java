package org.ourproject.kune.app.client;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.app.ui.UIExtensionPointManager;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;

public abstract class Plugin {
    private String name;
    private final boolean started;
    private Services services;

    public Plugin(final String name) {
        this.name = name;
        this.started = false;
        // InitDataDTO...
    }

    protected void init(final Services services) {
        this.services = services;
    }

    public final boolean isActive() {
        return started;
    }

    public final Dispatcher getDisplacher() {
        return services.dispatcher;
    }

    public final I18nTranslationService getI18n() {
        return services.i18n;
    }

    public Session getSession() {
        return services.session;
    }

    public UIExtensionPointManager getExtensionPointManager() {
        return services.extensionPointManager;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    protected abstract void start();

    protected abstract void stop();

}
