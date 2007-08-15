package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import com.google.inject.Inject;

public class DatabaseInitializationTest {
    @Inject
    UserManager manager;
    @Inject
    LicenseManager licenseManager;
    @Inject
    DatabaseProperties properties;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
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
