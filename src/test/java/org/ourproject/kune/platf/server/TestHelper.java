package org.ourproject.kune.platf.server;

import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.platf.integration.HttpServletRequestMocked;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wideplay.warp.jpa.JpaUnit;

public abstract class TestHelper {
    public static Injector create(final Module module, final String persistenceUnit, final String propetiesFileName) {
        Injector injector = Guice.createInjector(module, new Module() {
            public void configure(Binder binder) {
                binder.bindConstant().annotatedWith(JpaUnit.class).to(persistenceUnit);
                binder.bindConstant().annotatedWith(PropertiesFileName.class).to(propetiesFileName);
                binder.bind(HttpServletRequest.class).to(HttpServletRequestMocked.class);
            }
        });
        return injector;
    }

    public static void inject(final Object target) {
        // test: use memory
        // test_db: use mysql
        TestHelper.create(new PlatformServerModule(), "test", "kune.properties").injectMembers(target);
    }

}
