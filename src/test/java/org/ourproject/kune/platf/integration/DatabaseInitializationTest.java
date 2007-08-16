package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;

import com.google.inject.Inject;

public class DatabaseInitializationTest {
    @Inject
    GroupManager manager;
    @Inject
    LicenseManager licenseManager;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

    @Test
    public void testDatabase() {
	Group group = manager.getDefaultGroup();
	assertNotNull(group);
	ToolConfiguration toolConfiguration = group.getToolConfiguration(DocumentServerTool.NAME);
	assertNotNull(toolConfiguration);
	assertNotNull(group.getDefaultContent());
	assertTrue(licenseManager.getAll().size() > 0);
    }
}
