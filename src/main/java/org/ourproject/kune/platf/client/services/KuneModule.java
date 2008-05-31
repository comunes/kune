package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.emite.client.modular.Container;
import com.calclab.emite.client.modular.Module;
import com.calclab.emite.client.modular.ModuleBuilder;
import com.calclab.emite.client.modular.Provider;
import com.calclab.emite.client.modular.Scopes;

public class KuneModule implements Module {

    public static Kune getKune(final Container container) {
        return container.getInstance(Kune.class);
    }

    public Class<? extends Module> getType() {
        return KuneModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
        builder.add(new KuneModule());
        builder.registerProvider(Kune.class, new Provider<Kune>() {
            public Kune get() {
                return new Kune(builder);
            }

        }, Scopes.SINGLETON);

        builder.registerProvider(I18nUITranslationService.class, new Provider<I18nUITranslationService>() {
            public I18nUITranslationService get() {
                return new I18nUITranslationService();
            }
        }, Scopes.SINGLETON);

        builder.registerProvider(ColorTheme.class, new Provider<ColorTheme>() {
            public ColorTheme get() {
                return new ColorTheme();
            }
        }, Scopes.SINGLETON);
    }

}
