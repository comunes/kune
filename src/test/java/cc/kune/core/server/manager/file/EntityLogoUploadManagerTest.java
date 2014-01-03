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
package cc.kune.core.server.manager.file;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.integration.content.ContentServiceIntegrationTest;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityLogoUploadManagerTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityLogoUploadManagerTest extends ContentServiceIntegrationTest {

  /** The Constant TEST_FILE. */
  private static final String TEST_FILE = "src/test/java/cc/kune/core/server/manager/file/orig.png";

  /** The manager. */
  @Inject
  EntityLogoUploadManager manager;

  /**
   * Creates the.
   */
  @Before
  public void create() {
    new IntegrationTestHelper(true, this);
  }

  /**
   * Test create logo.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testCreateLogo() throws Exception {
    manager.createUploadedFile(super.getSiteDefaultContent().getStateToken(), "image/png", new File(
        TEST_FILE));
    final StateContainerDTO defaultContent = super.getSiteDefaultContent();
    assertTrue(defaultContent.getGroup().hasLogo());
  }

  /**
   * Test error response.
   */
  @Ignore
  public void testErrorResponse() {
    // JSONObject expected =
    // JSONObject.fromObject("{\"success\":false,\"errors\":[{\"id\":\""
    // + EntityLogoView.LOGO_FORM_FIELD + "\",\"msg\":\"Some message\"}]}");
    // assertEquals(expected, manager.createJsonResponse(false,
    // "Some message"));
  }

  /**
   * Test session exp.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = SessionExpiredException.class)
  public void testSessionExp() throws Exception {
    manager.createUploadedFile("otherhash", null, null, null, null);
  }

  /**
   * Test success response.
   */
  @Ignore
  public void testSuccessResponse() {
    // JSONObject expected =
    // JSONObject.fromObject("{\"success\":true,\"errors\":[{}]}");
    // assertEquals(expected, manager.createJsonResponse(true, null));
  }

  /**
   * Test user must be auth.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = UserMustBeLoggedException.class)
  public void testUserMustBeAuth() throws Exception {
    manager.createUploadedFile(null, null, null, null, null);
  }
}
