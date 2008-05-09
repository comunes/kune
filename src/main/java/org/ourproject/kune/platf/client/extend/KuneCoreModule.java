package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.emite.client.components.Container;
import com.calclab.emite.client.core.dispatcher.Dispatcher;

public class KuneCoreModule {
    public static final String COMPONENT_I18N = "kune:i18n";
    public static final String COMPONENT_DISPATCHER = "kune:dispatcher";
    public static final String COMPONENT_SESSION = "kune:session";

    public static Dispatcher getDispatcher(final Container container) {
        return (Dispatcher) container.get(COMPONENT_DISPATCHER);
    }

    public static I18nTranslationService getI18n(final Container container) {
        return (I18nTranslationService) container.get(COMPONENT_I18N);
    }

    public static void load(final Container container) {
        // dependencies
        // final Services services =
        // ServicesAbstractModule.getServices(container);

        // injections
        // final DefaultDispatcher dispatcher = new DefaultDispatcher();
        // container.register(COMPONENT_DISPATCHER, dispatcher);

        // final Stream iStream = new Stream();
        // final EmiteBosh emite = new EmiteBosh(dispatcher, iStream);
        // container.register(COMPONENT_EMITE, emite);

        // final Bosh bosh = new Bosh(iStream, options);
        // final BoshManager boshManager = new BoshManager(services, emite,
        // bosh);
        // container.register(COMPONENT_BOSH, boshManager);

    }

}