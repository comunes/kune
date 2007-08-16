package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wideplay.warp.jpa.JpaUnit;

public class TestHelper {
    public static void inject(final Object target) {
	TestHelper.create(new PlatformServerModule(), "test", "kune.properties").injectMembers(target);
    }

    public static Injector create(final Module module, final String persistenceUnit, final String propetiesFileName) {
	Injector injector = Guice.createInjector(module, new Module() {
	    public void configure(Binder binder) {
		binder.bindConstant().annotatedWith(JpaUnit.class).to(persistenceUnit);
		binder.bindConstant().annotatedWith(PropertiesFileName.class).to(propetiesFileName);
	    }
	});
	return injector;
    }

}
