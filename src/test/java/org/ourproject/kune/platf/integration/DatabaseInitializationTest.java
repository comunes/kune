package org.ourproject.kune.platf.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.chat.server.ChatServerTool;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
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
import com.wideplay.warp.persist.Transactional;

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
    @Inject
    ContentManager contentManager;
    @Inject
    ContainerManager containerManager;

    private Group defaultGroup;

    /**
     * If this test fails, see database configuration in INSTALL (the collation
     * part) and http://dev.mysql.com/doc/refman/5.0/en/case-sensitivity.html
     * 
     * Title must be created as something like `title` varchar(255) collate
     * utf8_bin default NULL
     * 
     */
    @Test
    public void caseSensitive() {
        final Content defaultContent = defaultGroup.getDefaultContent();
        assertTrue(contentManager.findIfExistsTitle(defaultContent.getContainer(), "Welcome to kune demo"));
        assertTrue(!contentManager.findIfExistsTitle(defaultContent.getContainer(), "welcome to kune Demo"));
        assertTrue(!contentManager.findIfExistsTitle(defaultContent.getContainer(), "Welcome to kune demo "));
    }

    @Transactional
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
        assertNotNull(countryManager.find(Long.valueOf(75)));
        assertNotNull(languageManager.findByCode("en"));
        assertNotNull(languageManager.find(Long.valueOf(1819)));
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
        final List<String> enabledTools = groupManager.findEnabledTools(defaultGroup.getId());
        assertNotNull(enabledTools);
        assertTrue(enabledTools.size() > 0);
    }

}
