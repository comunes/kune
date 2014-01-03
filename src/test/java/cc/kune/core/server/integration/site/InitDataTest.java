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
package cc.kune.core.server.integration.site;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.domain.Group;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class InitDataTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InitDataTest extends IntegrationTest {

  /** The group finder. */
  @Inject
  Group groupFinder;

  /** The i18n lang manager. */
  @Inject
  I18nLanguageManager i18nLangManager;

  /** The service. */
  @Inject
  SiteService service;

  /** The session. */
  @Inject
  UserSessionManager session;

  /**
   * Assert valid license dto list.
   * 
   * @param licenseList
   *          the license list
   */
  private void assertValidLicenseDTOList(final List<LicenseDTO> licenseList) {
    assertTrue(licenseList.size() > 0);
    for (final Object o : licenseList) {
      assertNotNull(o);
      assertEquals(LicenseDTO.class, o.getClass());
    }
  }

  /**
   * Inits the.
   */
  @Before
  public void init() {
    new IntegrationTestHelper(true, this);
  }

  /**
   * Test get init data.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testGetInitData() throws Exception {
    final InitDataDTO initData = service.getInitData(null);
    assertNotNull(initData);
    assertValidLicenseDTOList(initData.getLicenses());
    assertTrue(initData.getLanguages().size() > 0);
    assertTrue(initData.getCountries().size() > 0);
    assertNotNull(initData.getLanguages().get(0).getCode());
    assertNotNull(initData.getCountries().get(0).getCode());
    assertNotNull(initData.getGroupTools());
    assertNotNull(initData.getUserTools());
    assertTrue(initData.getGroupTools().size() > 0);
    assertTrue(initData.getUserTools().size() > 0);
    assertTrue(initData.getgSpaceThemes().size() > 1);
    assertTrue(initData.getReservedWords().size() > 1);
  }

}
