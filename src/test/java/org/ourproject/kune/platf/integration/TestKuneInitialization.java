package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.docs.server.KuneDocumentModule;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.services.ContentServerService;
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
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
    DatabaseProperties properties;
    @Inject
    UserManager manager;
    @Inject
    LicenseManager licenseManager;
    private Injector injector;
    @Inject
    UserSession session;

    @Before
    public void create() {
	injector = Guice.createInjector(new KunePlatformModule(), new KuneDocumentModule(), new AbstractModule() {
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
    public void testWithLoggedUser() throws SerializableException {
	SiteBarService loginService = getService(SiteBarService.class);
	loginService.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	ContentService contentService = getService(ContentService.class);
	ContentDTO response = contentService.getContent(null, null, null, null, null);
	assertNotNull(response.getAccessRights());
	assertTrue(response.getAccessRights().isEditable);
    }

    @Test
    public void testLogin() throws SerializableException {
	assertNull(session.getUser());
	SiteBarService service = getService(SiteBarService.class);
	service.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	assertNotNull(session.getUser());
	// TODO: esto es para ti, vicente
	// service.login(properties.getDefaultSiteAdminEmail(),
	// properties.getAdminPassword());
    }

    @Test
    public void testCallDefault() throws ContentNotFoundException {
	ContentServerService service = getService(ContentServerService.class);
	ContentDTO content = service.getContent(null, null, null, null, null);
	assertNotNull(content);
	assertNotNull(content.getGroup());
	assertNotNull(content.getFolder());
	assertNotNull(content.getFolder().getId());
	assertNotNull(content.getToolName());
	assertNotNull(content.getDocRef());
    }

    private <T extends RemoteService> T getService(final Class<T> type) {
	return injector.getInstance(type);
    }

    @Test
    public void testDatabase() {
	User user = manager.getByShortName(properties.getDefaultSiteShortName());
	assertNotNull(user);
	Group group = user.getUserGroup();
	assertNotNull(group);
	ToolConfiguration toolConfiguration = group.getToolConfiguration(DocumentServerTool.NAME);
	assertNotNull(toolConfiguration);
	assertNotNull(group.getDefaultContent());
	assertTrue(licenseManager.getAll().size() > 0);
    }
}
