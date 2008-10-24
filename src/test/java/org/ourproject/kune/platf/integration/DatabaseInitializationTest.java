package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.chat.server.ChatServerTool;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;

import com.google.inject.Inject;

public class DatabaseInitializationTest {
    @Inject
    GroupManager groupManager;
    @Inject
    LicenseManager licenseManager;
    @Inject
    I18nTranslationManager transManager;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nCountryManager countryManager;

    private Group defaultGroup;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
        defaultGroup = groupManager.getSiteDefaultGroup();
    }

    @Test
    public void testDefaultContentAndLicenses() {
        assertNotNull(defaultGroup.getDefaultContent());
        assertTrue(licenseManager.getAll().size() > 0);
        assertNotNull(defaultGroup.getDefaultLicense());
    }

    @Test
    public void testDefaultDocumentContent() {
        final Content content = defaultGroup.getDefaultContent();
        assertEquals(DocumentServerTool.TYPE_DOCUMENT, content.getTypeId());
        final Container rootDocFolder = content.getContainer();
        assertEquals(true, rootDocFolder.isRoot());
    }

    @Test
    public void testI18n() {
        assertNotNull(countryManager.find(new Long(75)));
        assertNotNull(languageManager.findByCode("en"));
        assertNotNull(languageManager.find(new Long(1819)));
    }

    @Test
    public void testToolConfiguration() {
        assertNotNull(defaultGroup);
        final ToolConfiguration docToolConfig = defaultGroup.getToolConfiguration(DocumentServerTool.NAME);
        assertNotNull(docToolConfig);
        assertTrue(docToolConfig.isEnabled());
        final ToolConfiguration chatToolConfig = defaultGroup.getToolConfiguration(ChatServerTool.NAME);
        assertNotNull(chatToolConfig);
        assertTrue(chatToolConfig.isEnabled());
        List<String> enabledTools = groupManager.findEnabledTools(defaultGroup.getId());
        assertNotNull(enabledTools);
        assertTrue(enabledTools.size() > 0);
    }

}
