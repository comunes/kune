package org.ourproject.kune.platf.integration;

import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class IntegrationTestHelper {

    private final Injector injector;

    public IntegrationTestHelper(final Object test) {
	injector = Guice.createInjector(new KunePlatformModule(), new DocumentServerModule(), new AbstractModule() {
	    @Override
	    protected void configure() {
		bindScope(SessionScoped.class, Scopes.SINGLETON);
		bindConstant().annotatedWith(JpaUnit.class).to("test");
		bindConstant().annotatedWith(PropertiesFileName.class).to("kune.properties");
	    }
	});
	injector.getInstance(KunePersistenceService.class).start();
	injector.injectMembers(test);
    }

    public <T extends RemoteService> T getService(final Class<T> type) {
	return injector.getInstance(type);
    }

}
