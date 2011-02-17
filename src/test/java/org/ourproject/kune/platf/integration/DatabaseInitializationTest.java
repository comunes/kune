/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

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
