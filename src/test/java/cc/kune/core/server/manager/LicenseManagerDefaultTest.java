/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistenceTest;
import cc.kune.domain.License;
import cc.kune.domain.finders.LicenseFinder;

import com.google.inject.Inject;

public class LicenseManagerDefaultTest extends PersistenceTest {
  private License license;
  @Inject
  LicenseFinder licenseFinder;
  @Inject
  LicenseManager licenseManager;

  @After
  public void close() {
    if (getTransaction().isActive()) {
      getTransaction().rollback();
    }
  }

  @Before
  public void insertData() {
    openTransaction();
    assertEquals(0, licenseFinder.getAll().size());
    license = new License("by", "Creative Commons Attribution", "",
        "http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "");
    licenseManager.persist(license);
  }

  @Test
  public void testLicenseCreation() {
    assertNotNull(license.getId());
    assertEquals(1, licenseFinder.getAll().size());
  }
}
