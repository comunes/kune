package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.docs.server.KuneDocumentModule;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManagerDefault;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class TestKuneInitialization {
    @Inject
    KunePersistenceService persistenceService;

    @Inject
    ToolRegistry registry;
    @Inject
    DocumentServerTool documentTool;

    @Inject
    KuneProperties properties;

    @Inject
    UserManagerDefault manager;

    @Before
    public void create() {
	Injector injector = Guice.createInjector(new KunePlatformModule(), new KuneDocumentModule(),
		new AbstractModule() {
		    @Override
		    protected void configure() {
			bindScope(SessionScoped.class, Scopes.SINGLETON);
			bindConstant().annotatedWith(JpaUnit.class).to("test");
			bindConstant().annotatedWith(PropertiesFileName.class).to("kune.properties");
		    }
		});
	injector.injectMembers(this);
	registry.register(documentTool);
	persistenceService.start();
    }

    @Test
    public void testDatabase() {
	User user = manager.getByShortName(properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME));
	assertNotNull(user);
	Group group = user.getUserGroup();
	assertNotNull(group);
	ToolConfiguration toolConfiguration = group.getToolConfiguration(DocumentServerTool.NAME);
	assertNotNull(toolConfiguration);
	assertNotNull(toolConfiguration.getWelcome());
    }
}
