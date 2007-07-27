package org.ourproject.kune;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wideplay.warp.jpa.JpaUnit;
import com.wideplay.warp.persist.PersistenceService;

public class GuiceTestHelper {
    public static Injector create(Module module) {
        Injector injector = Guice.createInjector(module , new Module () {
            public void configure(Binder binder) {
                binder.bindConstant().annotatedWith(JpaUnit.class).to("test");
            }
        });
        PersistenceService persistence = injector.getInstance(PersistenceService.class);
        persistence.start();
        return injector;
    }

}
