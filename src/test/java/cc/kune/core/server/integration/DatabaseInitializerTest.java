/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.integration;

import static cc.kune.docs.shared.DocsToolConstants.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseInitializerTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DatabaseInitializerTest {

  /** The container manager. */
  @Inject
  ContainerManager containerManager;

  /** The content manager. */
  @Inject
  ContentManager contentManager;

  /** The country manager. */
  @Inject
  I18nCountryManager countryManager;

  /** The default group. */
  private Group defaultGroup;

  /** The group manager. */
  @Inject
  GroupManager groupManager;

  /** The language manager. */
  @Inject
  I18nLanguageManager languageManager;

  /** The license manager. */
  @Inject
  LicenseManager licenseManager;

  /** The trans manager. */
  @Inject
  I18nTranslationManager transManager;

  /**
   * Inits the.
   */
  @KuneTransactional
  @Before
  public void init() {
    new IntegrationTestHelper(true, this);
    defaultGroup = groupManager.getSiteDefaultGroup();
  }

  /**
   * Test default content and licenses.
   */
  @Test
  public void testDefaultContentAndLicenses() {
    assertNotNull(defaultGroup.getDefaultContent());
    assertTrue(licenseManager.getAll().size() > 0);
    assertNotNull(defaultGroup.getDefaultLicense());
  }

  /**
   * Test default document content.
   */
  @Test
  public void testDefaultDocumentContent() {
    final Content content = defaultGroup.getDefaultContent();
    assertEquals(TYPE_DOCUMENT, content.getTypeId());
    final Container rootDocFolder = content.getContainer();
    assertEquals(true, rootDocFolder.isRoot());
  }

  /**
   * Test i18n.
   */
  @Test
  public void testI18n() {
    assertNotNull(countryManager.find(Long.valueOf(75)));
    assertNotNull(languageManager.findByCode("en"));
    assertNotNull(languageManager.find(Long.valueOf(1819)));
  }

  /**
   * Test tool configuration.
   */
  @Test
  public void testToolConfiguration() {
    assertNotNull(defaultGroup);
    final ToolConfiguration docToolConfig = defaultGroup.getToolConfiguration(TOOL_NAME);
    assertNotNull(docToolConfig);
    assertTrue(docToolConfig.isEnabled());
    // final ToolConfiguration chatToolConfig =
    // defaultGroup.getToolConfiguration(ChatConstants.NAME);
    // assertNotNull(chatToolConfig);
    // assertTrue(chatToolConfig.isEnabled());
    final List<String> enabledTools = groupManager.findEnabledTools(defaultGroup.getId());
    assertNotNull(enabledTools);
    assertTrue(enabledTools.size() > 0);
    assertNotNull(containerManager.getTrashFolder(defaultGroup).getId());
  }

}
